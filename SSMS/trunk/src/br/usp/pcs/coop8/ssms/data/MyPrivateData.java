/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.data;

import net.sourceforge.floggy.persistence.Persistable;

/**
 * Representa os parâmetros
 */
public class MyPrivateData implements Persistable {

    private byte[] qA;
    private byte[] idA;
    private byte[] yA;
    private byte[] tA;
    private byte[] hA;

    public byte[] getHA() {
        return hA;
    }

    public void setHA(byte[] hA) {
        this.hA = hA;
    }

    public byte[] getIdA() {
        return idA;
    }

    public void setIdA(byte[] idA) {
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
}
