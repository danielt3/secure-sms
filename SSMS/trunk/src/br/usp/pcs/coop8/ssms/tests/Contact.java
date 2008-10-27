/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.tests;

import net.sourceforge.floggy.persistence.Persistable;

/**
 *
 * @author Administrador
 */
public class Contact implements Persistable {

    private String name;
    private String phone;
    private byte[][] publicKey;

    public Contact(String name, String phone, byte[][] publicKey) {

        this.name = name;
        this.phone = phone;
        this.publicKey = publicKey;

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

    public byte[][] getPublicKey() {
        return publicKey;
    }
}
