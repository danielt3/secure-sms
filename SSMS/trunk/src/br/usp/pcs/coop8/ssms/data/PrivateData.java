/**
 *
 * Copyright (C) 2008 Eduardo de Souza Cruz, Geovandro Carlos C. F. Pereira
 *                    and Rodrigo Rodrigues da Silva
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

package br.usp.pcs.coop8.ssms.data;

import net.sourceforge.floggy.persistence.FloggyException;
import net.sourceforge.floggy.persistence.ObjectSet;
import net.sourceforge.floggy.persistence.Persistable;
import net.sourceforge.floggy.persistence.PersistableManager;

/**
 * Representa os parâmetros
 */
public class PrivateData implements Persistable {

    private byte[] qA;
    private String idA;
    private byte[] yA;
    private byte[] tA;
    private byte[] hA;
    private byte[] encryptedQA_c;
    private byte[] encryptedQA_h;
    private byte[] encryptedQA_z;
    private String kgbPhone;
    private static PrivateData myDataInstance = null;
    

    public PrivateData() {

    }

    public static void clearInstance() {
        myDataInstance = null;
    }

    /**
     * Retorna a instância persistente
     * @return
     */
    public static PrivateData getInstance() {
        if (myDataInstance == null) {
            try {
                //Verifica se tem no Floggy
                PersistableManager perMan = PersistableManager.getInstance();

                ObjectSet results = perMan.find(PrivateData.class, null, null);

                if (results == null || results.size() == 0) {
                    //Retorna uma instância com os campos nulos
                    myDataInstance = new PrivateData();
                    perMan.save(myDataInstance);
                } else {
                    myDataInstance = (PrivateData) results.get(0);
                }

            } catch (FloggyException ex) {
                ex.printStackTrace();
            }

        }
        return myDataInstance;
    }

    public byte[] getHA() {
        return hA;
    }

    public void setHA(byte[] hA) {
        this.hA = hA;
    }

    public String getIdA() {
        return idA;
    }

    public void setIdA(String idA) {
        this.idA = idA;
    }

    public byte[] getQA() {
        return qA;
    }

    public void setQA(byte[] qA) {
        this.qA = qA;
    }

    public byte[] getTA() {
        return tA;
    }

    public void setTA(byte[] tA) {
        this.tA = tA;
    }

    public byte[] getYA() {
        return yA;
    }

    public void setYA(byte[] yA) {
        this.yA = yA;
    }
    
    public byte[] getEncryptedQA_c() {
        return encryptedQA_c;
    }
    
    public void setEncryptedQA_c(byte[] encryptedQA_c) {
        this.encryptedQA_c = encryptedQA_c;
    }
    
    public byte[] getEncryptedQA_h() {
        return encryptedQA_h;
    }
    
    public void setEncryptedQA_h(byte[] encryptedQA_h) {
        this.encryptedQA_h = encryptedQA_h;
    }
    
    public byte[] getEncryptedQA_z() {
        return encryptedQA_z;
    }
    
    public void setEncryptedQA_z(byte[] encryptedQA_z) {
        this.encryptedQA_z = encryptedQA_z;
    }
    

    public String getKgbPhone() {
        if (kgbPhone == null) {
            //preehcne com o default
            kgbPhone = "1174749679";
        }
        return kgbPhone;
    }

    public void setKgbPhone(String kgbPhone) {
        this.kgbPhone = kgbPhone;
    }
}
