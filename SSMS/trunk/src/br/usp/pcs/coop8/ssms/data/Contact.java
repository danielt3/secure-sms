/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.data;

import net.sourceforge.floggy.persistence.Persistable;

/**
 *
 * @author Administrador
 */
public class Contact implements Persistable {

    private String name;
    private String phone;
    private byte[] yA;
    private byte[] tA;
    private byte[] hA;

    //Só o fato de ter yA setado já significa que está validado
    //private boolean validated;
    public Contact() {
    }

    public Contact(String name, String phone) {

        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public byte[] getYA() {
        return yA;
    }

    public byte[] getHA() {
        return hA;
    }

    public byte[] getTA() {
        return tA;
    }

    public void setYA(byte[] yA) {
        this.yA = yA;
    }

    public void setHA(byte[] hA) {
        this.hA = hA;
    }

    public void setTA(byte[] tA) {
        this.tA = tA;
    }
}
