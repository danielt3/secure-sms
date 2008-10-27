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
    private byte[] publicValue;
    private boolean validated;
    
    public Contact(){}

    public Contact(String name, String phone, byte[] publicValue) {

        this.name = name;
        this.phone = phone;
        this.publicValue = publicValue;
        this.validated = false;
    }
    
    public Contact(String name, String phone) {

        this.name = name;
        this.phone = phone;
        this.publicValue = null;
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

    public byte[] getPublicValue() {
        return publicValue;
    }
    
    public boolean isKeyValid() {
        return validated;
    }
    
    public void setKeyValid(boolean value) {
        this.validated = value;
    }
}
