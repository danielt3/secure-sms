package br.usp.larc.pbarreto.jaes;

/**
 * MAC.java
 *
 * A simple abstraction for the basic functionality of a
 * message authentication code (MAC) engine.
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
abstract public class MAC {

    abstract public void init();

    /**
     * Update the MAC tag computation with a message (AAD or ciphertext) chunk.
     *
     * @param   M   AAD or ciphertext chunk
     * @param   m   its length in bytes
     * @param   aad whether the message chunk is part of the AAD (or else the ciphertext)
     */
    abstract public void update(byte[] M, int m, boolean aad);

    /**
     * Update the MAC tag computation with a full data block.
     *
     * @param   M   the data block
     */
    abstract public void update(byte[] M);

    /**
     * Complete a phase (AAD or ciphertext) of the MAC computation.
     *
     * @param   aad whether the message chunk is part of the AAD (or else the ciphertext)
     */
    abstract public void finish(boolean aad);

    /**
     * Get the complete MAC tag of the whole message provided.
     *
     * @param   tag the MAC tag
     * @param   t   its length in bytes
     */
    abstract public void getTag(byte[] tag, int t);

}