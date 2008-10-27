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
    private boolean validated;

    public Contact() {
    }

    public Contact(String name, String phone, byte[] yA, byte[] tA, byte[] hA) {

        this.name = name;
        this.phone = phone;
        this.yA = yA;
        this.tA = tA;
        this.hA = hA;
        this.validated = false;
    }

    public Contact(String name, String phone) {

        this.name = name;
        this.phone = phone;
        this.validated = false;
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

    public boolean isKeyValid() {
        return validated;
    }

    public void setKeyValid(boolean value) {
        this.validated = value;
    }
}
