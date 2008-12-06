package br.usp.larc.pbarreto.jaes;

/*
 * CMAC.java
 *
 * The One-key CBC AEAD (CMAC, formerly OMAC) message authentication code,
 * designed by T. Iwata and K. Kurosawa.
 *
 * @author Paulo S. L. M. Barreto
 *
 * This software is hereby placed in the public domain.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHORS ''AS IS'' AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHORS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
//import java.util.Arrays;

/*********************************************************************
 * Algorithm CMAC_K^t(M)
 ********************************************************************/

public final class CMAC extends MAC {
    private BlockCipher _E; // block cipher context
    private int block_size;
    private int block_ints;
    private int[] L;        // CMAC padding: B = 2L, P = 4L
    private int[] V;
    private byte[] T;       // CMAC tag
    private int rem;        // remaining space on T, in bytes
    private int mask;
    private int state;

    public CMAC(BlockCipher E) {
        if (E.blockBits() != 128 && E.blockBits() != 64) {
            throw new RuntimeException("invalid block size");
        }
        _E = E;
        block_size = _E.blockSize();
        block_ints = block_size >> 2;
        mask = (block_size == 16) ? 0x87 : 0x1B;
        L = new int[block_ints];
        V = new int[block_ints];
        T = new byte[block_size];
        //Arrays.fill(L, 0);
        //Arrays.fill(V, 0);
        //Arrays.fill(T, (byte)0);
        rem = block_size;
        state = -1;
    }

    public void init() {
        // compute padding mask:
        //Arrays.fill(L, 0);
        _E.encrypt(L, L); // L = E_K(0^n)
        int c = (L[0] & 0x80000000) != 0 ? mask : 0;
        for (int b = 0; b < block_ints - 1; b++) {
            L[b] = (L[b] << 1) | (L[b + 1] >>> 31);
        }
        L[block_ints - 1] = (L[block_ints - 1] << 1) ^ c; // B = 2L
        // initialize tag accumulator
        //Arrays.fill(T, (byte)0);
        rem = block_size;
        state = 0; // initialized
    }

    public void update(byte[] M, int m, boolean aad) {
        if (state != 0) {
            throw new RuntimeException("wrong order of operations");
        }
        int i = 0;
        for (;;) {
            switch ((m > rem) ? rem : m) {
            case 16: T[15] ^= M[i + 15]; // FALLTHROUGH
            case 15: T[14] ^= M[i + 14]; // FALLTHROUGH
            case 14: T[13] ^= M[i + 13]; // FALLTHROUGH
            case 13: T[12] ^= M[i + 12]; // FALLTHROUGH
            case 12: T[11] ^= M[i + 11]; // FALLTHROUGH
            case 11: T[10] ^= M[i + 10]; // FALLTHROUGH
            case 10: T[ 9] ^= M[i +  9]; // FALLTHROUGH
            case  9: T[ 8] ^= M[i +  8]; // FALLTHROUGH
            case  8: T[ 7] ^= M[i +  7]; // FALLTHROUGH
            case  7: T[ 6] ^= M[i +  6]; // FALLTHROUGH
            case  6: T[ 5] ^= M[i +  5]; // FALLTHROUGH
            case  5: T[ 4] ^= M[i +  4]; // FALLTHROUGH
            case  4: T[ 3] ^= M[i +  3]; // FALLTHROUGH
            case  3: T[ 2] ^= M[i +  2]; // FALLTHROUGH
            case  2: T[ 1] ^= M[i +  1]; // FALLTHROUGH
            case  1: T[ 0] ^= M[i     ]; // FALLTHROUGH
            case  0: break;
            }
            if (m <= rem) {
                break;
            }
            _E.byte2int(T, V); _E.encrypt(V, V); _E.int2byte(V, T); // since there is more data, no padding applies
            // proceed to the next block:
            m -= rem;
            i += rem;
            rem = block_size;
            //assert(m > 0);
        }
        rem -= m;
        //assert(m == 0 || t < block_size); // m == 0 here only occurs if m == 0 from the very beginning
    }

    public void update(byte[] M) {
        update(M, block_size, false);
    }

    public void finish(boolean aad) {
        if (state != 0) {
            throw new RuntimeException("wrong order of operations");
        }
        // compute padding:
        if (rem > 0) {
            // compute special padding mask:
            int c = (L[0] & 0x80000000) != 0 ? mask : 0;
            for (int b = 0; b < block_ints - 1; b++) {
                L[b] = (L[b] << 1) | (L[b + 1] >>> 31);
            }
            L[block_ints - 1] = (L[block_ints - 1] << 1) ^ c; // P = 4L
            // pad incomplete block:
            T[block_size - rem] ^= 0x80; // padding toggle
            rem = 0;
        }
        _E.byte2int(T, V);
        for (int b = 0; b < block_ints; b++) {
            V[b] ^= L[b];
        }
        _E.encrypt(V, V); _E.int2byte(V, T); // T contains the complete tag
        state = 1; // CMAC tag available
    }

    /**
     * Get the complete CMAC tag of the whole message provided.
     * @return  the CMAC tag.
     */
    public void getTag(byte[] tag, int t) {
        if (state == -1) {
            throw new RuntimeException("wrong order of operations");
        } else if (state == 0) {
            finish(false);
        }
        System.arraycopy(T, 0, tag, 0, (t < block_size) ? t : block_size);
    }

    public void doFinalize() {
        //Arrays.fill(L, 0);
        //Arrays.fill(V, 0);
        //Arrays.fill(T, (byte)0);
        rem = 0;
        state = -1;
    }

}
