/**
 * BNField2.java
 *
 * Arithmetic in the finite extension field GF(p^2) with p = 3 (mod 4) and p = 4 (mod 9).
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

import java.math.BigInteger;

public class BNField2 {

    /**
     * Convenient BigInteger constants
     */
    private static final BigInteger
        _0 = BigInteger.valueOf(0L),
        _1 = BigInteger.valueOf(1L),
        _3 = BigInteger.valueOf(3L);

    public static final String differentFields =
        "Operands are in different finite fields";

    /**
     * BN parameters (singleton)
     */
    BNParams bn;

    /**
     * "Real" part
     */
    BigInteger re;

    /**
     * "Imaginary" part
     */
    BigInteger im;

    BNField2(BNParams bn) {
        this.bn = bn;
        this.re = _0;
        this.im = _0;
    }

    BNField2(BNParams bn, BigInteger re) {
        this.bn = bn;
        this.re = re.mod(bn.p);
        this.im = _0;
    }

    BNField2(BNParams bn, BigInteger re, BigInteger im) {
        this.bn = bn;
        this.re = re.mod(bn.p);
        this.im = im.mod(bn.p);
    }

    public boolean isZero() {
        return re.signum() == 0 && im.signum() == 0;
    }

    public boolean isOne() {
        return re.compareTo(_1) == 0 && im.signum() == 0;
    }

    public boolean equals(Object u) {
        if (!(u instanceof BNField2)) {
            return false;
        }
        BNField2 v = (BNField2)u;
        return bn.equals(v.bn) &&
            re.compareTo(v.re) == 0 &&
            im.compareTo(v.im) == 0;
    }

    public BNField2 negate() {
        /*
         * -(x + yi)
         */
        return new BNField2(bn, bn.p.subtract(re), bn.p.subtract(im));
    }

    public BNField2 conjugate() {
        /*
         * (x + yi)^p = x - yi
         */
        return new BNField2(bn, re, bn.p.subtract(im));
    }

    public BigInteger norm() {
        /*
         * (x + yi)^{p + 1} = (x - yi)(x + yi) = x^2 + y^2
         */
        return re.multiply(re).add(im.multiply(im)).mod(bn.p);
    }

	/*
    public BNField2 normalize() throws ArithmeticException {
        // (x + yi)^{p - 1} = (x - yi)^2/(x^2 + y^2) = (x^2 - y^2 - 2xyi)/(x^2 + y^2)
        BigInteger re2 = re.multiply(re);
        BigInteger im2 = im.multiply(im);
        BigInteger d = re2.add(im2).modInverse(bn.p); // norm
        return (d.compareTo(_1) == 0) ? this : new BNField2(bn,
            re2.subtract(im2).multiply(d).mod(bn.p),
            bn.p.subtract(re.multiply(im).shiftLeft(1)).multiply(d).mod(bn.p));
    }
    //*/

    public BNField2 add(BNField2 v) {
        if (!bn.equals(v.bn)) {
            throw new IllegalArgumentException(differentFields);
        }
        return new BNField2(bn, re.add(v.re).mod(bn.p), im.add(v.im).mod(bn.p));
    }

    public BNField2 add(BigInteger v) {
        return new BNField2(bn, re.add(v).mod(bn.p), im);
    }

    public BNField2 twice(int k) {
        return new BNField2(bn, re.shiftLeft(k).mod(bn.p), im.shiftLeft(k).mod(bn.p));
    }

    public BNField2 subtract(BNField2 v) {
        if (!bn.equals(v.bn)) {
            throw new IllegalArgumentException(differentFields);
        }
        return new BNField2(bn, re.subtract(v.re).mod(bn.p), im.subtract(v.im).mod(bn.p));
    }

    public BNField2 subtract(BigInteger v) {
        return new BNField2(bn, re.subtract(v).mod(bn.p), im);
    }

    public BNField2 half() {
        return new BNField2(bn,
            (re.testBit(0) ? re.add(bn.p) : re).shiftRight(1).mod(bn.p),
            (im.testBit(0) ? im.add(bn.p) : im).shiftRight(1).mod(bn.p));
    }

    /**
     * (x + yi)(u + vi) = (xu - yv) + ((x + y)(u + v) - xu - yv)i
     */
    public BNField2 multiply(BNField2 v) {
        if (!bn.equals(v.bn)) {
            throw new IllegalArgumentException(differentFields);
        }
        BigInteger re2 = re.multiply(v.re); // mod p
        BigInteger im2 = im.multiply(v.im); // mod p
        BigInteger mix = re.add(im).multiply(v.re.add(v.im)); // mod p;
        return new BNField2(bn,
            re2.subtract(im2).mod(bn.p),
            mix.subtract(re2).subtract(im2).mod(bn.p));
    }

    /**
     * (x + yi)v = xv + yvi
     */
    public BNField2 multiply(BigInteger v) {
        return new BNField2(bn, re.multiply(v).mod(bn.p), im.multiply(v).mod(bn.p));
    }

    /**
     * (x + yi)^2 = (x + y)(x - y) + 2xyi
     */
    public BNField2 square() {
        return new BNField2(bn,
            re.add(im).multiply(re.subtract(im)).mod(bn.p),
            re.multiply(im).shiftLeft(1).mod(bn.p));
    }

    /**
     * (x + yi)^3 = x(x^2 - 3y^2) + y(3x^2 - y^2)i
     */
    public BNField2 cube() {
        BigInteger re2 = re.multiply(re); // mod p
        BigInteger im2 = im.multiply(im); // mod p
        return new BNField2(bn,
            re.multiply(re2.subtract(im2.multiply(_3))).mod(bn.p),
            im.multiply(re2.multiply(_3).subtract(im2)).mod(bn.p));
    }

    /**
     * (x + yi)^{-1} = (x - yi)/(x^2 + y^2)
     */
    public BNField2 inverse() throws ArithmeticException {
        BigInteger d = re.multiply(re).add(im.multiply(im)).modInverse(bn.p);
        return new BNField2(bn,
            re.multiply(d).mod(bn.p),
            bn.p.subtract(im).multiply(d).mod(bn.p));
    }

    /**
     * (x + yi)i = (-y + ix)
     */
    public BNField2 multiplyI() {
        return new BNField2(bn, bn.p.subtract(im), re);
    }

    /**
     * (x + yi)(1 + i) = (x - y) + (x + y)i
     */
    public BNField2 multiplyV() {
        return new BNField2(bn,
            re.subtract(im).mod(bn.p),
            re.add(im).mod(bn.p));
    }

    public BNField2 divideV() {
        BigInteger qre = re.add(im).mod(bn.p);
        BigInteger qim = im.subtract(re).mod(bn.p);
        return new BNField2(bn,
            (qre.testBit(0) ? qre.add(bn.p) : qre).shiftRight(1),
            (qim.testBit(0) ? qim.add(bn.p) : qim).shiftRight(1));
    }

	/*
    public BNField2 exp0(BigInteger k) {
        BNField2 u = this;
        for (int i = k.bitLength()-2; i >= 0; i--) {
            u = u.square();
            if (k.testBit(i)) {
                u = u.multiply(this);
            }
        }
        return u;
    }
    //*/

    public BNField2 exp(BigInteger k) {
        BNField2 P = this;
        if (k.signum() < 0) {
        	k = k.negate();
        	P = P.inverse();
        }
        byte[] e = k.toByteArray();
        BNField2[] mP = new BNField2[16];
        mP[0] = new BNField2(bn, _1);
        mP[1] = P;
        for (int m = 1; m <= 7; m++) {
            mP[2*m    ] = mP[  m].square();
            mP[2*m + 1] = mP[2*m].multiply(P);
        }
        BNField2 A = mP[0];
        for (int i = 0; i < e.length; i++) {
            int u = e[i] & 0xff;
            A = A.square().square().square().square().multiply(mP[u >>> 4]).square().square().square().square().multiply(mP[u & 0xf]);
        }
        return A;
    }

    /**
     * Compute a square root of this.
     *
     * @return  a square root of this if one exists, or null otherwise.
     */
    public BNField2 sqrt() {
        if (this.isZero()) {
            return this;
        }
        BNField2 r = this.exp(bn.sqrtExponent2); // r = v^{(p^2 + 7)/16}
        BNField2 r2 = r.square();
        if (r2.subtract(this).isZero()) {
            return r;
        }
        if (r2.add(this).isZero()) {
            return r.multiplyI();
        }
        r2 = r2.multiplyI();
        BNField2 sqrtI = new BNField2(bn, _1, bn.p.subtract(_1)).multiply(bn.invSqrtMinus2); // sqrt(i) = (1 - i)/sqrt(-2)
        r = r.multiply(sqrtI);
        if (r2.subtract(this).isZero()) {
            return r;
        }
        if (r2.add(this).isZero()) {
            return r.multiplyI();
        }
        return null;
    }

    /**
     * Compute a cube root of this.
     *
     * @return  a cube root of this if one exists, or null otherwise.
     */
    public BNField2 cbrt() {
        if (this.isZero()) {
            return this;
        }
        BNField2 r = this.exp(bn.cbrtExponent2); // r = v^{(p^2 + 2)/9}
        return r.cube().subtract(this).isZero() ? r : null;
    }

    public String toString() {
        return "(" + re + ", " + im + ")";
    }
}
