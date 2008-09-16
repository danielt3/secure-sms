package BNPairing;

/**
 * SMSCurve2.java
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

import java.io.InputStream;
import pseudojava.BigInteger;
import pseudojava.SecureRandom;
import java.util.Random;

public class SMSCurve2 {

    /**
     * Convenient BigInteger constants
     */
    static final BigInteger
        _0 = BigInteger.valueOf(0L),
        _1 = BigInteger.valueOf(1L),
        _3 = BigInteger.valueOf(3L);

    /**
     * Underlying curve E/GF(p) of which this curve is a sextic twist
     */
    SMSCurve E;

	SMSParams sms;

    /**
     * The base point of the cryptographic subgroup
     */
    SMSPoint2 Gt;

    /**
     * The point at infinity
     */
    SMSPoint2 Ot;

    /**
     * Multiples of the base point Gt by simple multiples of powers of 16.
     */
    //protected SMSPoint2[][] pp16Gt;

    /**
     * Build the quadratic twist MNT4'(F_{p^2}): y'^2 = x'^3 - 3*v^2*x' - b*v^3 of the curve MNT4(F_p): y^2 = x^3 - 3*x + b.
     *
     * @param   E   given MNT4 curve
     *
     * @return  the desired curve, or null if E does not have a suitable twist of the above form.
     */
    public SMSCurve2(SMSCurve E) {
        this.E = E;
        this.sms = E.sms;
        Ot = new SMSPoint2(this);
        //BigInteger nt = sms.p.subtract(_1).pow(2).add(sms.t.pow(2));
        //BigInteger nt = sms.t.multiply(sms.t).multiply(sms.n);
        Gt = new SMSPoint2(this, sms.xt, sms.yt);
		/*
		System.out.println("Gt    = " + Gt);
		System.out.println("n*Gt  = " + Gt.multiply0(sms.n));
		System.out.println("t2*Gt = " + Gt.multiply0(sms.t).multiply0(sms.t).normalize());
		System.out.println("nt*Gt = " + Gt.multiply0(nt));
		//*/
        /*
        System.out.println(
"            yt = new SMSField2(this,\n" +
"                new BigInteger(\"" + Gt.y.re + "\"),\n" +
"                new BigInteger(\"" + Gt.y.im + "\"),\n" +
"                false);");
		//*/
        /*
        pp16Gt = new SMSPoint2[(sms.n.bitLength() + 3)/4][16];
        SMSPoint2[] pp16Gi = pp16Gt[0];
        pp16Gi[0] = Ot;
        pp16Gi[1] = Gt;
        for (int i = 1, j = 2; i <= 7; i++, j += 2) {
            pp16Gi[j  ] = pp16Gi[i].twice(1);
            pp16Gi[j+1] = pp16Gi[j].add(Gt);
        }
        for (int i = 1; i < pp16Gt.length; i++) {
            SMSPoint2[] pp16Gh = pp16Gi;
            pp16Gi = pp16Gt[i];
            pp16Gi[0] = pp16Gh[0];
            for (int j = 1; j < 16; j++) {
                pp16Gi[j] = pp16Gh[j].twice(4);
            }
        }
        //*/
    }

    /**
     * Get a random nonzero point on this curve, given a fixed base point.
     *
     * @param   rand    a cryptographically strong PRNG
     *
     * @return  a random nonzero point on this curve
     */
    public SMSPoint2 pointFactory(SecureRandom rand) {
        BigInteger k;
        do {
            k = new BigInteger(sms.n.bitLength(), rand).mod(sms.n);
        } while (k.signum() == 0);
        return Gt.multiply(k);
    }

    /**
     * Check whether this curve contains a given point
     * (i.e. whether that point satisfies the curve equation)
     *
     * @param   P   the point whose pertinence or not to this curve is to be determined
     *
     * @return  true if this curve contains P, otherwise false
     */
    public boolean contains(SMSPoint2 P) {
        if (P.E != this) {
            return false;
        }
        // check the projective equation y^2 = x^3 - 3*(1+i)^2*x*z^4 + b*(1+i)^3*z^6,
        // i.e. x^3 + (z^2*v*b - 3*x)*(z^2*v)^2 - y^2 = 0
        SMSField2
            x  = P.x,
            y  = P.y,
            z  = P.z,
            w  = z.square().multiplyV();
        return y.square().equals(x.cube().add(w.multiply(sms.b).subtract(x.multiply(_3)).multiply(w.square())));
    }

    /**
     * Compute k*G
     *
     * @param   k   scalar by which the base point G is to be multiplied
     *
     * @return  k*G
     *
     * References:
     *
     * Alfred J. Menezes, Paul C. van Oorschot, Scott A. Vanstone,
     *      "Handbook of Applied Cryptography", CRC Press (1997),
     *      section 14.6 (Exponentiation)
     */
    /*
    public SMSPoint2 kG(BigInteger k) {
        k = k.mod(sms.n);
        SMSPoint2 A = Ot;
        for (int i = 0, w = 0; i < pp16Gt.length; i++, w >>>= 4) {
            if ((i & 7) == 0) {
                w = k.intValue();
                k = k.shiftRight(32);
            }
            A = A.add(pp16Gt[i][w & 0xf]);
        }
        return A;
    }
    //*/

    public String toString() {
        return "MNT4': y'^2 = x'^3 - 3*(1+i)^2*x + " + sms.b + "*(1+i)^3";
    }
}
