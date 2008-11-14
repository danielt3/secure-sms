/**
 *
 * Copyright (C) 2008 Rodrigo Rodrigues da Silva, Eduardo de Souza Cruz and 
 *                    Geovandro Carlos C. F. Pereira
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
 * 
 */

package br.usp.pcs.coop8.ssms.protocol;


import br.usp.larc.pbarreto.smspairing.SMSField4;
import br.usp.larc.pbarreto.smspairing.SMSPoint2;
import pseudojava.BigInteger;

/**
 * @author rodrigo
 *
 */
public class BDCPSAuthority extends BDCPSImpl {
	
	public BDCPSAuthority(int bits, byte[] masterKey, byte[] id) {
		setup(bits, masterKey, id);
	}
        
        private BDCPSAuthority(){}
	
	private BigInteger s;
	
	/**
	 * Trust authority setup
	 * 
	 * @param bits 		Security parameter (key size)
	 * @param masterKey A byte array containing the trust authority master key
	 * @author 			rodrigo
	 * @since 			2008-10-25
	 */
	public void setup(int bits, byte[] param, byte[] id) {

		setup(bits, new BigInteger(param), id);
	}

	/**
	 * Trust authority setup
	 * 
	 * @param bits 		Security parameter (key size)
	 * @param masterKey The trust authority master key (a BigInteger)
	 * @author 			rodrigo
	 * @since 			2008-10-25
	 */
	protected void setup(int bits, BigInteger masterKey, byte[] id) {
		k = bits;
		this.id = id;
		initParams();
		s = masterKey.mod(sms.getN());
                this.x_A = s; //xA do KGB é o S
		Ppub = P.multiply(s);		
		
		//logger.debug("Ppubs are " + (Ppub.equals(Ppub2)? "equal" : "different"));
		//logger.debug("Auth's Ppub: " + Ppub.normalize());
		//logger.debug("Auth's generated Ppub: " + Ppub2.normalize());
	}
	
	public byte[] privateKeyExtract(byte[] id, byte[] publicValue) {
		if (id == null) throw new IllegalArgumentException ("BDCPS: id cannot be null!");
		if (sms == null) throw new RuntimeException ("BDCPS: SMSParams not set!");
		SMSField4 y_A = new SMSField4(sms, publicValue, 0); 

		if (s == null) throw new RuntimeException ("BDCPS: Trust Authority not set!");
		if (y_A == null) throw new RuntimeException ("BDCPS: Public value not set!");
		
		//TODO: check k
		SMSPoint2 Q_A = Q.multiply( (BDCPSUtil.h1(y_A, id, sms.getN()).add(s)).modInverse(sms.getN())).normalize();
		//Desnecessário fazer esse assert, estava apenas deixando mais lento:             
                //if (!checkPrivateKey(Q_A, y_A, id)) throw new RuntimeException ("BDCPS: Failure at Check-Private-Key");
		return Q_A.toByteArray(SMSPoint2.COMPRESSED);
	}



}
