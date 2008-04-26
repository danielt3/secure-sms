/**
 * SMSField4.java
 *
 * Arithmetic in the finite extension field GF(p^4) with p = 3 (mod 4).
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

import ssms.pseudojava.BigInteger;

public class SMSField4 {

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
    SMSField2 re;

    /**
     * "Imaginary" part
     */
    SMSField2 im;

    SMSField4(SMSParams sms) {
        this.sms = sms;
        this.re = sms.Fp2_0;
        this.im = sms.Fp2_0;
    }

    SMSField4(SMSParams sms, BigInteger re) {
        this.sms = sms;
        this.re = new SMSField2(sms, re);
        this.im = sms.Fp2_0;
    }

    SMSField4(SMSParams sms, SMSField2 re, SMSField2 im) {
        this.sms = sms;
        this.re = re;
        this.im = im;
    }

    public boolean isZero() {
        return re.isZero() && im.isZero();
    }

    public boolean isOne() {
        return re.isOne()&& im.isOne();
    }

    public boolean equals(Object u) {
        if (!(u instanceof SMSField4)) {
            return false;
        }
        SMSField4 v = (SMSField4)u;
        return sms == v.sms && // singleton comparison
            re.equals(v.re) &&
            im.equals(v.im);
    }

    public SMSField4 negate() {
        return new SMSField4(sms, re.negate(), im.negate());
    }

    public SMSField4 frobenius(int k) {
    	switch (k) {
    	default:
    	case 0:
    		return this;
    	case 1:
    		return new SMSField4(sms, re.conjugate(), im.conjugate().multiplyV().multiply(sms.sigma));
    	case 2:
    		return new SMSField4(sms, re, im.negate());
    	case 3:
            return new SMSField4(sms, re.conjugate(), im.conjugate().multiplyV().multiply(sms.sigma).negate());
    	}
    }

    public SMSField4 add(SMSField4 v) {
        if (sms != v.sms) { // singleton comparison
            throw new IllegalArgumentException(differentFields);
        }
        return new SMSField4(sms, re.add(v.re), im.add(v.im));
    }

    public SMSField4 subtract(SMSField4 v) {
        if (sms != v.sms) { // singleton comparison
            throw new IllegalArgumentException(differentFields);
        }
        return new SMSField4(sms, re.subtract(v.re), im.subtract(v.im));
    }

    /**
     * (x + yz)(u + vz) = (xu + yv(1+i)) + ((x + y)(u + v) - xu - yv)z
     */
    public SMSField4 multiply(SMSField4 v) {
        if (sms != v.sms) { // singleton comparison
            throw new IllegalArgumentException(differentFields);
        }
        SMSField2 re2 = re.multiply(v.re);
        SMSField2 im2 = im.multiply(v.im);
        SMSField2 mix = re.add(im).multiply(v.re.add(v.im));
        return new SMSField4(sms, re2.add(im2.multiplyV()), mix.subtract(re2).subtract(im2));
    }

    /**
     * (x + yz)v = xv + yvz
     */
    public SMSField4 multiply(BigInteger v) {
        return new SMSField4(sms, re.multiply(v), im.multiply(v));
    }

    /**
     * (x + yz)^2 = (x^2 + y^2(1+i)) + ((x + y)^2 - x^2 - y^2)z
     */
    public SMSField4 square() {
        SMSField2 re2 = re.square();
        SMSField2 im2 = im.square();
        SMSField2 mix = re.add(im).square();
        return new SMSField4(sms, re2.add(im2.multiplyV()), mix.subtract(re2).subtract(im2));
    }

    /**
     * (x + yz)^{-1} = (x - yz)/(x^2 - y^2(1+i))
     */
    public SMSField4 inverse() throws ArithmeticException {
        SMSField2 d = re.square().subtract(im.square().multiplyV()).inverse();
        return new SMSField4(sms, re.multiply(d), im.negate().multiply(d));
    }

	//*
    public SMSField4 exp(BigInteger k) {
        SMSField4 u = this;
        for (int i = k.bitLength()-2; i >= 0; i--) {
            u = u.square();
            if (k.testBit(i)) {
                u = u.multiply(this);
            }
        }
        return u;
    }
    //*/

	/*
    public SMSField4 exp(BigInteger k) {
        SMSField4 P = this;
        if (k.signum() < 0) {
        	k = k.negate();
        	P = P.inverse();
        }
        byte[] e = k.toByteArray();
        SMSField4[] mP = new SMSField4[16];
        mP[0] = new SMSField4(sms, _1);
        mP[1] = P;
        for (int m = 1; m <= 7; m++) {
            mP[2*m    ] = mP[  m].square();
            mP[2*m + 1] = mP[2*m].multiply(P);
        }
        SMSField4 A = mP[0];
        for (int i = 0; i < e.length; i++) {
            int u = e[i] & 0xff;
            A = A.square().square().square().square().multiply(mP[u >>> 4]).square().square().square().square().multiply(mP[u & 0xf]);
        }
        return A;
    }
    //*/

    public SMSField4 finExp() {
    	SMSField4 f = this;
        // p^4 - 1 = (p^2 - 1)*(p^2 + 1)
        try {
            f = f.frobenius(2).multiply(f.inverse()); // f = f^(p^2 - 1)
        } catch (ArithmeticException x) {
            f = this; // this can only happen when this instance is not invertible, i.e. zero
        }
        //*
        //comentado
        //assert (f.inverse().equals(f.frobenius(2)));
        //*/
        // (p^2+1)/n = p + t
        //f = f.exp(sms.p.add(sms.t));
        //*
        if (sms.t.signum() >= 0) {
        	f = f.frobenius(1).multiply(f.exp(sms.t));
        } else {
        	f = f.frobenius(1).multiply(f.frobenius(2).exp(sms.t.negate()));
        }
        //*/
        return f;
    }

    public String toString() {
        return "(" + re + ", " + im + ")";
    }
}
