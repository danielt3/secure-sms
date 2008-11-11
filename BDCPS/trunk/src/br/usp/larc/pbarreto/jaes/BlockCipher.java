package br.usp.larc.pbarreto.jaes;

/**
 * BlockCipher.java
 *
 * A simple abstraction for the basic functionality of a block cipher engine.
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
abstract public class BlockCipher {

    /**
     * Flag to setup the encryption key schedule.
     */
    public static final int DIR_ENCRYPT = 1;

    /**
     * Flag to setup the decryption key schedule.
     */
    public static final int DIR_DECRYPT = 2;

    /**
     * Flag to setup both key schedules (encryption/decryption).
     */
    public static final int DIR_BOTH    = (DIR_ENCRYPT|DIR_DECRYPT);

    /**
     * Block size in bits.
     */
    abstract public int blockBits();

    /**
     * Block size in bytes.
     */
    abstract public int blockSize();

    /**
     * Key size in bits.
     */
    abstract public int keyBits();

    /**
     * Key size in bytes.
     */
    abstract public int keySize();

    /**
     * Convert one data block from byte[] to int[] representation.
     */
    abstract public void byte2int(byte[] b, int[] i);

    /**
     * Convert one data block from int[] to byte[] representation.
     */
    abstract public void int2byte(int[] i, byte[] b);

    /**
     * Setup the AES key schedule for encryption, decryption, or both.
     *
     * @param   cipherKey   the cipher key (128, 192, or 256 bits).
     * @param   keyBits     size of the cipher key in bits.
     * @param   direction   cipher direction (DIR_ENCRYPT, DIR_DECRYPT, or DIR_BOTH).
     */
    abstract public void makeKey(byte[] cipherKey, int keyBits, int direction);

    /**
     * Encrypt exactly one block of plaintext represented as int[].
     *
     * @param   pt          plaintext block.
     * @param   ct          ciphertext block.
     */
    abstract public void encrypt(int[] pt, int[] ct);

    /**
     * Decrypt exactly one block of ciphertext represented as int[].
     *
     * @param   ct          ciphertext block.
     * @param   pt          plaintext block.
     */
    abstract public void decrypt(int[] ct, int[] pt);

    /**
     * Cleanup the block cipher context.
     */
    abstract public void doFinalize();

}
