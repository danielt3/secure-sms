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
