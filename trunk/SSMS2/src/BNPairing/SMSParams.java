/**
 * SMSParams.java
 *
 * Parameters for MNT4 pairing-friendly elliptic curves.
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

public class SMSParams {

    /**
     * Convenient BigInteger constants
     */
    static final BigInteger
        _0 = BigInteger.valueOf(0L),
        _1 = BigInteger.valueOf(1L),
        _2 = BigInteger.valueOf(2L),
        _3 = BigInteger.valueOf(3L),
        _4 = BigInteger.valueOf(4L),
        _5 = BigInteger.valueOf(5L),
        _6 = BigInteger.valueOf(6L),
        _7 = BigInteger.valueOf(7L);

    /**
     * Rabin-Miller certainty used for primality testing
     */
    static final int PRIMALITY_CERTAINTY = 20;

    /**
     * Invalid parameters error message
     */
    public static final String invalidParams =
        "The specified parameters do not properly define a suitable/supported MNT4 curve";

    /**
     * Size of the underlying finite field GF(p)
     */
    BigInteger p;

    /**
     * Trace of the Frobenius endomorphism
     **/
    BigInteger t;

    /**
     * Prime curve order
     */
    BigInteger n;

    /**
     * Curve equation coefficient
     */
    BigInteger b;

    /**
     * Y-coordinate of the base point of the standard MNT4 curve
     */
    BigInteger y;

    SMSField2 xt, yt;

    BigInteger sqrtExponent;
    BigInteger sqrtExponent2;
    SMSField2  Fp2_0;
    SMSField2  sqrtI;

    /**
     * z^p = sigma*(1+i)*z
     */
    BigInteger sigma;

    /**
     * Cofactor of twist (twist order = h*n), h = p + 1 + t
     */
    BigInteger h;

    static int nextValid(int fieldSize) {
        if (fieldSize < 117) {
            return 117;
        } else if (fieldSize < 129) {
            return 129;
        } else if (fieldSize < 135) {
            return 135;
        } else if (fieldSize < 147) {
            return 147;
        } else if (fieldSize < 161) {
            return 161;
        } else if (fieldSize < 226) {
            return 226;
        } else if (fieldSize < 256) {
            return 256;
        } else {
            return 0;
        }
    }

    public SMSParams(int fieldBits) {
        switch (fieldBits) {
        case 117: // D = -462403
            t = new BigInteger("-304864921449088313");
            b = new BigInteger("31121470934510494488245967187577325");
            y = new BigInteger("56292402516863337378828812307438954");
            xt = new SMSField2(this,
                new BigInteger("78395026638807937052920679361402331"),
                new BigInteger("52537908634577878089901944864466474"),
                false);
            yt = new SMSField2(this,
                new BigInteger("68464511366483090937790466286475624"),
                new BigInteger("91497796781333548440728748004444260"),
                false);
            break;
        case 129: // D = -47570707
            t = new BigInteger("-22136366182452723033");
            b = new BigInteger("252377254009246332426505119296008362547");
            y = new BigInteger("218126525311951272471089429095328648845");
            xt = new SMSField2(this,
                new BigInteger("187857876930789708038436943761626462223"),
                new BigInteger("223185933710929548500073008916819337145"),
                false);
            yt = new SMSField2(this,
                new BigInteger("103715142622411939223161887329457242660"),
                new BigInteger("062591760479090648289683294263695495145"),
                false);
            break;
        case 135: // D = -1430907
            t = new BigInteger("-169660810666261163289");
            b = new BigInteger("24807843391568719908734348605157423212939");
            y = new BigInteger("16355637756967184508238239125776688563658");
            xt = new SMSField2(this,
                new BigInteger("04348881663757214965967815766994477602534"),
                new BigInteger("21147006063066644925046565890176206922060"),
                false);
            yt = new SMSField2(this,
                new BigInteger("20510308103419307195603386923738858581225"),
                new BigInteger("27562081027876491138906525977130896208102"),
                false);
            break;
        /*
        case 137: // D = -391304587
            t = new BigInteger("378897773699074047511");
            b = new BigInteger("");
            y = new BigInteger("");
            break;
        case 142: // D = -229597387
            t = new BigInteger("1953734246365012238191");
            b = new BigInteger("");
            y = new BigInteger("");
            break;
        //*/
        case 147: // D = -41325827
            t = new BigInteger("11577304265826173336527");
            b = new BigInteger("044281454923558763198243730094184005614178755");
            y = new BigInteger("056212309106875791698353572073425183648018644");
            xt = new SMSField2(this,
                new BigInteger("126705774106441840799988279270833365326629794"),
                new BigInteger("086741658726798463731314602400947132495895206"),
                false);
            yt = new SMSField2(this,
                new BigInteger("021787549805670209290875174518598327863063098"),
                new BigInteger("118752582026007019424079970760213199339462900"),
                false);
            break;
        case 161: // D = -12574563
            t = new BigInteger("1397283740559671498201551");
            b = new BigInteger("1462009490940594328986778140516988886394372480150");
            y = new BigInteger("1855534507375748784222801535448695887723137520448");
            xt = new SMSField2(this,
                new BigInteger("1156056284970151683367858181928362513897833464601"),
                new BigInteger("384955184099209496428909786345655656603018396422"),
                false);
            yt = new SMSField2(this,
                new BigInteger("1910521880975528072082942167755034467792414142066"),
                new BigInteger("1950442312554671891354843625385252584246506330969"),
                false);
            break;
        /*
        case 165: // D = -397700587
            p = new BigInteger("40865968649087981093212371905982073176808678624843");
            n = new BigInteger("40865968649087981093212378298631652896315022435237");
            b = new BigInteger("");
            y = new BigInteger("");
            break;
        case 175: // D = -145196139
            p = new BigInteger("34597786650769143491213719202899048803736534505230651");
            n = new BigInteger("34597786650769143491213719016894246041095097673960677");
            b = new BigInteger("");
            y = new BigInteger("");
            break;
        case 187: // D = -116799691
            p = new BigInteger("144476043753595291180666302368370486843317130048990042091");
            n = new BigInteger("144476043753595291180666302380390305633698093657532145957");
            b = new BigInteger("");
            y = new BigInteger("");
            break;
        case 202: // D = -277523907
            p = new BigInteger("4724110995170910429404558945792484165834329125164737774696203");
            n = new BigInteger("4724110995170910429404558945790310663822558510209964974272677");
            b = new BigInteger("");
            y = new BigInteger("");
            break;
        //*/
        case 226: // D = -16460547
            t = new BigInteger("9964083456805723098430155805814407");
            b = new BigInteger("83706149477408836211957006881178006672942824671022064444986347235881");
            y = new BigInteger("17346235989379807611950078136872167280557282706151781471076522946876");
            xt = new SMSField2(this,
                new BigInteger("67373520565662410739389725977776745398369707494661337917458976125526"),
                new BigInteger("59547569509813261402534827814909920945813003237557114798362980564868"),
                false);
            yt = new SMSField2(this,
                new BigInteger("36012319453583492419621527315475160466616029988738592025318807698380"),
                new BigInteger("71520561716789587817485421569302402433011169080184148593897435298553"),
                false);
            break;
        /*
        case 242: // D = -242449747
            t = new BigInteger("-2500473058596708182541284074237471809");
            //p = new BigInteger("6252365516767976832221314255822262703439095030469107551069016666307204291");
            //n = new BigInteger("6252365516767976832221314255822262705939568089065815733610300740544676101");
            b = new BigInteger("");
            y = new BigInteger("");
            break;
        //*/
        case 256: // D = -56415963
            t = new BigInteger("333788735613598007608249400336693300671");
            //p = new BigInteger("111414920022524430892658400746600150808609303168288123734064824116395715749571");
            //n = new BigInteger("111414920022524430892658400746600150808275514432674525726456574716059022448901");
            b = new BigInteger("35369269986011139699238013756346314081616079494832438937442404572459541090733");
            y = new BigInteger("87289916865357820745733302418856691553786390496858282286836977146181557018494");
            xt = new SMSField2(this,
                new BigInteger("105337481934967801076292386721744521935681235448757435764851103644026024000672"),
                new BigInteger("49203010393104901774668691606983647499177213115295632144396633221483864621233"),
                false);
            yt = new SMSField2(this,
                new BigInteger("75295584696966240491178984578066339427720828468650094710176099737595435242694"),
                new BigInteger("89652246708429732955018751668459974566380836093355791683795521455490454672379"),
                false);
            break;
        default:
            throw new IllegalArgumentException(invalidParams);
        }
        p = t.pow(2).subtract(t).add(_1);
        if (p.mod(_4).intValue() != 3) {
            throw new IllegalArgumentException("LOGIC ERROR!!!!!");
        }
        if (!p.isProbablePrime(PRIMALITY_CERTAINTY)) {
            throw new IllegalArgumentException("LOGIC ERROR!!!!!");
        }
        n = p.add(_1).subtract(t);
        if (!n.isProbablePrime(PRIMALITY_CERTAINTY)) {
            throw new IllegalArgumentException("LOGIC ERROR!!!!!");
        }
        //System.out.println("t = new BigInteger(\"" + t + "\");");
        h = p.add(_1).add(t);
        sqrtExponent = p.add(_1).shiftRight(2); // (p + 1)/4
        sqrtExponent2 = p.multiply(p).add(_7).shiftRight(4); // (p^2 + 7)/16
        sigma = _2.modInverse(p).modPow(sqrtExponent, p); // 1/sqrt(2);
        sqrtI = new SMSField2(this, sigma, sigma, false); // sqrt(i) = (1+i)/sqrt(2) = (1+i)*sigma
        Fp2_0 = new SMSField2(this);
    }

    /**
     * Compute a square root of v (mod p) where p = 3 (mod 4).
     *
     * @return  a square root of v (mod p) if one exists, or null otherwise.
     *
     * @exception   IllegalArgumentException    if the size p of the underlying finite field does not satisfy p = 3 (mod 4).
     */
    BigInteger sqrt(BigInteger v) {
    	/*
        if (!p.testBit(1)) {
            throw new IllegalArgumentException("This implementation is optimized for, and only works with, prime fields GF(p) where p = 3 (mod 4)");
        }
        //*/
        if (v.signum() == 0) {
            return _0;
        }
        BigInteger r = v.modPow(sqrtExponent, p); // r = v^{(p + 1)/4}
        // test solution:
        return r.multiply(r).subtract(v).mod(p).signum() == 0 ? r : null;
    }

}