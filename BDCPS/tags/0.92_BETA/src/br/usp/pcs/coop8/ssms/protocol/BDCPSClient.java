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

import br.usp.larc.pbarreto.smspairing.SMSPoint;

/**
 * @author rodrigo
 *
 */
public class BDCPSClient extends BDCPSImpl {
	
	public BDCPSClient(int bits, byte[] publicPoint, byte[] id) {
		setup(bits, publicPoint, id);
	}
	
	protected BDCPSClient(int bits, SMSPoint publicPoint, byte[] id) {
		setup(bits, publicPoint, id);
	}
	
	private BDCPSClient(){}
	
	/**
	 * Client setup. Must be run before any method.
	 * 
	 * @param bits 		Security parameter (key size)
	 * @param param The trust authority's public point byte representation
         * @param id The hash of the client's phone
	 * @author 			rodrigo
	 * @since 			2008-10-25
	 */	
	public void setup(int bits, byte[] param, byte[] id) {		
		k = bits;
		this.id = id;
		initParams();
		this.Ppub = new SMSPoint(E, param).normalize();
		
		//logger.debug("Client's Ppub: " + Ppub.normalize());
	}
	
	protected void setup(int bits, SMSPoint Ppub, byte[] id) {
				
		k = bits;
		this.id = id;
		initParams();
		this.Ppub = Ppub.normalize();
	}	

	public byte[] privateKeyExtract(byte[] id, byte[] publicValue) {
		throw new RuntimeException("BDCPS: A client cannot run privateKeyExtract!");
	}

}
