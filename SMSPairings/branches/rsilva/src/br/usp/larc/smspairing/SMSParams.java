package br.usp.larc.smspairing;
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

import java.math.BigInteger;

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
    private BigInteger n;

    /**
     * Curve equation coefficient
     */
    BigInteger b;

    BigInteger y;

    SMSField2 xt, yt;

    BigInteger sqrtExponent;
    BigInteger sqrtExponent2;
    SMSField2  Fp2_0;
    SMSField2  Fp2_1;
    SMSField2  sqrtI;
    SMSField4  Fp4_0;
    SMSField4  Fp4_1;

    /**
     * z^p = sigma*(1+i)*z
     */
    BigInteger sigma;

    /**
     * Cofactor of twist (twist order = h*n), h = p + 1 + t
     */
    BigInteger h;

    static int nextValid(int fieldSize) {
        if (fieldSize == -1) {
            return 313;
        } else if (fieldSize <  80) {
            return  80;
        } else if (fieldSize <  96) {
            return  96;
        } else if (fieldSize < 104) {
            return 104;
        } else if (fieldSize < 112) {
            return 112;
        } else if (fieldSize < 117) {
            return 117;
        } else if (fieldSize < 127) {
            return 127;
        /*
        } else if (fieldSize < 135) {
            return 135;
        //*/
        } else if (fieldSize < 142) {
            return 142;
        /*
        } else if (fieldSize < 147) {
            return 147;
        //*/
        } else if (fieldSize < 160) {
            return 160;
        } else if (fieldSize < 176) {
            return 176;
        } else if (fieldSize < 187) {
            return 187;
        } else if (fieldSize < 226) {
            return 226;
        } else if (fieldSize < 256) {
            return 256;
        } else if (fieldSize < 272) {
            return 272;
        } else if (fieldSize < 313) {
            return 313;
        /*
        } else if (fieldSize < 463) {
            return 463;
        //*/
        } else {
            return 0;
        }
    }

    public SMSParams(int fieldBits) {
        Fp2_0 = new SMSField2(this);
        Fp2_1 = new SMSField2(this, _1);
        Fp4_0 = new SMSField4(this);
        Fp4_1 = new SMSField4(this, Fp2_1, Fp2_0);
        SMSField2 Fp2_2 = new SMSField2(this, _2);
    	xt = Fp2_1;
    	yt = null;
        switch (fieldBits) {
        case 80: // D = -67335027
            t = new BigInteger("-841854926633");
            b = new BigInteger("460966726800183450205057");
            y = new BigInteger("123313000842242340015870");
            xt = new SMSField2(this, // 1
                new BigInteger("598921402059544952380024"),
                new BigInteger("213530257852845949154527"),
                false);
            yt = new SMSField2(this,
                new BigInteger("517041540359404398114356"),
                new BigInteger("442549197429105163615254"),
                false);
            sigma = new BigInteger("139255886142836945741742");
            break;
        case 96: // D = -60694251
            t = new BigInteger("-267453927933545");
            b = new BigInteger("17313178757619190697893958102");
            y = new BigInteger("65177459163592944440167013552");
            xt = new SMSField2(this,
                new BigInteger("50149735131273200010179305099"),
                new BigInteger("60104820090668377467617604484"),
                false);
            yt = new SMSField2(this,
                new BigInteger("21416312425263696821815550293"),
                new BigInteger("14336564418191630798528712593"),
                false);
            sigma = new BigInteger("46877523093284160428012151538");
            break;
        case 104: // D = -33960243
            t = new BigInteger("4333619003946607");
            b = new BigInteger("12106083922639671494194173492402");
            y = new BigInteger("3012866152472675203177706708798");
            xt = new SMSField2(this,
                new BigInteger("8229207798427751456705759904577"),
                new BigInteger("5892753553979809278480984128737"),
                false);
            yt = new SMSField2(this,
                new BigInteger("7812712855500579095675654588187"),
                new BigInteger("5367076765048413917818226428674"),
                false);
            sigma = new BigInteger("15803502785282446967607210858688");
            break;
        case 112: // D = -1415179891
            t = new BigInteger("-55856128349083145");
            b = new BigInteger("841460990537463346745088037873957");
            y = new BigInteger("1981444268186026891561432148509706");
            xt = new SMSField2(this, // 2
                new BigInteger("2801225588347379995389353626097021"),
                new BigInteger("2639025597585491610589355067425393"),
                false);
            yt = new SMSField2(this,
                new BigInteger("543137205359413322275695488649824"),
                new BigInteger("2946530608556171928341547263743961"),
                false);
            sigma = new BigInteger("594039690174421228030493197869651");
            break;
        case 117: // D = -462403
            t = new BigInteger("-304864921449088313");
            b = new BigInteger("31121470934510494488245967187577325");
            y = new BigInteger("56292402516863337378828812307438954");
            xt = new SMSField2(this,
                new BigInteger("6146065379367497680518710797348654"),
                new BigInteger("66898889437970446762577741158561509"),
                false);
            yt = new SMSField2(this,
                new BigInteger("66407045540863335626224951072213445"),
                new BigInteger("10163541967334304133207487244721979"),
                false);
            sigma = new BigInteger("30056898267551570624002911862121915");
            break;
		/*
        case 119: // D = -1414737123
            t = new BigInteger("-627325778764433369");
            b = new BigInteger("");
            y = new BigInteger("");
            xt = new SMSField2(this,
                new BigInteger(""),
                new BigInteger(""),
                false);
            yt = new SMSField2(this,
                new BigInteger(""),
                new BigInteger(""),
                false);
            sigma = new BigInteger("30056898267551570624002911862121915");
            break;
		//*/
        case 127: // D = -107431123
           t = new BigInteger("10303860181612693647");
           b = new BigInteger("95327690609124801206622861962149340547");
           y = new BigInteger("72928337084280004736095111530932862556");
           xt = new SMSField2(this, // 2
               new BigInteger("94867768503401758497379858145498615744"),
               new BigInteger("8598618832592332672629961942767400708"),
               false);
           yt = new SMSField2(this,
               new BigInteger("62061264838363688300602631325581895796"),
               new BigInteger("70616427083892872568577150301404461062"),
               false);
           sigma = new BigInteger("62553386649688631958684015821190789268");
           break;
        /*
        case 135: // D = -1430907
            t = new BigInteger("-169660810666261163289");
            b = new BigInteger("24807843391568719908734348605157423212939");
            y = new BigInteger("16355637756967184508238239125776688563658");
            xt = new SMSField2(this, // 1
                new BigInteger("15195253008960900918783567930836758147816"),
                new BigInteger("18573218104699913749054806725117012126607"),
                false);
            yt = new SMSField2(this,
                new BigInteger("17146816976801309380011407317987650817556"),
                new BigInteger("26888330029404710877513018687628425181601"),
                false);
            sigma = new BigInteger("20298680851576595688105367197524417440174");
            break;
        //*/
        case 142: // D = -229597387
            t = new BigInteger("1953734246365012238191");
            b = new BigInteger("1837108823282327304706555928689846335012146");
            y = new BigInteger("2371113335409409575337030149405245917907553");
            xt = new SMSField2(this, // 2
                new BigInteger("2275105146250106370446188128983471474589448"),
                new BigInteger("2294085508780598182821919645597663206858886"),
                false);
            yt = new SMSField2(this,
                new BigInteger("795404742572475183108027518299599384993398"),
                new BigInteger("3220950364027492724368067442221169657178207"),
                false);
            sigma = new BigInteger("-2974539270140279727576143514063397190398109");
            break;
        /*
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
            sigma = new BigInteger("044877086671837870917052807273046110905586270");
            break;
        //*/
        case 160: // D = -1173931627
            //p = new BigInteger("730996464809526906653171213409755627912276816323");
            //n = new BigInteger("730996464809526906653170358426443036650700061957");
            t = new BigInteger("854983312591261576754367");
            b = new BigInteger("673963145988068993043805580512096566691314649355");
            y = new BigInteger("394659321211467273134268617298519344151331791546");
            xt = new SMSField2(this, // -1
                new BigInteger("246105893558887978299396423928461769823070910467"),
                new BigInteger("148392900788822939407425538608185699263906871924"),
                false);
            yt = new SMSField2(this,
                new BigInteger("173042882893427527700994657248048434798066720626"),
                new BigInteger("541359331402387212540113347513183788065707766369"),
                false);
            sigma = new BigInteger("411021774373329311533540777776960614135413332780");
            break;
        case 176: // D = -1611918043
            t = new BigInteger("-285146733715024817582510193");
            b = new BigInteger("1147904386201424049182125214897584921458856754335784");
            y = new BigInteger("19907496442705110346657125292805444092475975045626990");
            xt = new SMSField2(this, // -1
                new BigInteger("77904876598301501703464574873906895881303166980010660"),
                new BigInteger("36759426756937166791752160138198160315255412221581169"),
                false);
            yt = new SMSField2(this,
                new BigInteger("33369039730996865469284499008965562734538474433209914"),
                new BigInteger("61263326689639294532924627117772028361918539463850456"),
                false);
            sigma = new BigInteger("62050996317897003272532081579398477273612303292259858");
            break;
        case 187: // D = -116799691
            t = new BigInteger("-12019818790380963608542103865");
            b = new BigInteger("51313662483359463506978867290140063586130968576235197182");
            y = new BigInteger("97710219743137455080236782400553778602351645303566777774");
            xt = new SMSField2(this, // -1
                new BigInteger("102714817790189225591915090931039213515216599699120312039"),
                new BigInteger("135571189224741982641978380418521614098724964947720448232"),
                false);
            yt = new SMSField2(this,
                new BigInteger("104049363541653335766079580534002205173320508479461492863"),
                new BigInteger("15745133494490396708349783806511177004155908972650786411"),
                false);
        	sigma = new BigInteger("72871331114135049245245425281014791653975754281137179192");
            break;
        /*
        case 202: // D = -277523907
            //p = new BigInteger("4724110995170910429404558945792484165834329125164737774696203");
            //n = new BigInteger("4724110995170910429404558945790310663822558510209964974272677");
            t = new BigInteger("2173502011770614954772800423527");
            b = new BigInteger("");
            y = new BigInteger("");
            xt = new SMSField2(this,
                new BigInteger(""),
                new BigInteger(""),
                false);
            yt = new SMSField2(this,
                new BigInteger(""),
                new BigInteger(""),
                false);
            sigma = new BigInteger("791636515196283771260838471714970196146710244737125148986505");
            break;
        //*/
        case 226: // D = -16460547
            t = new BigInteger("9964083456805723098430155805814407");
            b = new BigInteger("83706149477408836211957006881178006672942824671022064444986347235881");
            y = new BigInteger("17346235989379807611950078136872167280557282706151781471076522946876");
            xt = new SMSField2(this, // 1
                new BigInteger("69744917083264637509625479956460178323258731416866156362884790413450"),
                new BigInteger("15999144190126774096101934309010000929529137735884078664117118135155"),
                false);
            yt = new SMSField2(this,
                new BigInteger("26195376483506069654036644499911279015680258493523538138046899722790"),
                new BigInteger("71526173968540205534248191588131933511535023989241512962915397080992"),
                false);
            sigma = new BigInteger("13679812912780861185954281479419439388085829653034942056129401210692");
            break;
        /*
        case 242: // D = -242449747
            t = new BigInteger("-2500473058596708182541284074237471809");
            b = new BigInteger("");
            y = new BigInteger("");
            xt = new SMSField2(this,
                new BigInteger(""),
                new BigInteger(""),
                false);
            yt = new SMSField2(this,
                new BigInteger(""),
                new BigInteger(""),
                false);
            sigma = new BigInteger("");
            break;
        //*/
        case 256: // D = -56415963
            t = new BigInteger("333788735613598007608249400336693300671");
            b = new BigInteger("35369269986011139699238013756346314081616079494832438937442404572459541090733");
            y = new BigInteger("87289916865357820745733302418856691553786390496858282286836977146181557018494");
            xt = new SMSField2(this, // 1
                new BigInteger("31308918258904727662953486405772972470428541905901838510601741084467239695221"),
                new BigInteger("8298157408746381604175914000037502945767116754734727404774820058853157260704"),
                false);
            yt = new SMSField2(this,
                new BigInteger("11322259579778347456178706234918549019137928223031746823736610245315499574757"),
                new BigInteger("20607408075200181718429467751771575905816359508656323982532252645461664715907"),
                false);
            sigma = new BigInteger("57480848876134466153876889911354103964164983428539272267578759647616956639202");
            break;
        case 272: // D = -293470419
            t = new BigInteger("-80723176406116899412334695161890179659305");
            b = new BigInteger("1267616074302456069942878045208680867153482575646349086586881773878649273724127662");
            y = new BigInteger("4066936354215817136402992207238273395785873623177221780711565803518947196123549488");
            xt = new SMSField2(this, // 2
                new BigInteger("4241578102396029330452145402365002719896217686099450397514455732145598233502753603"),
                new BigInteger("3130131398997452537046496743938385250383236338590846998535133667125775885289483657"),
                false);
            yt = new SMSField2(this,
                new BigInteger("4054912555434626768939026964698055949895274183406146331539144058227151732759345007"),
                new BigInteger("2721272038358270568183658396916556422504852425479911750954967408162384001817340835"),
                false);
            sigma = new BigInteger("5184250016517416803376653532585033944038964147329978638065153176158995377014553518");
            break;
        case 313: // D = -1475251
            t = new BigInteger("102686063291726321340412859777047535913538943055");
            b = new BigInteger("6456379186392478513037349254162573407314718779563807619297344086672885596802480241312779607730");
            y = new BigInteger("4511491909925398876497301707690815562324476146485128373387215826129694432513608174743559776022");
            xt = new SMSField2(this, // 2
                new BigInteger("440156801977433534707364162388474036495923434149514439430091632023224784341647627630397592146"),
                new BigInteger("5230961749158345587031766254603513805189766974052307938523234141368172656089698080025296150254"),
                false);
            yt = new SMSField2(this,
                new BigInteger("6231748897889078351084915049337162450353642230757417346501852335237740189684264006771965859956"),
                new BigInteger("2693636047657384368000622860270305595727988263284326319178193752604743134170034325389989084053"),
                false);
            sigma = new BigInteger("9556937642034534149516578128480569575177079759470351313084012506180086904560130621319976757030");
            break;
		/*
        case 463: // D = -15411979
            t = new BigInteger("-4614355931202371337303541315695343970418161818694121460058119237603305");
            b = new BigInteger("9620710120521787422899210164634125543053461635858110613685906298256296246102503921252854434542702081757937116325844374753555895082657142862");
            y = new BigInteger("5180196921326780217780337447853729324861383746747513169066333148429003341105263239506056603038544515994572807467511083409144924620574245374");
            xt = new SMSField2(this, // 1
                new BigInteger("12534900461535656192707277808749903819796708308889426183403049553177785179992652765945165073925269102578344929033240576724607806850556523366"),
                new BigInteger("18356910634356166281072392907176892285405111010411082606405397509964660200291875617513063497787930143867937948443645360378150508965928176398"),
                false);
            yt = new SMSField2(this,
                new BigInteger("2383213907204117660358563861825866822932042239241863108423731676608666889305878359341122800049401461268487587402050326332037659918657353248"),
                new BigInteger("3971953555183142983730258251627471535191291888151165214374926697784372177656880263691720597211277388072642372868449017604476342374890561369"),
                false);
            sigma = new BigInteger("4702265693306842533277229041239906778509189188693247890160246084378118676899857234516876674525314259050051387407684933610525260746218494218");
            break;
        //*/
    	default:
            throw new IllegalArgumentException(invalidParams + ":" + fieldBits);
        }
        p = t.pow(2).subtract(t).add(_1);
        assert(p.mod(_4).intValue() == 3);
        assert(p.isProbablePrime(PRIMALITY_CERTAINTY));
        setN(p.add(_1).subtract(t));
        assert(getN().isProbablePrime(PRIMALITY_CERTAINTY));
        h = p.add(_1).add(t);
        sqrtExponent = p.add(_1).shiftRight(2); // (p + 1)/4
        sqrtExponent2 = p.multiply(p).add(_7).shiftRight(4); // (p^2 + 7)/16
        if (t.signum() >= 0 && sigma.testBit(0)) {
            sigma = p.subtract(sigma);
            System.out.println("sigma@" + fieldBits + " = " + sigma);
        }
        sqrtI = new SMSField2(this, sigma, p.subtract(sigma), false); // sqrt(i) = (1-i)*sigma
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

	private void setN(BigInteger n) {
		this.n = n;
	}

	public BigInteger getN() {
		return n;
	}

}