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

import java.util.Date;
import net.sourceforge.floggy.persistence.Persistable;

/**
 *
 * @author rodrigo
 */
public class PersistentMessage implements Persistable {

    private Contact from;
    private Contact to;
    private Date date;
    private byte[] content;

    public PersistentMessage() {
        
        
        
    }
    

    public PersistentMessage(Contact from, Contact to, byte[] content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public Contact getFrom() {
        return from;
    }

    public void setFrom(Contact from) {
        this.from = from;
    }

    public Contact getTo() {
        return to;
    }

    public void setTo(Contact to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
