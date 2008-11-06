/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.protocol;

import br.usp.larc.smspairing.SMSCurve;
import br.usp.larc.smspairing.SMSCurve2;
import br.usp.larc.smspairing.SMSField4;
import br.usp.larc.smspairing.SMSPairing;
import br.usp.larc.smspairing.SMSParams;
import br.usp.larc.smspairing.SMSPoint;
import br.usp.larc.smspairing.SMSPoint2;
import java.util.Hashtable;
import pseudojava.BigInteger;

/**
 * Funciona como um repositório de parâmetros pré-definidos.
 */
public class BDCPSParameters {

    private static Hashtable allInstances = new Hashtable();
    //params = (k; n;G1;G2;GT ; e; P; Q; g; Ppub; h0; h1; h2; h3).
    
    //Estes são visíveis por todas as classes, inclusive de aplicação
    public final int K;
    public final byte[] PPubBytes;
    public final BigInteger N;
    
    
    //Estes são visíveis somente pelas classes neste pacote:
    //Vale muito a pena manter aqui, evita ficar calculando toda
    //vez que instancia um BDCPSClient ou BDCPSAuthority
    protected final SMSParams SMSPARAMS;
    protected final SMSPoint P;
    protected final SMSPoint PPub;
    protected final SMSPoint2 Q;
    protected final SMSField4 g;
    protected final SMSCurve E; //G1
    protected final SMSCurve2 E2; //G2
    protected final SMSPairing PAIR;

