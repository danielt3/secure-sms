package BNPairing;

/**
 * SMSPoint2.java
 *
 * Arithmetic in the group of points on the quadratic twist an MNT4 elliptic curve over GF(p^2).
 *
 * A point of an elliptic curve is only meaningful when suitably attached
 * to some curve.  Hence, there must be no public means to create a point
 * by itself (i.e. concrete subclasses of SMSPoint2 shall have no public
 * constructor); the proper way to do this is to invoke the factory method
 * pointFactory() of the desired SMSCurve subclass.
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

import pseudojava.BigInteger;
import pseudojava.SecureRandom;
import java.util.Random;

public class SMSPoint2 {

    /**
     * Convenient BigInteger constants
     */
    private static final BigInteger
        _0 = BigInteger.valueOf(0L),
        _1 = BigInteger.valueOf(1L),
        _3 = BigInteger.valueOf(3L);

    public static final String differentCurves =
        "Cannot combine points from different elliptic curves";
    public static final String invalidCPSyntax =
        "Syntax error in curve point description";
    public static final String pointNotOnCurve =
        "The given point does not belong to the given elliptic curve";

    /**
     * The underlying elliptic curve, given by its parameters
     */
    SMSCurve2 E;

    /**
     * The projective x-coordinate
     */
    SMSField2 x;

    /**
     * The projective y-coordinate
     */
    SMSField2 y;

    /**
     * The projective z-coordinate
     */
    SMSField2 z;

    /**
     * Numerator of the line slope if this point was the result of a group operation
     */
    SMSField2 m;

    /**
     * Flag/mask for compressed, expanded, or hybrid point representation
     */
    public static final int
        COMPRESSED  = 2,
        EXPANDED    = 4,
        HYBRID      = COMPRESSED | EXPANDED;

    /**
     * Create an instance of the SMSCurve point at infinity on curve E.
     *
     * @param   E   the elliptic curve where the created point is located.
     */
    SMSPoint2(SMSCurve2 E) {
        this.E = E;
        /*
         * the point at infinity is represented as (1, 1, 0) after IEEE Std 1363:2000
         * (notice that this triple satisfies the projective curve equation y^2 = x^3 - 3xz^4 + bz^6)
         */
        SMSParams sms = E.E.sms;
        x = sms.Fp2_1;
        y = sms.Fp2_1;
        z = sms.Fp2_0;
        m = sms.Fp2_0;
    }

    /**
     * Create a normalized twist point from given affine coordinates and a curve
     *
     * @param   E   the underlying elliptic curve.
     * @param   x   the affine x-coordinate.
     * @param   y   the affine y-coordinate.
     */
    SMSPoint2(SMSCurve2 E, SMSField2 x, SMSField2 y) {
        this.E = E;
        SMSParams sms = E.E.sms;
        this.x = x;
        this.y = y;
        this.z = sms.Fp2_1; // normalized
        this.m = sms.Fp2_0;
        if (!E.contains(this)) {
            throw new IllegalArgumentException(pointNotOnCurve);
        }
    }

    /**
     * Create an SMSCurve point from a given affine x-coordinate, a y-bit and a curve
     *
     * @param   E       the underlying elliptic curve.
     * @param   x       the affine x-coordinate.
     * @param   yBit    the least significant bit of the y-coordinate.
     */
    SMSPoint2(SMSCurve2 E, SMSField2 x, boolean yBit) {
        if (x.isZero()) {
            throw new IllegalArgumentException(pointNotOnCurve); // otherwise the cryptographic subgroup order would not be prime, or the point would be in a small (weak) subgroup
        }
        SMSParams sms = E.sms;
        /*
    	// alpha = x^3 - 3x - b = (x^2 - 3)x - b
        SMSField2 alpha = x.square().subtract(_3).multiply(x).subtract(sms.b);
        SMSField2 beta = alpha.sqrt();
        //*/
        //*
        SMSField2 bt = new SMSField2(E.sms, E.sms.b);
        SMSField2 beta = x.cube().subtract(x.multiplyV().multiplyV().multiply(_3)).add(bt.multiplyV().multiplyV().multiplyV()).sqrt();
        //*/
        if (beta == null) {
            throw new IllegalArgumentException(pointNotOnCurve);
        }
        this.E = E;
        this.x = x;
        this.y = (beta.re.testBit(0) == yBit) ? beta : beta.negate();
        this.z = sms.Fp2_1; // normalized
        this.m = sms.Fp2_0;
    }

    SMSPoint2(SMSCurve2 E, byte[] os) {
        this.E = E;
        SMSParams sms = E.sms;
        int pc = os[0] & 0xff;
        if (pc == 0) { // infinity
	        this.x = sms.Fp2_1;
	        this.y = sms.Fp2_1;
	        this.z = sms.Fp2_0;
        } else {
	        this.x = new SMSField2(sms, os, 1);
	        if (x.isZero()) {
	            throw new IllegalArgumentException(pointNotOnCurve); // otherwise the cryptographic subgroup order would not be prime, or the point would be in a small (weak) subgroup
	        }
	        if ((pc & EXPANDED) != 0) {
				int len = (sms.p.bitLength() + 7)/8;
	        	this.y = new SMSField2(sms, os, 1 + 2*len);
	        } else {
		        boolean yBit = (pc & 1) != 0;
		        SMSField2 bt = new SMSField2(E.sms, E.sms.b);
		        SMSField2 beta = x.cube().subtract(x.multiplyV().multiplyV().multiply(_3)).add(bt.multiplyV().multiplyV().multiplyV()).sqrt();
		        if (beta == null) {
		            throw new IllegalArgumentException(pointNotOnCurve);
		        }
		        this.y = (beta.re.testBit(0) == yBit) ? beta : beta.negate();
	        }
	        this.z = sms.Fp2_1; // normalized
        }
        this.m = sms.Fp2_0;
    }

    /**
     * Create an SMSCurve point from given projective coordinates and a curve.
     *
     * @param   E   the underlying elliptic curve.
     * @param   x   the affine x-coordinate.
     * @param   y   the affine y-coordinate.
     * @param   z   the affine z-coordinate.
     */
    private SMSPoint2(SMSCurve2 E, SMSField2 x, SMSField2 y, SMSField2 z) {
        this.E = E;
        SMSParams sms = E.E.sms;
        this.x = x;
        this.y = y;
        this.z = z;
        this.m = sms.Fp2_0;
    }

    private SMSPoint2(SMSCurve2 E, SMSField2 x, SMSField2 y, SMSField2 z, SMSField2 m) {
        this.E = E;
        this.x = x;
        this.y = y;
        this.z = z;
        this.m = m;
    }

    /**
     * Create a clone of a given point.
     *
     * @param   Q   the point to be cloned.
     */
    private SMSPoint2(SMSPoint2 Q) {
        this.E = Q.E;
        this.x = Q.x;
        this.y = Q.y;
        this.z = Q.z;
        this.m = Q.m;
    }

    /**
     * Check whether this is the point at infinity (i.e. the SMSCurve group zero element).
     *
     * @return  true if this is the point at infinity, otherwise false.
     */
    public boolean isZero() {
        return z.isZero();
    }

    /**
     * Compare this point to a given object.
     *
     * @param   Q   the elliptic curve point to be compared to this.
     *
     * @return  true if this point and Q are equal, otherwise false.
     */
    public boolean equals(Object Q) {
        if (!(Q instanceof SMSPoint2 && this.isOnSameCurve((SMSPoint2)Q))) {
            return false;
        }
        SMSPoint2 P = (SMSPoint2)Q;
        if (z.isZero() || P.isZero()) {
            return z.equals(P.z);
        }
    	/*
    	 * x/z^2 = x'/z'^2 <=> x*z'^2 = x'*z^2.
    	 * y/z^3 = y'/z'^3 <=> y*z'^3 = y'*z^3,
    	 */
        SMSField2
            z2 = z.square(),
            z3 = z.multiply(z2),
            pz2 = P.z.square(),
            pz3 = P.z.multiply(pz2);
        return
            x.multiply(pz2).subtract(P.x.multiply(z2)).isZero() &&
            y.multiply(pz3).subtract(P.y.multiply(z3)).isZero();
    }

    /**
     * Check whether Q lays on the same curve as this point.
     *
     * @param   Q   an elliptic curve point.
     *
     * @return  true if Q lays on the same curve as this point, otherwise false.
     */
    public boolean isOnSameCurve(SMSPoint2 Q) {
        return E.E.sms == Q.E.E.sms; // singleton comparison
    }

    /**
     * Compute a random point on the same curve as this.
     *
     * @param    rand    a cryptographically strong pseudo-random number generator.
     *
     * @return  a random point on the same curve as this.
     */
    public SMSPoint2 randomize(SecureRandom rand) {
        return E.pointFactory(rand);
    }

    /**
     * Normalize this point.
     *
     * @return  a normalized point equivalent to this.
     */
    public SMSPoint2 normalize() {
        if (z.isZero() || z.isOne()) {
            return this; // already normalized
        }
        SMSField2 zinv = null;
        try {
        	zinv = z.inverse();
        } catch (ArithmeticException a) {
        }
        SMSParams sms = E.E.sms;
        SMSField2 zinv2 = zinv.square(), zinv3 = zinv.multiply(zinv2);
        return new SMSPoint2(E, x.multiply(zinv2), y.multiply(zinv3), sms.Fp2_1);
    }

    /**
     * Compute -this.
     *
     * @return  -this.
     */
    public SMSPoint2 negate() {
        return new SMSPoint2(E, x, y.negate(), z);
    }

    /**
     * Check if a point equals -this.
     */
    public boolean opposite(SMSPoint2 P) {
        return this.equals(P.negate());
    }

    /**
     * Compute this + Q.
     *
     * @return  this + Q.
     *
     * @param   Q   an elliptic curve point.
     */
    public SMSPoint2 add(SMSPoint2 Q) {
        /*
        if (!this.isOnSameCurve(Q)) {
            throw new IllegalArgumentException(differentCurves);
        }
        */
        if (this.isZero()) {
            return Q;
        }
        if (Q.isZero()) {
            return this;
        }
        // P1363 section A.10.5
        SMSField2 t1, t2, t3, t4, t5, t6, t7, t8;
        t1 = x;
        t2 = y;
        t3 = z;
        t4 = Q.x;
        t5 = Q.y;
        t6 = Q.z;
        if (!t6.isOne()) {
            t7 = t6.square(); // t7 = z1^2
            // u0 = x0.z1^2
            t1 = t1.multiply(t7);
            // s0 = y0.z1^3 = y0.z1^2.z1
            t2 = t2.multiply(t7).multiply(t6);
        }
        if (!t3.isOne()) {
            t7 = t3.square(); // t7 = z0^2
            // u1 = x1.z0^2
            t4 = t4.multiply(t7);
            // s1 = y1.z0^3 = y1.z0^2.z0
            t5 = t5.multiply(t7).multiply(t3);
        }
        // W = u0 - u1
        t7 = t1.subtract(t4);
        // R = s0 - s1
        t8 = t2.subtract(t5);
        if (t7.isZero()) {
            return t8.isZero() ? Q.twice(1) : E.Ot;
        }
        // T = u0 + u1
        t1 = t1.add(t4);
        // M = s0 + s1
        t2 = t2.add(t5);
        // z2 = z0.z1.W
        if (!t6.isOne()) {
            t3 = t3.multiply(t6);
        }
        t3 = t3.multiply(t7);
        // x2 = R^2 - T.W^2
        t5 = t7.square(); // t5 = W^2
        t6 = t1.multiply(t5); // t6 = T.W^2
        t1 = t8.square().subtract(t6);
        // 2.y2 = (T.W^2 - 2.x2).R - M.W^2.W
        t2 = t6.subtract(t1.twice(1)).multiply(t8).subtract(t2.multiply(t5).multiply(t7)).halve();
        return new SMSPoint2(E, t1, t2, t3);
    }

    /**
     * Left-shift this point by a given distance n, i.e. compute (2^^n)*this.
     *
     * @param    n    the shift amount.
     *
     * @return (2^^n)*this.
     */
    public SMSPoint2 twice(int n) {
        // P1363 section A.10.4
        SMSParams sms = E.E.sms;
        SMSField2 t1, t2, t3, t4, t5, M = sms.Fp2_0;
        t1 = x;
        t2 = y;
        t3 = z;
        while (n-- > 0) {
            if (t2.isZero() || t3.isZero()) {
                return E.Ot;
            }
            t4 = t3.square().multiplyV(); // t4 = z^2.(1+i)
            // M = 3.x^2 - 3.(1+i)^2.(z^2)^2 = 3.[x^2 - (1+i)^2.(z^2)^2] = [x - (z^2).(1+i)].[x + (z^2).(1+i)].3
            M = t4 = t1.subtract(t4).multiply(t1.add(t4)).multiply(_3);
            // z2 = 2.y.z
            t3 = t3.multiply(t2).twice(1);
            // S = 4.x.y^2
            t2 = t2.square(); // t2 = y^2
            t5 = t1.multiply(t2).twice(2);
            // x2 = M^2 - 2.S
            t1 = t4.square().subtract(t5.twice(1));
            // T = 8.(y^2)^2
            t2 = t2.square().twice(3);
            // y2 = M(S - x2) - T
            t2 = t4.multiply(t5.subtract(t1)).subtract(t2);
        }
        return new SMSPoint2(E, t1, t2, t3, M);
    }

    /**
     * Compute k*this
     *
     * @param   k   scalar by which the base point G is to be multiplied
     *
     * @return  k*this
     */
	public SMSPoint2 multiply(BigInteger k) {
		// t > 0:
		// k = k_0 + k_1*(t - 1) => k*Q = k_0*Q + k_1*(t - 1)*Q = k_0*Q + k_1*Q'
		// t < 0:
		// k = k_0 + k_1*(1 - t) => k*Q = k_0*Q - k_1*(t - 1)*Q = k_0*Q - k_1*Q'
		BigInteger m = E.sms.t.subtract(_1).abs();
		SMSPoint2 P = this.normalize();
		if (k.signum() < 0) {
			P = P.negate();
			k = k.negate();
		}
		k = k.mod(E.sms.n);
		SMSPoint2 Y = P.frobex();
		/*
        if (!Y.equals(P.multiply0(E.sms.p))) {
	        System.out.println("Y    = " + Y);
	        System.out.println("p*P  = " + P.multiply0(E.sms.p));
	        System.exit(0);
            throw new RuntimeException("frobex error!");
        }
        //*/
		if (E.sms.t.signum() < 0) {
			Y = Y.negate();
		}
		return P.simultaneous(k.mod(m), k.divide(m), Y);
	}
	//*
    public SMSPoint2 multiply0(BigInteger k) {
        SMSPoint2 P = this.normalize();
        if (k.signum() < 0) {
            k = k.negate();
            P = P.negate();
        }
		k = k.mod(E.sms.n);
        byte[] e = k.toByteArray();
        SMSPoint2[] mP = new SMSPoint2[16];
        mP[0] = E.Ot;
        mP[1] = P;
        for (int i = 1; i <= 7; i++) {
            mP[2*i    ] = mP[  i].twice(1);
            mP[2*i + 1] = mP[2*i].add(P);
        }
        SMSPoint2 A = E.Ot;
        for (int i = 0; i < e.length; i++) {
            int u = e[i] & 0xff;
            A = A.twice(4).add(mP[u >>> 4]).twice(4).add(mP[u & 0xf]);
        }
        return A.normalize();
    }
	//*/

    /**
     * Compute ks*this + kr*Y.  This is useful in the verification part of several signature algorithms,
     * and (hopely) faster than two scalar multiplications.
     *
     * @param   ks  scalar by which this point is to be multiplied.
     * @param   kr  scalar by which Y is to be multiplied.
     * @param   Y   a curve point.
     *
     * @return  ks*this + kr*Y
     */
    public SMSPoint2 simultaneous(BigInteger ks, BigInteger kr, SMSPoint2 Y) {
//        assert (isOnSameCurve(Y));
        SMSPoint2[] hV = new SMSPoint2[16];
        SMSPoint2 P = this.normalize();
        Y = Y.normalize();
        if (ks.signum() < 0) {
        	ks = ks.negate();
        	P = P.negate();
        }
        if (kr.signum() < 0) {
        	kr = kr.negate();
        	Y = Y.negate();
        }
        hV[0] = E.Ot;
        hV[1] = P;
        hV[2] = Y;
        hV[3] = P.add(Y);
        for (int i = 4; i < 16; i += 4) {
            hV[i] = hV[i >> 2].twice(1);
            hV[i + 1] = hV[i].add(hV[1]);
            hV[i + 2] = hV[i].add(hV[2]);
            hV[i + 3] = hV[i].add(hV[3]);
        }
        int t = Math.max(kr.bitLength(), ks.bitLength());
        SMSPoint2 R = E.Ot;
        for (int i = (((t + 1) >> 1) << 1) - 1; i >= 0; i -= 2) {
            int j = (kr.testBit(i  ) ? 8 : 0) |
                    (ks.testBit(i  ) ? 4 : 0) |
                    (kr.testBit(i-1) ? 2 : 0) |
                    (ks.testBit(i-1) ? 1 : 0);
            R = R.twice(2).add(hV[j]);
        }
        return R.normalize();
    }

	public SMSPoint2 frobex() {
		SMSPoint2 P = normalize();
		// (P.x/z^2, P.y/z^3) = (P.x/(1+i), P.y*z/(1+i)^2)
		// psi(P.x/z^2, P.y/z^3) = (conj(P.x)/(1-i), conj(P.y)*(1+i)*sigma*z/(1-i)^2) = (conj(P.x)*i/z^2, -conj(P.y)*(1+i)*sigma/z^3)
		SMSField2 fx = P.x.conjugate().multiplyI();
		SMSField2 fy = P.y.conjugate().negate().multiplyV().multiply(E.sms.sigma);
		return new SMSPoint2(E, fx, fy);
		// therefore g^u = e(P, Q)^u = e(P, uQ) = e(P, u_0*Q + u_1*psi(Q)) = e(P, Q)^{u_0}*e(P, psi(Q))^{u_1} = g^{u_0}*(g^p)^{u_1}
	}

    /**
     * Convert this curve point to a byte array.
     *
     * @return  this point converted to a byte array
     */
    public byte[] toByteArray(int formFlags) {
        byte[] result;
        if (this.isZero()) {
            result = new byte[1];
            result[0] = (byte)0;
            return result;
        }
        SMSPoint2 P = this.normalize();
        byte[] osX = null, osY = null;
        osX = P.x.toByteArray();
        int pc = P.y.re.testBit(0) ? 1 : 0;
        int resLen = 1 + osX.length;
        if ((formFlags & COMPRESSED) != 0) {
            pc |= COMPRESSED;
        }
        if ((formFlags & EXPANDED) != 0) {
            pc |= EXPANDED;
            osY = P.y.toByteArray();
            resLen += osY.length;
        }
        result = new byte[resLen];
        result[0] = (byte)pc;
        System.arraycopy(osX, 0, result, 1, osX.length);
        if (osY != null) {
            System.arraycopy(osY, 0, result, 1 + osX.length, osY.length);
        }
        return result;
    }

    public String toString() {
        return this.isZero() ? "O" : "[" + x + " : " + y + " : " + z + "]";
    }

}
