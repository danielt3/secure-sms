/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.data;

import net.sourceforge.floggy.persistence.FloggyException;
import net.sourceforge.floggy.persistence.ObjectSet;
import net.sourceforge.floggy.persistence.Persistable;
import net.sourceforge.floggy.persistence.PersistableManager;

/**
 * Representa os parâmetros
 */
public class MyPrivateData implements Persistable {

    private byte[] qA;
    private String idA;
    private byte[] yA;
    private byte[] tA;
    private byte[] hA;
    private static MyPrivateData myDataInstance = null;
    private String kgbPhone;

    public MyPrivateData() {

    }

    public static void clearInstance() {
        myDataInstance = null;
    }

    /**
     * Retorna a instância persistente
     * @return
     */
    public static MyPrivateData getInstance() {
        if (myDataInstance == null) {
            try {
                //Verifica se tem no Floggy
                PersistableManager perMan = PersistableManager.getInstance();

                ObjectSet results = perMan.find(MyPrivateData.getThisClass(), null, null);

                if (results == null || results.size() == 0) {
                    //Retorna uma instância com os campos nulos
                    myDataInstance = new MyPrivateData();
                    perMan.save(myDataInstance);
                } else {
                    myDataInstance = (MyPrivateData) results.get(0);
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

    public static Class getThisClass() {
        try {
            return Class.forName("br.usp.pcs.coop8.ssms.data.MyPrivateData");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
