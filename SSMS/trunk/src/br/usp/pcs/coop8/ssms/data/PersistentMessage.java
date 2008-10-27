/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.usp.pcs.coop8.ssms.data;

import java.util.Date;
import net.sourceforge.floggy.persistence.Persistable;

/**
 *
 * @author rodrigo
 */
public class PersistentMessage implements Persistable{
    
    private Contact from;
    private Contact to;
    private Date date;
    private String content;
    
    public PersistentMessage(){}
    
    public PersistentMessage(Contact from, Contact to, String content) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
