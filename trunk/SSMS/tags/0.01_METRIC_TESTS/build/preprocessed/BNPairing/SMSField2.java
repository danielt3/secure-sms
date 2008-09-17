package BNPairing;

/**
 * SMSField2.java
 *
 * Arithmetic in the finite extension field GF(p^2) with p = 3 (mod 4).
 *
 * Copyright (C) Paulo S. L. M. Barreto and Pedro d'Aquino F. F. de Sa' Barbuda.
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

import pseudojava.BigInteger;

public class SMSField2 {

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
     * SMS parameters (singleton)
     */
    SMSParams sms;

    /**
     * "Real" part
     */
    BigInteger re;

    /**
     * "Imaginary" part
     */
    BigInteger im;

    SMSField2(SMSParams sms) {
        this.sms = sms;
        this.re = _0;
        this.im = _0;
    }

    SMSField2(SMSParams sms, BigInteger re) {
        this.sms = sms;
        this.re = re; // caveat: no modular reduction!
        this.im = _0;
    }

    SMSField2(SMSParams sms, BigInteger re, BigInteger im, boolean reduce) {
        this.sms = sms;
        if (reduce) {
            this.re = re.mod(sms.p);
            this.im = im.mod(sms.p);
        } else {
            this.re = re;
            this.im = im;
        }
    }

	public SMSField2(SMSParams sms, byte[] compressed, int offset) {
        this.sms = sms;
		int len = (sms.p.bitLength() + 7)/8;
		byte[] vre = new byte[1 + len];
		byte[] vim = new byte[1 + len];
		vre[0] = vim[0] = 0;
		System.arraycopy(compressed, offset,       vre, 1, len);
		System.arraycopy(compressed, offset + len, vim, 1, len);
        this.re = (new BigInteger(vre)).mod(sms.p);
        this.im = (new BigInteger(vim)).mod(sms.p);
	}

    public boolean isZero() {
        return re.signum() == 0 && im.signum() == 0;
    }

    public boolean isOne() {
        return re.compareTo(_1) == 0 && im.signum() == 0;
    }

    public boolean equals(Object u) {
        if (!(u instanceof SMSField2)) {
            return false;
        }
        SMSField2 v = (SMSField2)u;
        return sms == v.sms && // singleton comparison
            re.compareTo(v.re) == 0 &&
            im.compareTo(v.im) == 0;
    }

    /**
     * -(x + yi)
     */
    public SMSField2 negate() {
        return new SMSField2(sms, (re.signum() == 0) ? re : sms.p.subtract(re), (im.signum() == 0) ? im : sms.p.subtract(im), false);
    }

    /**
     * (x + yi)^p = x - yi
     */
    public SMSField2 conjugate() {
        return new SMSField2(sms, re, (im.signum() == 0) ? im : sms.p.subtract(im), false);
    }

    /*
    public BigInteger norm() {
        return re.multiply(re).add(im.multiply(im)).mod(sms.p);
    }
    //*/

    public SMSField2 add(SMSField2 v) {
        if (sms != v.sms) { // singleton comparison
            throw new IllegalArgumentException(differentFields);
        }
        BigInteger r = re.add(v.re);
        if (r.compareTo(sms.p) >= 0) {
            r = r.subtract(sms.p);
        }
        BigInteger i = im.add(v.im);
        if (i.compareTo(sms.p) >= 0) {
            i = i.subtract(sms.p);
        }
        return new SMSField2(sms, r, i, false);
    }

    public SMSField2 add(BigInteger v) {
        BigInteger s = re.add(v);
        if (s.compareTo(sms.p) >= 0) {
            s = s.subtract(sms.p);
        }
        return new SMSField2(sms, s, im, false);
    }

    public SMSField2 subtract(SMSField2 v) {
        if (sms != v.sms) { // singleton comparison
            throw new IllegalArgumentException(differentFields);
        }
        BigInteger r = re.subtract(v.re);
        if (r.signum() < 0) {
            r = r.add(sms.p);
        }
        BigInteger i = im.subtract(v.im);
        if (i.signum() < 0) {
            i = i.add(sms.p);
        }
        return new SMSField2(sms, r, i, false);
    }

    public SMSField2 subtract(BigInteger v) {
        BigInteger r = re.subtract(v);
        if (r.signum() < 0) {
            r = r.add(sms.p);
        }
        return new SMSField2(sms, r, im, false);
    }

    public SMSField2 twice(int k) {
        BigInteger r = re;
        BigInteger i = im;
        while (k-- > 0) {
            r = r.shiftLeft(1);
            if (r.compareTo(sms.p) >= 0) {
                r = r.subtract(sms.p);
            }
            i = i.shiftLeft(1);
            if (i.compareTo(sms.p) >= 0) {
                i = i.subtract(sms.p);
            }
        }
        return new SMSField2(sms, r, i, false);
    }

    public SMSField2 halve() {
        return new SMSField2(sms,
            (re.testBit(0) ? re.add(sms.p) : re).shiftRight(1),
            (im.testBit(0) ? im.add(sms.p) : im).shiftRight(1),
            false);
    }

    /**
     * (x + yi)(u + vi) = (xu - yv) + ((x + y)(u + v) - xu - yv)i
     */
    public SMSField2 multiply(SMSField2 v) {
        if (sms != v.sms) { // singleton comparison
            throw new IllegalArgumentException(differentFields);
        }
        BigInteger re2 = re.multiply(v.re); // mod p
        BigInteger im2 = im.multiply(v.im); // mod p
        BigInteger mix = re.add(im).multiply(v.re.add(v.im)); // mod p;
        return new SMSField2(sms,
            re2.subtract(im2),
            mix.subtract(re2).subtract(im2),
            true);
    }

    /**
     * (x + yi)v = xv + yvi
     */
    public SMSField2 multiply(BigInteger v) {
        return new SMSField2(sms, re.multiply(v), im.multiply(v), true);
    }

    /**
     * (x + yi)^2 = (x + y)(x - y) + 2xyi
     */
    public SMSField2 square() {
        return new SMSField2(sms, 
            re.add(im).multiply(re.subtract(im)),
            re.multiply(im).shiftLeft(1),
            true);
    }

    /**
     * (x + yi)^3 = x(x^2 - 3y^2) + y(3x^2 - y^2)i
     */
    public SMSField2 cube() {
        BigInteger re2 = re.multiply(re); // mod p
        BigInteger im2 = im.multiply(im); // mod p
        return new SMSField2(sms,
            re.multiply(re2.subtract(im2.multiply(_3))),
            im.multiply(re2.multiply(_3).subtract(im2)),
            true);
    }

    /**
     * (x + yi)^{-1} = (x - yi)/(x^2 + y^2)
     */
    public SMSField2 inverse() throws ArithmeticException {
        BigInteger d = re.multiply(re).add(im.multiply(im)).modInverse(sms.p);
        return new SMSField2(sms, re.multiply(d), im.multiply(d).negate(), true);
    }

    /**
     * (x + yi)i = (-y + ix)
     */
    public SMSField2 multiplyI() {
        return new SMSField2(sms, (im.signum() == 0) ? im : sms.p.subtract(im), re, false);
    }

    /**
     * (x + yi)(1 + i) = (x - y) + (x + y)i
     */
    public SMSField2 multiplyV() {
        BigInteger r = re.subtract(im);
        if (r.signum() < 0) {
            r = r.add(sms.p);
        }
        BigInteger i = re.add(im);
        if (i.compareTo(sms.p) >= 0) {
            i = i.subtract(sms.p);
        }
        return new SMSField2(sms, r, i, false);
    }

    public SMSField2 divideV() {
        BigInteger qre = re.add(im);
        if (qre.compareTo(sms.p) >= 0) {
            qre = qre.subtract(sms.p);
        }
        BigInteger qim = im.subtract(re);
        if (qim.signum() < 0) {
            qim = qim.add(sms.p);
        }
        return new SMSField2(sms,
            (qre.testBit(0) ? qre.add(sms.p) : qre).shiftRight(1),
            (qim.testBit(0) ? qim.add(sms.p) : qim).shiftRight(1),
            false);
    }

    public SMSField2 exp(BigInteger k) {
        SMSField2 P = this;
        if (k.signum() < 0) {
        	k = k.negate();
        	P = P.inverse();
        }
        byte[] e = k.toByteArray();
        SMSField2[] mP = new SMSField2[16];
        mP[0] = new SMSField2(sms, _1);
        mP[1] = P;
        for (int m = 1; m <= 7; m++) {
            mP[2*m    ] = mP[  m].square();
            mP[2*m + 1] = mP[2*m].multiply(P);
        }
        SMSField2 A = mP[0];
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
    public SMSField2 sqrt() {
        if (this.isZero()) {
            return this;
        }
        /*
        System.out.println("p             = " + sms.p);
        System.out.println("sqrtExponent2 = " + sms.sqrtExponent2);
        //*/
        SMSField2 r = this.exp(sms.sqrtExponent2); // r = v^{(p^2 + 7)/16}
        //System.out.println("r             = " + r);
        SMSField2 r2 = r.square();
        //System.out.println("this          = " + this);
        //System.out.println("r^2           = " + r2);
        if (r2.subtract(this).isZero()) {
            return r;
        }
        if (r2.add(this).isZero()) {
            return r.multiplyI();
        }
        r2 = r2.multiplyI();
        //SMSField2 sqrtI = new SMSField2(sms, sms.invSqrt2, sms.p.subtract(sms.invSqrt2), false); // sqrt(i) = (1 - i)/sqrt(2)
        r = r.multiply(sms.sqrtI);
        if (r2.subtract(this).isZero()) {
            return r;
        }
        if (r2.add(this).isZero()) {
            return r.multiplyI();
        }
        return null;
    }

	public byte[] toByteArray() {
		int len = (sms.p.bitLength() + 7)/8;
		//System.out.println("byte length = " + len);
        byte[] buf = new byte[2*len];
        for (int i = 0; i < buf.length; i++) {
        	buf[i] = (byte)0;
        }
        byte[] vre = re.toByteArray();
        //String hex = "0123456789abcdef";
        //System.out.print("vre:" + vre.length + " =   "); for (int i = 0; i < vre.length; i++) { System.out.print(hex.charAt((vre[i] & 0xff) >>> 4)); System.out.print(hex.charAt(vre[i] & 15)); } System.out.println();
        byte[] vim = im.toByteArray();
        //System.out.print("vim:" + vim.length + " =                                   "); for (int i = 0; i < vim.length; i++) { System.out.print(hex.charAt((vim[i] & 0xff) >>> 4)); System.out.print(hex.charAt(vim[i] & 15)); } System.out.println();
        buf[0] = (byte)(re.testBit(0) ? 1 : 0);

        //System.arraycopy(vre, (vre.length <= len) ? 0 : 1, buf, (vre.length <= len ? len - vre.length : 0),       len);
        if (vre.length <= len) {
	        System.arraycopy(vre, 0, buf, (len - vre.length), vre.length);
        } else {
	        System.arraycopy(vre, 1, buf, 0,                  len);
        }
        //System.arraycopy(vim, (vim.length <= len) ? 0 : 1, buf, (vim.length <= len ? len - vim.length : 0) + len, len);
        if (vim.length <= len) {
	        System.arraycopy(vim, 0, buf, len + (len - vim.length), vim.length);
        } else {
	        System.arraycopy(vim, 1, buf, len,                      len);
        }

        //System.out.print("buf:" + buf.length + " = "); for (int i = 0; i < buf.length; i++) { System.out.print(hex.charAt((buf[i] & 0xff) >>> 4)); System.out.print(hex.charAt(buf[i] & 15)); } System.out.println();
        return buf;
	}

    public String toString() {
        return "(" + re.toString(16) + ", " + im.toString(16) + ")";
    }
}
