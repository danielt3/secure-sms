/**
 * SMSTests.java
 *
 * Copyright (C) Paulo S. L. M. Barreto.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package BNPairing;

import ssms.Output;
import ssms.pseudojava.BigInteger;
import ssms.pseudojava.SecureRandom;
import java.util.Random;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class SMSTests {

    protected static final BigInteger
        _0 = BigInteger.valueOf(0L),
        _1 = BigInteger.valueOf(1L),
        _2 = BigInteger.valueOf(2L),
        _3 = BigInteger.valueOf(3L),
        _4 = BigInteger.valueOf(4L),
        _5 = BigInteger.valueOf(5L),
        _6 = BigInteger.valueOf(6L);

    /**
     * Generic prototypes used in the SMSPoint and SMSPoint2 tests.
     */
    SMSPoint prototype;
    SMSPoint2 prototype2;

    /**
     * Create an instance of SMSTests by providing prototypes
     * for SMSPoint and GF variables.
     *
     * This is a direct application of the "Prototype" design pattern
     * as described by E. Gamma, R. Helm, R. Johnson and J. Vlissides in
     * "Design Patterns - Elements of Reusable Object-Oriented Software",
     * Addison-Wesley (1995), pp. 117-126.
     *
     * @param   prototype   the prototype for SMSPoint instantiation
     */
    public SMSTests(SMSPoint prototype, SMSPoint2 prototype2) {
        this.prototype = prototype;
        this.prototype2 = prototype2;
    }

    /**
     * Perform a complete test suite on the SMSCurve implementation
     *
     * @param   iterations  the desired number of iterations of the test suite
     * @param   random      the source of randomness for the various tests
     */
    public void doTest(int iterations, SecureRandom rand, boolean verbose) {
        SMSPoint w, x, y, z, ecZero;
        BigInteger m, n;
        int numBits = 256; // caveat: maybe using larger values is better
        long totalElapsed = -System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            if (verbose) {
                System.out.print("test #" + i);
            }
            long elapsed = -System.currentTimeMillis();
            // create random values from the prototype:
            x = prototype.randomize(rand);
            y = prototype.randomize(rand);
            z = prototype.randomize(rand);
            ecZero = prototype.E.O;
            m = new BigInteger(numBits, rand);
            n = new BigInteger(numBits, rand);

            // check cloning/comparison/pertinence:
            if (iterations == 1) {
                System.out.print("\nchecking cloning/comparison/pertinence");
            }
            if (!x.equals(x)) {
                throw new RuntimeException("Comparison failure");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.isOnSameCurve(x)) {
                throw new RuntimeException("Inconsistent pertinence self-comparison");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.E.contains(x)) {
                throw new RuntimeException("Inconsistent curve pertinence");
            }
            if (verbose) {
                System.out.print(".");
            }

            // check addition properties:
            if (iterations == 1) {
                System.out.print(" done.\nchecking addition properties");
            }
            if (!x.add(y).equals(y.add(x))) {
                throw new RuntimeException("x + y != y + x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.add(ecZero).equals(x)) {
                throw new RuntimeException("x + 0 != x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.add(x.negate()).isZero()) {
                throw new RuntimeException("x + (-x) != 0");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.add(y).add(z).equals(x.add(y.add(z)))) {
                throw new RuntimeException("(x + y) + z != x + (y + z)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.negate().negate().equals(x)) {
                throw new RuntimeException("-(-x) != x");
            }

            // check scalar multiplication properties:
            if (iterations == 1) {
                System.out.print(" done.\nchecking scalar multiplication properties");
            }
            if (!x.multiply(BigInteger.valueOf(0L)).equals(ecZero)) {
                throw new RuntimeException("0*x != 0");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(1L)).equals(x)) {
                throw new RuntimeException("1*x != x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(2L)).equals(x.twice(1))) {
                throw new RuntimeException("2*x != twice x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(2L)).equals(x.add(x))) {
                throw new RuntimeException("2*x != x + x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(-1L)).equals(x.negate())) {
                throw new RuntimeException("(-1)*x != -x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(m.negate()).equals(x.negate().multiply(m))) {
                throw new RuntimeException("(-m)*x != m*(-x)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(m.negate()).equals(x.multiply(m).negate())) {
                throw new RuntimeException("(-m)*x != -(m*x)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(m.add(n)).equals(x.multiply(m).add(x.multiply(n)))) {
                throw new RuntimeException("(m + n)*x != m*x + n*x");
            }
            if (verbose) {
                System.out.print(".");
            }
            w = x.multiply(n).multiply(m);
            if (!w.equals(x.multiply(m).multiply(n))) {
                throw new RuntimeException("m*(n*x) != n*(m*x)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!w.equals(x.multiply(m.multiply(n)))) {
                throw new RuntimeException("m*(n*x) != (m*n)*x");
            }
            // TODO: test point compression/expansion/conversion
            elapsed += System.currentTimeMillis();
            if (verbose) {
                System.out.println(" done; elapsed =  " + (float)elapsed/1000 + " s.");
            }
        }
        totalElapsed += System.currentTimeMillis();
        //if (verbose) {
            System.out.println(" OK; all " + iterations + " tests done in " + (float)totalElapsed/1000 + " s.");
        //}
    }

    /**
     * Perform a complete test suite on the SMSCurve2 implementation
     *
     * @param   iterations  the desired number of iterations of the test suite
     * @param   random      the source of randomness for the various tests
     */
    public void doTest2(int iterations, SecureRandom rand, boolean verbose) {
        SMSPoint2 w, x, y, z, ecZero;
        BigInteger m, n;
        int numBits = 256; // caveat: maybe using larger values is better
        long totalElapsed = -System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            if (verbose) {
                System.out.print("test #" + i);
            }
            long elapsed = -System.currentTimeMillis();
            // create random values from the prototype:
            x = prototype2.randomize(rand);
            y = prototype2.randomize(rand);
            z = prototype2.randomize(rand);
            ecZero = prototype2.E.Ot;
            m = new BigInteger(numBits, rand);
            n = new BigInteger(numBits, rand);

            // check cloning/comparison/pertinence:
            if (iterations == 1) {
                System.out.print("\nchecking cloning/comparison/pertinence");
            }
            if (!x.equals(x)) {
                throw new RuntimeException("Comparison failure");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.isOnSameCurve(x)) {
                throw new RuntimeException("Inconsistent pertinence self-comparison");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.E.contains(x)) {
                throw new RuntimeException("Inconsistent curve pertinence");
            }
            if (verbose) {
                System.out.print(".");
            }

            // check addition properties:
            if (iterations == 1) {
                System.out.print(" done.\nchecking addition properties");
            }
            if (!x.add(y).equals(y.add(x))) {
                throw new RuntimeException("x + y != y + x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.add(ecZero).equals(x)) {
                throw new RuntimeException("x + 0 != x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.add(x.negate()).isZero()) {
                throw new RuntimeException("x + (-x) != 0");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.add(y).add(z).equals(x.add(y.add(z)))) {
                throw new RuntimeException("(x + y) + z != x + (y + z)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.negate().negate().equals(x)) {
                throw new RuntimeException("-(-x) != x");
            }

            // check scalar multiplication properties:
            if (iterations == 1) {
                System.out.print(" done.\nchecking scalar multiplication properties");
            }
            if (!x.multiply(BigInteger.valueOf(0L)).equals(ecZero)) {
                throw new RuntimeException("0*x != 0");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(1L)).equals(x)) {
                throw new RuntimeException("1*x != x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(2L)).equals(x.twice(1))) {
                throw new RuntimeException("2*x != twice x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(2L)).equals(x.add(x))) {
                throw new RuntimeException("2*x != x + x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(-1L)).equals(x.negate())) {
                throw new RuntimeException("(-1)*x != -x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(m.negate()).equals(x.negate().multiply(m))) {
                throw new RuntimeException("(-m)*x != m*(-x)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(m.negate()).equals(x.multiply(m).negate())) {
                throw new RuntimeException("(-m)*x != -(m*x)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(m.add(n)).equals(x.multiply(m).add(x.multiply(n)))) {
                throw new RuntimeException("(m + n)*x != m*x + n*x");
            }
            if (verbose) {
                System.out.print(".");
            }
            w = x.multiply(n).multiply(m);
            if (!w.equals(x.multiply(m).multiply(n))) {
                throw new RuntimeException("m*(n*x) != n*(m*x)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!w.equals(x.multiply(m.multiply(n)))) {
                throw new RuntimeException("m*(n*x) != (m*n)*x");
            }
            // TODO: test point compression/expansion/conversion
            elapsed += System.currentTimeMillis();
            if (verbose) {
                System.out.println(" done; elapsed =  " + (float)elapsed/1000 + " s.");
            }
        }
        totalElapsed += System.currentTimeMillis();
        //if (verbose) {
            System.out.println(" OK; all " + iterations + " tests done in " + (float)totalElapsed/1000 + " s.");
        //}
    }

    public static void main(String[] args) throws IOException {

        long elapsed;
        
        //Mudamos para apenas  
        int BM = 10;
        
        //Colocamos o loop para executar apenas uma vez, com 117 bits.
        
        for (int i = 117/*SMSParams.nextValid(0)*/; i <= 117; i = SMSParams.nextValid(i)) {
            //System.out.println("======== bits: " + i);
            
        	       
        	elapsed = -System.currentTimeMillis();
        	SMSParams sms = new SMSParams(i);
        	
            SMSCurve E = new SMSCurve(sms);// System.out.println(E);
        	
            SMSCurve2 E2 = new SMSCurve2(E); //System.out.println(E2);

            SMSPoint P = E.G;
            SMSPoint2 Q = E2.Gt;
                       
            //System.out.println("-----------------");

            if (P.isZero()) {
                throw new RuntimeException("LOGIC ERROR!");
            }
            if (Q.isZero()) {
                throw new RuntimeException("LOGIC ERROR!");
            }
            SMSField4 f;//, g, a, b, c;
            SMSPairing pair = new SMSPairing(E2);
           
            elapsed += System.currentTimeMillis();
            Output.println("Tempo para inicialização de TUDO: " + elapsed + " ms.");
            
            //System.out.println("Benchmarking Eta Pairing:");
            
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = pair.eta(P, Q);
            }
            elapsed += System.currentTimeMillis();
            //System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");
            Output.println("Tempo Eta " + i + " bits: " + (float)elapsed/BM + " ms.");            
            //System.out.println("f = " + f);

            //System.out.println("Benchmarking Ate Pairing:");
            
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = pair.ate(Q, P);
            }
            elapsed += System.currentTimeMillis();
            //System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");
            Output.println("Tempo Ate " + i + " bits: " + (float)elapsed/BM + " ms.");
            //System.out.println("f = " + f);
        }
    }

}
