package BNPairing;

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

import pseudojava.BigInteger;

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

    public static final String elementNotOnTorus =
        "Field element not on algebraic torus";

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

	public SMSField4(SMSParams sms, byte[] compressed, int offset) {
        this.sms = sms;
        /*
		int len = (sms.p.bitLength() + 7)/8;
		byte[] vre = new byte[1 + len];
		byte[] vim = new byte[1 + len];
		vre[0] = vim[0] = 0;
		System.arraycopy(compressed, offset + 1,       vre, 1, len);
		System.arraycopy(compressed, offset + 1 + len, vim, 1, len);
		SMSField2 imx = new SMSField2(sms, new BigInteger(vre), new BigInteger(vim), true);
		//*/
		boolean vBit = (compressed[offset] != 0);
		SMSField2 imx = new SMSField2(sms, compressed, offset + 1);
        SMSField2 rex = imx.square().multiplyV().add(_1).sqrt();
        /*
        if (rex == null || rex.re == null) {
        	String hex = "0123456789abcdef";
        	System.out.print("*** compressed @" + offset + " = "); for (int i = 0; i < compressed.length; i++) { System.out.print(hex.charAt((compressed[i] & 0xff) >>> 4)); System.out.print(hex.charAt(compressed[i] & 15)); } System.out.println(": " + compressed.length + " bytes");
        	System.out.println("*** imx = " + imx);
        }
        //*/
        if (rex.re.testBit(0) != vBit) {
        	rex = rex.negate();
        }
        this.re = rex;
        this.im = imx;
	}

    public boolean isZero() {
        return re.isZero() && im.isZero();
    }

    public boolean isOne() {
        return re.isOne()&& im.isZero();
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

	/*
	 * Compute this^{p^k}.
	 */
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
        BigInteger
            t0 = re.re.multiply(v.re.re),//.mod(sms.p),
            t1 = re.im.multiply(v.re.im),//.mod(sms.p),
            t2 = im.re.multiply(v.im.re),//.mod(sms.p),
            t3 = im.im.multiply(v.im.im),//.mod(sms.p),
            t01 = t0.add(t1),
            c01 = re.re.add(re.im).multiply(v.re.re.add(v.re.im)).subtract(t01),//.mod(sms.p),
            t23 = t2.add(t3),
            c23 = im.re.add(im.im).multiply(v.im.re.add(v.im.im)).subtract(t23),//.mod(sms.p),
            u02 = re.re.add(im.re),
            v02 = v.re.re.add(v.im.re),
            t02 = t0.add(t2),
            c02 = u02.multiply(v02).subtract(t02),//.mod(sms.p),
            u13 = re.im.add(im.im),
            v13 = v.re.im.add(v.im.im),
            t13 = t1.add(t3),
            c13 = u13.multiply(v13).subtract(t13),//.mod(sms.p),
            c44 = u02.subtract(u13).multiply(v02.subtract(v13)).subtract(t01.add(t23).add(c02).add(c13)).add(c01).add(c23),//.mod(sms.p),
            w0 = t02.subtract(t13.add(c23)),//.mod(sms.p),
            w1 = c01.add(c23).add(t2).subtract(t3),//.mod(sms.p),
            w2 = c02.subtract(c13),//.mod(sms.p),
            w3 = c44.negate();//sms.p.subtract(c44);        
        return new SMSField4(sms, new SMSField2(sms, w0, w1, true), new SMSField2(sms, w2, w3, true));
    }
    /*
    public SMSField4 multiply(SMSField4 v) {
        if (sms != v.sms) { // singleton comparison
            throw new IllegalArgumentException(differentFields);
        }
        SMSField2 re2 = re.multiply(v.re);
        SMSField2 im2 = im.multiply(v.im);
        SMSField2 mix = re.add(im).multiply(v.re.add(v.im));
        return new SMSField4(sms, re2.add(im2.multiplyV()), mix.subtract(re2).subtract(im2));
    }
    //*/

    /**
     * (x + yz)v = xv + yvz
     */
    public SMSField4 multiply(BigInteger v) {
        return new SMSField4(sms, re.multiply(v), im.multiply(v));
    }

    /**
     * (x + yz)^2 = (x^2 + y^2(1+i)) + ((x + y)^2 - x^2 - y^2)z
     */
    //*
    public SMSField4 square() {
        BigInteger
            re2re = re.re.add(re.im).multiply(re.re.subtract(re.im)),
            re2im = re.re.multiply(re.im).shiftLeft(1),
            im2re = im.re.add(im.im).multiply(im.re.subtract(im.im)),
            im2im = im.re.multiply(im.im).shiftLeft(1),
            sumre = re.re.add(im.re),
            sumim = re.im.add(im.im),
            mixre = sumre.add(sumim).multiply(sumre.subtract(sumim)),
            mixim = sumre.multiply(sumim).shiftLeft(1);
        return new SMSField4(sms, new SMSField2(sms, re2re.add(im2re).subtract(im2im), re2im.add(im2im).add(im2re), true), new SMSField2(sms, mixre.subtract(re2re.add(im2re)), mixim.subtract(re2im.add(im2im)), true));
    }
    //*/
    /*
    public SMSField4 square() {
        SMSField2 re2 = re.square();
        SMSField2 im2 = im.square();
        SMSField2 mix = re.add(im).square();
        return new SMSField4(sms, re2.add(im2.multiplyV()), mix.subtract(re2).subtract(im2));
    }
    //*/

    /**
     * (x + yz)^{-1} = (x - yz)/(x^2 - y^2(1+i))
     */
    public SMSField4 inverse() throws ArithmeticException {
        SMSField2 d = re.square().subtract(im.square().multiplyV()).inverse();
        return new SMSField4(sms, re.multiply(d), im.negate().multiply(d));
    }

    public SMSField4 finExp() {
    	SMSField4 f = this;
        // p^4 - 1 = (p^2 - 1)*(p^2 + 1)
        try {
            f = f.frobenius(2).multiply(f.inverse()); // f = f^(p^2 - 1)
        } catch (ArithmeticException x) {
            f = this; // this can only happen when this instance is not invertible, i.e. zero
        }
        //*
//        assert (f.inverse().equals(f.frobenius(2)));
        //*/
        // (p^2+1)/n = p + t
        SMSField4 h = f;
        BigInteger x = sms.t;
        //*
        if (x.signum() < 0) {
        	h = h.frobenius(2);
        	x = x.negate();
        }
        SMSField4 u = h;
        for (int i = x.bitLength()-2; i >= 0; i--) {
            u = u.square();
            if (x.testBit(i)) {
                u = u.multiply(h);
            }
        }
        f = f.frobenius(1).multiply(u);
        //*/
        return f;
    }

    /**
     * Compute this^ks + Y^kr.
     *
     */
    public SMSField4 simultaneous(BigInteger ks, BigInteger kr, SMSField4 Y) {
        SMSField4[] hV = new SMSField4[16];
        SMSField4 P = this;
        if (ks.signum() < 0) {
        	ks = ks.negate();
        	P = P.frobenius(2);
        }
        if (kr.signum() < 0) {
        	kr = kr.negate();
        	Y = Y.frobenius(2);
        }
        hV[0] = sms.Fp4_1;
        hV[1] = P;
        hV[2] = Y;
        hV[3] = P.multiply(Y);
        for (int i = 4; i < 16; i += 4) {
            hV[i] = hV[i >> 2].square();
            hV[i + 1] = hV[i].multiply(hV[1]);
            hV[i + 2] = hV[i].multiply(hV[2]);
            hV[i + 3] = hV[i].multiply(hV[3]);
        }
        int t = Math.max(kr.bitLength(), ks.bitLength());
        SMSField4 R = sms.Fp4_1;
        for (int i = (((t + 1) >> 1) << 1) - 1; i >= 0; i -= 2) {
            int j = (kr.testBit(i  ) ? 8 : 0) |
                    (ks.testBit(i  ) ? 4 : 0) |
                    (kr.testBit(i-1) ? 2 : 0) |
                    (ks.testBit(i-1) ? 1 : 0);
            R = R.square().square().multiply(hV[j]);
        }
        return R;
    }

	//*
	public SMSField4 exp(BigInteger k) {
		// t > 0:
		// k = k_0 + k_1*(t - 1) => k*Q = k_0*Q + k_1*(t - 1)*Q = k_0*Q + k_1*Q' => g^k = g^{k_0}*(g^p)^{k_1}
		// t < 0:
		// k = k_0 + k_1*(1 - t) => k*Q = k_0*Q - k_1*(t - 1)*Q = k_0*Q - k_1*Q' => g^k = g^{k_0}*(g^p^3)^{k_1} 
		BigInteger m = sms.t.subtract(_1).abs();
		SMSField4 g = this;
		if (k.signum() < 0) {
			g = g.frobenius(2);
			k = k.negate();
		}
		k = k.mod(sms.n);
		SMSField4 y = g.frobenius((sms.t.signum() > 0) ? 1 : 3);
		return simultaneous(k.mod(m), k.divide(m), y);
	}
	//*/
	/*
    public SMSField4 exp(BigInteger k) {
        SMSField4 u = sms.Fp4_1;
		if (k.signum() < 0) {
			u = u.negate();
			k = k.negate();
		}
		k = k.mod(sms.n);
        for (int i = k.bitLength()-1; i >= 0; i--) {
            u = u.square();
            if (k.testBit(i)) {
                u = u.multiply(this);
            }
        }
        return u;
    }
    //*/

    public SMSField4 simultaneous(BigInteger kP, SMSField4 P, BigInteger kQ, SMSField4 Q, BigInteger kR, SMSField4 R, BigInteger kS, SMSField4 S) {
        SMSField4[] hV = new SMSField4[16];
        if (kP.signum() < 0) {
        	kP = kP.negate(); P = P.frobenius(2);
        }
		kP = kP.mod(sms.n);
        if (kQ.signum() < 0) {
        	kQ = kQ.negate(); Q = Q.frobenius(2); 
        }
		kQ = kQ.mod(sms.n);
        if (kR.signum() < 0) {
        	kR = kR.negate(); R = R.frobenius(2);
        }
		kR = kR.mod(sms.n);
        if (kS.signum() < 0) {
        	kS = kS.negate(); S = S.frobenius(2);
        }
		kS = kS.mod(sms.n);
        hV[0] = sms.Fp4_1;
        hV[1] = P; hV[2] = Q; hV[4] = R; hV[8] = S;
		for (int i = 2; i < 16; i <<= 1) {
			for (int j = 1; j < i; j++) {
		        hV[i + j] = hV[i].multiply(hV[j]);
			}
		}
        int t = Math.max(Math.max(kP.bitLength(), kQ.bitLength()), Math.max(kR.bitLength(), kS.bitLength()));
        SMSField4 V = sms.Fp4_1;
        for (int i = t - 1; i >= 0; i--) {
            int j = (kS.testBit(i) ?   8 : 0) |
                    (kR.testBit(i) ?   4 : 0) |
                    (kQ.testBit(i) ?   2 : 0) |
                    (kP.testBit(i) ?   1 : 0);
            V = V.square().multiply(hV[j]);
        }
        return V;
    }

    public SMSField4 fastSimultaneous(BigInteger ks, BigInteger kr, SMSField4 y) {
		BigInteger m = sms.t.subtract(_1).abs();
    	SMSField4 g = this;
		if (ks.signum() < 0) {
			g = g.frobenius(2);
			ks = ks.negate();
		}
		if (kr.signum() < 0) {
			y = y.frobenius(2);
			kr = kr.negate();
		}
		SMSField4 gg = g.frobenius((sms.t.signum() > 0) ? 1 : 3);
		SMSField4 yy = y.frobenius((sms.t.signum() > 0) ? 1 : 3);
		return simultaneous(ks.mod(m), g, ks.divide(m), gg, kr.mod(m), y, kr.divide(m), yy);
    }

	public SMSField4 norm2() {
		return this.multiply(frobenius(2));
	}

	/*
     * (x + yz)(x - yz) = x^2 - y^2(1+i) = 1 => x = sqrt(y^2(1+i) + 1), y = sqrt((x^2 - 1)/(1+i))
	 */
	/*
	public SMSField4 expand(SMSField2 y) {
		return y.square().multiplyV().add(_1).sqrt();
	}
	//*/

	/**
	 * Map this field element, assumed to have unit norm, unambiguously to a byte array.
	 */
	public byte[] toByteArray() {
		if (!norm2().isOne()) {
            throw new IllegalArgumentException(elementNotOnTorus);
		}
		int len = (sms.p.bitLength() + 7)/8;
		//System.out.println("byte length = " + len);
        byte[] buf = new byte[1 + 2*len];
        for (int i = 0; i < buf.length; i++) {
        	buf[i] = (byte)0;
        }
        byte[] vre = im.re.toByteArray();
        //String hex = "0123456789abcdef";
        //System.out.print("vre:" + vre.length + " =   "); for (int i = 0; i < vre.length; i++) { System.out.print(hex.charAt((vre[i] & 0xff) >>> 4)); System.out.print(hex.charAt(vre[i] & 15)); } System.out.println();
        byte[] vim = im.im.toByteArray();
        //System.out.print("vim:" + vim.length + " =                                   "); for (int i = 0; i < vim.length; i++) { System.out.print(hex.charAt((vim[i] & 0xff) >>> 4)); System.out.print(hex.charAt(vim[i] & 15)); } System.out.println();
        buf[0] = (byte)(re.re.testBit(0) ? 1 : 0);
        //System.out.println("vre[" + vre.length + "] from " + ((vre.length <= len) ? 0 : 1) + " onto buf[" + buf.length + "] from " + ((vre.length <= len ? len - vre.length : 0) + 1) + " for " + ((vre.length <= len) ? vre.length : len));
	    // System.arraycopy(vre, (vre.length <= len) ? 0 : 1, buf, (vre.length <= len ? len - vre.length : 0) + 1,       (vre.length <= len) ? vre.length : len);
        if (vre.length <= len) {
	        System.arraycopy(vre, 0, buf, 1 + (len - vre.length), vre.length);
        } else {
	        System.arraycopy(vre, 1, buf, 1,                      len);
        }
        //System.out.println("vim[" + vim.length + "] from " + ((vim.length <= len) ? 0 : 1) + " onto buf[" + buf.length + "] from " + ((vim.length <= len ? len - vim.length : 0) + 1 + len) + " for " + ((vim.length <= len) ? vim.length : len));
        //System.arraycopy(vim, (vim.length <= len) ? 0 : 1, buf, (vim.length <= len ? len - vim.length : 0) + 1 + len, (vim.length <= len) ? vim.length : len);
        if (vim.length <= len) {
	        System.arraycopy(vim, 0, buf, 1 + len + (len - vim.length), vim.length);
        } else {
	        System.arraycopy(vim, 1, buf, 1 + len,                      len);
        }
        //System.out.print("buf:" + buf.length + " = "); for (int i = 0; i < buf.length; i++) { System.out.print(hex.charAt((buf[i] & 0xff) >>> 4)); System.out.print(hex.charAt(buf[i] & 15)); } System.out.println();
        return buf;
	}

    public String toString() {
        return "(" + re + ", " + im + ")";
    }
}
