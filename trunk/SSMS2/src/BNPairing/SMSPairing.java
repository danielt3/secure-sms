/**
 * SMSPairing.java
 *
 * Bilinear pairings over MNT4 elliptic curves.
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

public class SMSPairing {

    /**
     * Convenient BigInteger constants
     */
    private static final BigInteger
        _0 = BigInteger.valueOf(0L),
        _1 = BigInteger.valueOf(1L),
        _2 = BigInteger.valueOf(2L),
        _3 = BigInteger.valueOf(3L),
        _6 = BigInteger.valueOf(6L);

    public static final String incompatibleCurves =
        "Cannot compute pairings of points from incompatible elliptic curves";

    public SMSCurve E;
    public SMSCurve2 E2;
    public SMSParams sms;

    public SMSField4 Fp4_0, Fp4_1;

    /**
     * Powers of the base element g by simple multiples of powers of 16.
     */
    protected SMSField4[][] gp16 = null;

    public SMSPairing(SMSCurve2 Et) {
        E2 = Et;
        E = Et.E;
        sms = E.sms;
        Fp4_0 = new SMSField4(sms);
        Fp4_1 = new SMSField4(sms, _1);
        gp16 = new SMSField4[(sms.n.bitLength() + 3)/4][16];
    }

    SMSField4 gl(SMSPoint V, SMSPoint P, SMSPoint A, SMSPoint2 Q) {
        BigInteger n, d;
        BigInteger p = sms.p;
        if (V.isZero() || P.isZero() || Q.isZero() || V.opposite(P)) {
            return Fp4_1;
        }
        BigInteger Vz3 = V.z.multiply(V.z).multiply(V.z).mod(p);
        if (A != null) {
            n = A.m;
            d = A.z;
        } else if (V.equals(P)) {
            // y = Y/Z^3 => 1/2y = Z^3/2Y
            // x = X/Z^2 => 3x^2 = 3X^2/Z^4 =>
            // => lambda = 3x^2/2y = 3X^2/(2Y*Z)
            n = V.x.multiply(V.x).multiply(_3);//.mod(p);
            d = V.y.multiply(V.z).shiftLeft(1);//.mod(p);
        } else {
            // lambda = (P.y - V.y)/(P.x - V.x) // P.Z = 1
            // = (P.Y - V.Y/V.Z^3) / (P.X - V.X/V.Z^2)
            // = (P.Y*V.Z^3 - V.Y) / (P.X*V.Z^3 - V.X*V.Z)
            //comentado
        	//assert (P.z.compareTo(_1) == 0);
            n = P.y.multiply(Vz3).subtract(V.y);//.mod(p);
            d = P.x.multiply(Vz3).subtract(V.x.multiply(V.z));//.mod(p);
        }
        // lambda = n/d
        SMSField2 re, im;
        //n*(Qt[1]/(1+i) - V.x) + d*(V.y - Qt[2]/(1+i)^3/2);
		re = Q.x.divideV().multiply(Vz3.multiply(n)).add(V.y.multiply(d).subtract(V.x.multiply(V.z).multiply(n)).mod(sms.p));
		im = Q.y.negate().divideV().divideV().multiply(d.multiply(Vz3));
        return new SMSField4(sms, re, im);
    }

	/**
	 * Compute the Tate pairing for points P and Q on BN curves E and E'.
	 */
    public SMSField4 eta(SMSPoint P, SMSPoint2 Q) {
        if (!E.contains(P) || !E2.contains(Q)) {
            throw new IllegalArgumentException(incompatibleCurves);
        }
        SMSField4 f = Fp4_1;
        P = P.normalize();
        Q = Q.normalize();
        if (!P.isZero() && !Q.isZero()) {
            SMSParams sms = E.sms;
            SMSPoint V = P;
            BigInteger ord = sms.n;
            for (int i = ord.bitLength() - 2; i >= 0; i--) {
            	SMSPoint A = V.twice(1);
                f = f.square().multiply(gl(V, V, A, Q));
                V = A;
                if (ord.testBit(i)) {
                    f = f.multiply(gl(V, P, null, Q));
                    V = V.add(P);
                }
            }
            f = f.finExp();
        }
        return f;
    }

    SMSField4 gl(SMSPoint2 T, SMSPoint2 Q, SMSPoint2 A, SMSPoint P) {
        SMSField2 n, d;
        if (T.isZero() || P.isZero() || Q.isZero() || T.opposite(Q)) {
            return Fp4_1;
        }
        SMSField2 Tz3 = T.z.cube();
        if (A != null) {
            n = A.m;
            d = A.z;
        } else if (T.equals(Q)) {
            // y = Y/Z^3 => 1/2y = Z^3/2Y
            // x = X/Z^2 => 3x^2 = 3X^2/Z^4 =>
            // => lambda = 3x^2/2y = 3X^2/(2Y*Z)
            n = T.x.square().multiply(_3);
            d = T.y.multiply(T.z).twice(1);
        } else {
            // lambda = (Q.y - T.y)/(Q.x - T.x) // Q.Z = 1
            // = (Q.Y - T.Y/T.Z^3) / (Q.X - T.X/T.Z^2)
            // = (Q.Y*T.Z^3 - T.Y) / (Q.X*T.Z^3 - T.X*T.Z)
            //comentado
        	//assert (Q.z.isOne());
            n = Q.y.multiply(Tz3).subtract(T.y);
            d = Q.x.multiply(Tz3).subtract(T.x.multiply(T.z));
        }
        // lambda = n/d
        SMSField2 re = null, im = null;
        re = Tz3.divideV().divideV().multiply(d).multiply(P.y.negate()).add(d.multiply(T.y));
        im = Tz3.multiplyV().multiply(P.x).subtract(T.x.multiply(T.z)).multiply(n);
        return new SMSField4(sms, re, im);
    }

    public SMSField4 ate(SMSPoint2 Q, SMSPoint P) {
        if (!E.contains(P) || !E2.contains(Q)) {
            throw new IllegalArgumentException(incompatibleCurves);
        }
        SMSField4 f = Fp4_1;
        P = P.normalize();
        Q = Q.normalize();
        if (!P.isZero() && !Q.isZero()) {
            SMSParams sms = E.sms;
            SMSPoint2 T = Q;
            BigInteger ord = sms.t.subtract(_1);
            if (ord.signum() < 0) {
                ord = ord.negate();
                T = T.negate();
            }
            for (int i = ord.bitLength() - 2; i >= 0; i--) {
                SMSPoint2 A = T.twice(1);
                f = f.square().multiply(gl(T, T, A, P));
                T = A;
                if (ord.testBit(i)) {
                    f = f.multiply(gl(T, Q, null, P));
                    T = T.add(Q);
                }
            }
            f = f.finExp();
        }
        return f;
    }

    protected SMSField4 g = null;

    public void gSet(SMSField4 g) {
        this.g = g;
        SMSField4[] gp16i = gp16[0];
        gp16i[0] = Fp4_1;
        gp16i[1] = g;
        for (int i = 1, j = 2; i <= 7; i++, j += 2) {
            gp16i[j  ] = gp16i[i].square();
            gp16i[j+1] = gp16i[j].multiply(g);
        }
        for (int i = 1; i < gp16.length; i++) {
            SMSField4[] gp16h = gp16i;
            gp16i = gp16[i];
            gp16i[0] = gp16h[0];
            for (int j = 1; j < 16; j++) {
                gp16i[j] = gp16h[j].square().square().square().square();
            }
        }
    }

    public SMSField4 gPower(BigInteger k) {
        if (g == null) {
            throw new IllegalArgumentException("not initialized");
        }
        k = k.mod(sms.n);
        SMSField4 A = Fp4_1;
        for (int i = 0, w = 0; i < gp16.length; i++, w >>>= 4) {
            if ((i & 7) == 0) {
                w = k.intValue();
                k = k.shiftRight(32);
            }
            A = A.multiply(gp16[i][w & 0xf]);
        }
        return A;
    }

    public String toString() {
        return "Bilinear pairing over " + E + " and " + E2;
    }
}
