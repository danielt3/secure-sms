import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class BNTests {

    protected static final BigInteger
        _0 = BigInteger.valueOf(0L),
        _1 = BigInteger.valueOf(1L),
        _2 = BigInteger.valueOf(2L),
        _3 = BigInteger.valueOf(3L),
        _4 = BigInteger.valueOf(4L),
        _5 = BigInteger.valueOf(5L),
        _6 = BigInteger.valueOf(6L);

    /**
     * Generic prototypes used in the BNPoint and BNPoint2 tests.
     */
    BNPoint prototype;
    BNPoint2 prototype2;

    /**
     * Create an instance of BNTests by providing prototypes
     * for BNPoint and GF variables.
     *
     * This is a direct application of the "Prototype" design pattern
     * as described by E. Gamma, R. Helm, R. Johnson and J. Vlissides in
     * "Design Patterns - Elements of Reusable Object-Oriented Software",
     * Addison-Wesley (1995), pp. 117-126.
     *
     * @param   prototype   the prototype for BNPoint instantiation
     */
    public BNTests(BNPoint prototype, BNPoint2 prototype2) {
        this.prototype = prototype;
        this.prototype2 = prototype2;
    }

    /**
     * Perform a complete test suite on the BNCurve implementation
     *
     * @param   iterations  the desired number of iterations of the test suite
     * @param   random      the source of randomness for the various tests
     */
    public void doTest(int iterations, SecureRandom rand, boolean verbose) {
        BNPoint w, x, y, z, ecZero;
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
            ecZero = prototype.E.infinity;
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
     * Perform a complete test suite on the BNCurve2 implementation
     *
     * @param   iterations  the desired number of iterations of the test suite
     * @param   random      the source of randomness for the various tests
     */
    public void doTest2(int iterations, SecureRandom rand, boolean verbose) {
        BNPoint2 w, x, y, z, ecZero;
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
            ecZero = prototype2.E.infinity;
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

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        byte[] randSeed = new byte[20];
        (new Random()).nextBytes(randSeed);
        /*
        for (int i = 0; i < randSeed.length; i++) {
            randSeed[i] = (byte)i;
        }
        */
        SecureRandom rnd = new SecureRandom(randSeed);
        long elapsed;
        for (int i = 128; i <= 160; i += 16) {
            System.out.println("======== bits: " + i);
            BNParams bn = new BNParams(i);
            BNCurve E = new BNCurve(bn); System.out.println(E);
            BNCurve2 E2 = new BNCurve2(E); System.out.println(E2);
            BNTests T = new BNTests(E.G, E2.Gt);
            //T.doTest(10, rnd, true);
            /*
            {
		        BNField2[] w = new BNField2[6];
		        for (int j = 0; j < 6; j++) {
		        	w[j] = E2.Fp2_0;
		        }
		        w[1] = E2.Fp2_1;
		        BNField12 z = new BNField12(bn, w);
		        System.out.println("***z   = " + z);
		        System.out.println("***z^p = " + z.exp(bn.p));
		        System.exit(0);
            }
            //*/
            //T.doTest2(10, rnd, true);
            System.out.println("-----------------");
            BNPairing pair = new BNPairing(E2);
            System.out.println(pair);
            BNField12 g, a, b, c;
            //
            /*
            g = pair.eta(E.G, E2.Gt);
            System.out.println("g = " + g);
            System.out.println("g^n = " + g.exp(bn.n));
            a = pair.eta(E.G.twice(1), E2.Gt);
            b = pair.eta(E.G, E2.Gt.twice(1));
            c = g.square();
            System.out.println("eq? " + (a.equals(b) && b.equals(c)));
            for (int j = 0; j < 10; j++) {
                BigInteger m = new BigInteger(i, rnd);
                a = pair.eta(E.G.multiply(m), E2.Gt);
                b = pair.eta(E.G, E2.Gt.multiply(m));
                c = g.exp(m);
                System.out.println("eq? " + (a.equals(b) && b.equals(c)));
                if (!(a.equals(b) && b.equals(c)) || a.isOne()) {
                    throw new RuntimeException("LOGIC ERROR!");
                }
            }
            //
            g = pair.ate(E2.Gt, E.G);
            System.out.println("g = " + g);
            System.out.println("g^n = " + g.exp(bn.n));
            a = pair.ate(E2.Gt.twice(1), E.G);
            b = pair.ate(E2.Gt, E.G.twice(1));
            c = g.square();
            System.out.println("eq? " + (a.equals(b) && b.equals(c)));
            if (!(a.equals(b) && b.equals(c)) || a.isOne()) {
                throw new RuntimeException("LOGIC ERROR!");
            }
            for (int j = 0; j < 10; j++) {
                BigInteger m = new BigInteger(i, rnd);
                a = pair.ate(E2.Gt.multiply(m), E.G);
                b = pair.ate(E2.Gt, E.G.multiply(m));
                c = g.exp(m);
                System.out.println("eq? " + (a.equals(b) && b.equals(c)));
                if (!(a.equals(b) && b.equals(c)) || a.isOne()) {
                    throw new RuntimeException("LOGIC ERROR!");
                }
            }
            //*/
            //*
            System.out.println("Q = " + E2.Gt);
            g = pair.opt(E2.Gt, E.G);
            System.out.println("g = " + g);
            System.out.println("g^n = " + g.exp(bn.n));
            a = pair.opt(E2.Gt.twice(1), E.G);
            b = pair.opt(E2.Gt, E.G.twice(1));
            c = g.square();
            System.out.println("eq? " + (a.equals(b) && b.equals(c)));
            if (!(a.equals(b) && b.equals(c)) || a.isOne()) {
                throw new RuntimeException("LOGIC ERROR!");
            }
            //*
            for (int j = 0; j < 10; j++) {
                BigInteger m = new BigInteger(i, rnd);
                a = pair.opt(E2.Gt.multiply(m), E.G);
                b = pair.opt(E2.Gt, E.G.multiply(m));
                c = g.exp(m);
                System.out.println("eq? " + (a.equals(b) && b.equals(c)));
                if (!(a.equals(b) && b.equals(c)) || a.isOne()) {
                    throw new RuntimeException("LOGIC ERROR!");
                }
            }
            //*/
            //
            System.out.println("Benchmarking BNPoint:");
            BNPoint P = E.infinity;
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < 10000; t++) {
                P = P.add(E.G);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/10000 + " ms.");
            System.out.println("P =" + P);
            //
            System.out.println("Benchmarking BNPoint2:");
            BNPoint2 Q = E2.infinity;
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < 10000; t++) {
                Q = Q.add(E2.Gt);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/10000 + " ms.");
            System.out.println("Q =" + Q);
            //
            System.out.println("Benchmarking BNField12:");
            BNField12 f = new BNField12(bn, _2);
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < 10000; t++) {
                f = f.multiply(f);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/10000 + " ms.");
            //
            System.out.println("Benchmarking Eta Pairing:");
            f = pair.Fp12_0;
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < 100; t++) {
                f = pair.eta(P, Q);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/100 + " ms.");
            System.out.println("f = " + f);
            //
            System.out.println("Benchmarking Ate Pairing:");
            f = pair.Fp12_0;
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < 100; t++) {
                f = pair.ate(Q, P);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/100 + " ms.");
            System.out.println("f = " + f);
            //
            System.out.println("Benchmarking Optimal Pairing:");
            f = pair.Fp12_0;
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < 100; t++) {
                f = pair.opt(Q, P);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/100 + " ms.");
            System.out.println("f = " + f);
        }
    }

}
