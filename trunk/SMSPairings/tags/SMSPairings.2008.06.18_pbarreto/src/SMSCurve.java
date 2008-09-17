/**
 * SMSCurve.java
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
import java.math.BigInteger;
import java.security.SecureRandom;

public class SMSCurve {

    /**
     * Convenient BigInteger constants
     */
    static final BigInteger
        _1 = BigInteger.valueOf(1L),
        _2 = BigInteger.valueOf(2L),
        _3 = BigInteger.valueOf(3L);

    /**
     * BN parameters (singleton)
     */
    SMSParams sms;

    /**
     * The base point of large prime order n
     */
    SMSPoint G;

    /**
     * The point at infinity
     */
    SMSPoint O;

    /**
     * Multiples of the base point G by simple multiples of powers of 16.
     */
    //protected SMSPoint[][] pp16G;

    /**
     * Build the standard MNT4 curve E: y^2 = x^3 - 3x + b.
     *
     * @param   sms  MNT4 parameters of the curve
     *
     * @return  the desired curve, or null if the given index does not define suitable parameters
     */
    public SMSCurve(SMSParams sms) {
        this.sms = sms;
        O = new SMSPoint(this); // caveat: must be set *after* p but *before* G!
        G = new SMSPoint(this, _1, sms.y);
        /*
        System.out.println("G = " + G);
        System.out.println("n*G = " + G.multiply(sms.n));
        //*/
        assert (G.multiply(sms.n).isZero());
        /*
        pp16G = new SMSPoint[(sms.n.bitLength() + 3)/4][16];
        System.out.println("table length = " + pp16G.length);
        SMSPoint[] pp16Gi = pp16G[0];
        pp16Gi[0] = O;
        pp16Gi[1] = G;
        for (int i = 1, j = 2; i <= 7; i++, j += 2) {
            pp16Gi[j  ] = pp16Gi[i].twice(1);
            pp16Gi[j+1] = pp16Gi[j].add(G);
        }
        for (int i = 1; i < pp16G.length; i++) {
            SMSPoint[] pp16Gh = pp16Gi;
            pp16Gi = pp16G[i];
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
    public SMSPoint pointFactory(SecureRandom rand) {
        BigInteger k;
        do {
            k = new BigInteger(sms.n.bitLength(), rand).mod(sms.n);
        } while (k.signum() == 0);
        return G.multiply(k);
    }

    /**
     * Check whether this curve contains a given point
     * (i.e. whether that point satisfies the curve equation)
     *
     * @param   P   the point whose pertinence or not to this curve is to be determined
     *
     * @return  true if this curve contains P, otherwise false
     */
    public boolean contains(SMSPoint P) {
        if (P.E != this) {
            return false;
        }
        // check the projective equation y^2 = x^3 - 3*x*z^4 + b*z^6,
        // i.e. x*x^2 + [b*z^2 - 3*x]*(z^2)^2 - y^2 = 0
        BigInteger
            x  = P.x,
            y  = P.y,
            z  = P.z,
            z2 = z.multiply(z).mod(sms.p),
            br = sms.b.multiply(z2).subtract(_3.multiply(x)).mod(sms.p);
        return x.multiply(x).multiply(x).add(br.multiply(z2).multiply(z2)).subtract(y.multiply(y)).mod(sms.p).signum() == 0;
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
    public SMSPoint kG(BigInteger k) {
        k = k.mod(sms.n);
        SMSPoint A = O;
        for (int i = 0, w = 0; i < pp16G.length; i++, w >>>= 4) {
            if ((i & 7) == 0) {
                w = k.intValue();
                k = k.shiftRight(32);
            }
            A = A.add(pp16G[i][w & 0xf]);
        }
        return A;
    }
    //*/

    public String toString() {
        return "MNT4(F_" + sms.p + "): y^2 = x^3 - 3x + " + sms.b;
    }
}
