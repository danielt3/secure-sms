package br.usp.larc.pbarreto.jaes;
/*
 * CTR.java
 *
 * The counter (CTR) mode of operation for block ciphers.
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


/*********************************************************************
 * Algorithm CTR_K^N(M)
 ********************************************************************/
public final class CTR {

    private BlockCipher _E; // block cipher context
    private int block_size;
    private int block_ints;
    private int[] N;        // CTR counter (1 block)
    private int[] V;
    private byte[] S;       // CTR mask    (1 block)
    private int s;          // available mask bytes on S

    public CTR(BlockCipher E) {
        if (E.blockBits() > 128) {
            throw new RuntimeException("invalid block size");
        }
        _E = E;
        block_size = _E.blockSize();
        block_ints = block_size >> 2;
        N = new int[block_ints];
        V = new int[block_ints];
        S = new byte[block_size];
        fill(N, 0);
        fill(S, (byte) 0);
        s = 0;
    }

    public void init(int[] IV) {   // effective nonce (initial counter value)
        // initialize nonce:
        System.arraycopy(IV, 0, N, 0, N.length);
        _E.encrypt(N, V);
        _E.int2byte(V, S); // S = E_K(N)
        s = block_size;
    }

    /**
     * Either encrypt or decrypt a message chunk in CTR mode.
     *
     * @param   M   message chunk.
     * @param   m   chunk length in bytes.
     * @param   C   resulting plaintext/ciphertext.
     * @param   G   MAC instance: if G != null, it is updated every time a full block is completed.
     */
    public void update(byte[] M, int m, byte[] C, MAC G) {
        int j = 0;
        for (;;) {
            switch ((m >= s) ? s : m) {
                case 16:
                    C[j + 15] = S[15] ^= M[j + 15]; // FALLTHROUGH
                case 15:
                    C[j + 14] = S[14] ^= M[j + 14]; // FALLTHROUGH
                case 14:
                    C[j + 13] = S[13] ^= M[j + 13]; // FALLTHROUGH
                case 13:
                    C[j + 12] = S[12] ^= M[j + 12]; // FALLTHROUGH
                case 12:
                    C[j + 11] = S[11] ^= M[j + 11]; // FALLTHROUGH
                case 11:
                    C[j + 10] = S[10] ^= M[j + 10]; // FALLTHROUGH
                case 10:
                    C[j + 9] = S[ 9] ^= M[j + 9]; // FALLTHROUGH
                case 9:
                    C[j + 8] = S[ 8] ^= M[j + 8]; // FALLTHROUGH
                case 8:
                    C[j + 7] = S[ 7] ^= M[j + 7]; // FALLTHROUGH
                case 7:
                    C[j + 6] = S[ 6] ^= M[j + 6]; // FALLTHROUGH
                case 6:
                    C[j + 5] = S[ 5] ^= M[j + 5]; // FALLTHROUGH
                case 5:
                    C[j + 4] = S[ 4] ^= M[j + 4]; // FALLTHROUGH
                case 4:
                    C[j + 3] = S[ 3] ^= M[j + 3]; // FALLTHROUGH
                case 3:
                    C[j + 2] = S[ 2] ^= M[j + 2]; // FALLTHROUGH
                case 2:
                    C[j + 1] = S[ 1] ^= M[j + 1]; // FALLTHROUGH
                case 1:
                    C[j] = S[ 0] ^= M[j]; // FALLTHROUGH
                case 0:
                    break;
            }
            if (m < s) {
                break;
            }
            m -= s;
            // increment the nonce:
            for (int w = block_ints - 1; w >= 0; w--) {
                if (++N[w] != 0) {
                    break;
                }
            }
            if (G != null) {
                G.update(S);
            }
            _E.encrypt(N, V);
            _E.int2byte(V, S);
            j += s;
            s = block_size;
        }
        s -= m;
    //assert(0 < s && s <= block_size);
    }

    /**
     * Complete the MAC computation of any remaining message chunk.
     *
     * @param   G   MAC instance: if G != null, it is updated with the remaining message chunk, if any.
     */
    public void finish(MAC G) {
        if (G != null) {
            G.update(S, block_size - s, false);
        }
    }

    public void finalize() {
        fill(N, 0);
        fill(V, 0);
        fill(S, (byte) 0);
        s = 0;
    }

    private void fill(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }
    }

    private void fill(byte[] array, byte value) {
        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }
    }
}