    /**
     * Gera os parâmetros (k; n;G1;G2;GT ; e; P; Q; g; Ppub; h0; h1; h2; h3)
     * a partir de um K dado.
     * @param K O número de bits do primo a ser usado
     */
    private BDCPSParameters(int k) {
        this.K = k;
        //KGB_TEL = "1174749679";

        SMSPARAMS = new SMSParams(k);
        N = SMSPARAMS.getN();
        
        E = new SMSCurve(SMSPARAMS);
        E2 = new SMSCurve2(E);

        P = E.getG();
        Q = E2.getGt();
        PAIR = new SMSPairing(E2);
        g = PAIR.ate(Q, P);

        //Aqui precisamos definir os bytes PPub... Não podemos calculá-lo como P.multiply(s)
        //pois não temos o s nesse escopo...
        //Os valores foram pré-calculados na KGB e fornecidos para utilização.

        // Na forma comprimida

        switch (k) {
            case 80: // D = -67335027
                PPubBytes = /* 80 */ new byte[]{(byte) 0x02, (byte) 0x1f, (byte) 0x54, (byte) 0x8d, (byte) 0xdd, (byte) 0xa4, (byte) 0xb7, (byte) 0x31, (byte) 0x5b, (byte) 0x27, (byte) 0x10};
                break;
            case 96: // D = -60694251
                PPubBytes = /* 96 */ new byte[]{(byte) 0x02, (byte) 0xa2, (byte) 0x4a, (byte) 0x9d, (byte) 0x3a, (byte) 0x87, (byte) 0x53, (byte) 0x6d, (byte) 0xbc, (byte) 0xdc, (byte) 0x3a, (byte) 0x49, (byte) 0xd3};
                break;
            case 104: // D = -33960243
                PPubBytes = /* 104 */ new byte[]{(byte) 0x02, (byte) 0x28, (byte) 0x9b, (byte) 0xb0, (byte) 0x84, (byte) 0x53, (byte) 0x48, (byte) 0x49, (byte) 0xf4, (byte) 0xbf, (byte) 0x79, (byte) 0x73, (byte) 0x74, (byte) 0x09};
                break;
            case 112: // D = -1415179891
                PPubBytes = /* 112 */ new byte[]{(byte) 0x02, (byte) 0x7a, (byte) 0x1e, (byte) 0x91, (byte) 0x6d, (byte) 0xaa, (byte) 0x60, (byte) 0x53, (byte) 0x29, (byte) 0x3e, (byte) 0x45, (byte) 0x35, (byte) 0x6b, (byte) 0x39, (byte) 0xac};
                break;
            case 117: // D = -462403
                PPubBytes = /* 117 */ new byte[]{(byte) 0x03, (byte) 0x09, (byte) 0xf8, (byte) 0xc6, (byte) 0xf0, (byte) 0xa6, (byte) 0xab, (byte) 0xd8, (byte) 0xfe, (byte) 0x90, (byte) 0x9d, (byte) 0x73, (byte) 0x20, (byte) 0xb9, (byte) 0xc3, (byte) 0x20};
                break;
            //case 119: // D = -1414737123
            //break;
            case 127: // D = -107431123
                PPubBytes = /* 127 */ new byte[]{(byte) 0x03, (byte) 0x1a, (byte) 0x63, (byte) 0xe7, (byte) 0xc0, (byte) 0x43, (byte) 0xa7, (byte) 0x5f, (byte) 0x6b, (byte) 0x21, (byte) 0xd9, (byte) 0xd4, (byte) 0xc2, (byte) 0x5c, (byte) 0x95, (byte) 0xf1, (byte) 0xf3};
                break;
            //case 135: // D = -1430907
            //    break;
            case 142: // D = -229597387
                PPubBytes = /* 142 */ new byte[]{(byte) 0x03, (byte) 0x0d, (byte) 0xc3, (byte) 0xe1, (byte) 0x50, (byte) 0x74, (byte) 0xd6, (byte) 0x1b, (byte) 0x4d, (byte) 0xe9, (byte) 0x0b, (byte) 0xa9, (byte) 0x94, (byte) 0xa1, (byte) 0xc6, (byte) 0x81, (byte) 0x3a, (byte) 0x21, (byte) 0x3a};
                break;
            //case 147: // D = -41325827

            //break;
            case 160: // D = -1173931627
                PPubBytes = /* 160 */ new byte[]{(byte) 0x03, (byte) 0x16, (byte) 0x81, (byte) 0x68, (byte) 0xfb, (byte) 0x36, (byte) 0x7a, (byte) 0x92, (byte) 0x6b, (byte) 0x3a, (byte) 0x81, (byte) 0xb0, (byte) 0x7e, (byte) 0xf8, (byte) 0x38, (byte) 0xff, (byte) 0x9d, (byte) 0x7d, (byte) 0xaf, (byte) 0xfe, (byte) 0x68};
                break;
            case 176: // D = -1611918043
                PPubBytes = /* 176 */ new byte[]{(byte) 0x02, (byte) 0x99, (byte) 0x29, (byte) 0x16, (byte) 0x49, (byte) 0x0c, (byte) 0x2d, (byte) 0x51, (byte) 0xcd, (byte) 0xb0, (byte) 0xd3, (byte) 0xc6, (byte) 0x9c, (byte) 0xdf, (byte) 0x07, (byte) 0x20, (byte) 0x1f, (byte) 0xaa, (byte) 0x78, (byte) 0xa1, (byte) 0x24, (byte) 0x9c, (byte) 0x26};
                break;

            case 187: // D = -116799691
                PPubBytes = /* 187 */ new byte[]{(byte) 0x02, (byte) 0x02, (byte) 0x3e, (byte) 0x47, (byte) 0x2b, (byte) 0x90, (byte) 0x96, (byte) 0xe3, (byte) 0xb0, (byte) 0xe3, (byte) 0x27, (byte) 0x72, (byte) 0x82, (byte) 0x0a, (byte) 0x81, (byte) 0xc7, (byte) 0xb4, (byte) 0x6e, (byte) 0xc4, (byte) 0xdf, (byte) 0xd3, (byte) 0x3e, (byte) 0x47, (byte) 0x9d, (byte) 0xef};
                break;

            //case 202: // D = -277523907
            //    break;
            case 226: // D = -16460547
                PPubBytes = /* 226 */ new byte[]{(byte) 0x03, (byte) 0x02, (byte) 0xe9, (byte) 0x2c, (byte) 0x3a, (byte) 0x9d, (byte) 0xe5, (byte) 0x80, (byte) 0xea, (byte) 0x0f, (byte) 0xef, (byte) 0x6c, (byte) 0xb9, (byte) 0xa8, (byte) 0x7b, (byte) 0x4d, (byte) 0x21, (byte) 0xa2, (byte) 0x4b, (byte) 0x82, (byte) 0xf8, (byte) 0xf5, (byte) 0xf7, (byte) 0x8f, (byte) 0x81, (byte) 0x20, (byte) 0x36, (byte) 0x76, (byte) 0xa6, (byte) 0xa0};
                break;

            //case 242: // D = -242449747
            //break;
            case 256: // D = -56415963
                PPubBytes = /* 256 */ new byte[]{(byte) 0x03, (byte) 0xc7, (byte) 0x1f, (byte) 0xbd, (byte) 0xf8, (byte) 0xca, (byte) 0x09, (byte) 0xa6, (byte) 0xbc, (byte) 0xa3, (byte) 0x74, (byte) 0xc7, (byte) 0x9b, (byte) 0x37, (byte) 0x8f, (byte) 0x1c, (byte) 0x73, (byte) 0x95, (byte) 0x3d, (byte) 0xba, (byte) 0x64, (byte) 0x93, (byte) 0xef, (byte) 0xe2, (byte) 0x71, (byte) 0x5b, (byte) 0x2b, (byte) 0xe5, (byte) 0xc4, (byte) 0xa4, (byte) 0xd2, (byte) 0xba, (byte) 0xa9};
                break;
            case 272: // D = -293470419
                PPubBytes = /* 272 */ new byte[]{(byte) 0x03, (byte) 0x87, (byte) 0xad, (byte) 0x9d, (byte) 0x5c, (byte) 0xd3, (byte) 0xc5, (byte) 0xee, (byte) 0xe5, (byte) 0x93, (byte) 0x92, (byte) 0xdd, (byte) 0x9f, (byte) 0xe3, (byte) 0x3d, (byte) 0x16, (byte) 0x90, (byte) 0xa7, (byte) 0x6c, (byte) 0x4d, (byte) 0x50, (byte) 0x3c, (byte) 0xc2, (byte) 0xa7, (byte) 0xd8, (byte) 0x3a, (byte) 0x43, (byte) 0x0e, (byte) 0xa2, (byte) 0xd3, (byte) 0x91, (byte) 0x82, (byte) 0xd9, (byte) 0xdf, (byte) 0x40};
                break;
            case 313: // D = -1475251                
                PPubBytes = /* 313 */ new byte[]{(byte) 0x02, (byte) 0x01, (byte) 0x32, (byte) 0xdc, (byte) 0x05, (byte) 0x30, (byte) 0x73, (byte) 0x7d, (byte) 0x34, (byte) 0x84, (byte) 0xeb, (byte) 0xfa, (byte) 0x98, (byte) 0x6f, (byte) 0x62, (byte) 0x75, (byte) 0xa7, (byte) 0xca, (byte) 0x01, (byte) 0x7d, (byte) 0x9b, (byte) 0x4a, (byte) 0x7a, (byte) 0xc7, (byte) 0x74, (byte) 0x45, (byte) 0xe7, (byte) 0x7a, (byte) 0xdb, (byte) 0xce, (byte) 0x54, (byte) 0x8d, (byte) 0xef, (byte) 0x5a, (byte) 0xb4, (byte) 0x38, (byte) 0x17, (byte) 0x00, (byte) 0x78, (byte) 0x26, (byte) 0xa5};
                break;
            //case 463: // D = -15411979
            //break;

            default:
                throw new IllegalArgumentException("The given K = " + k +
                        " is not supported by the protocol.");
        }
        PPub = new SMSPoint(E, PPubBytes);

    }

    /**
     * Pega uma instância contendos os parâmetros dados para
     * um dado K
     * @param K O número de bits do primo a ser usado
     */
    public static BDCPSParameters getInstance(int k) {
        if (allInstances.containsKey(new Integer(k))) {
            return (BDCPSParameters) allInstances.get(new Integer(k));

        } else {

            //Tentar gerar os parâmetros
            BDCPSParameters newInstance = new BDCPSParameters(k);
            //Sucesso! Coloca no Hashtable para futuras referências
            allInstances.put(new Integer(k), newInstance);
            return newInstance;
        }

    }
    }


