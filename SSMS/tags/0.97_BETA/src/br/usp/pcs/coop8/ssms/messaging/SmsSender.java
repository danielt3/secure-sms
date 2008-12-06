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


package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.application.Configuration;
import javax.microedition.io.Connector;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.MessageConnection;

/**
 *
 * @author rodrigo
 */
public class SmsSender {
    
    private SmsSender(){}
    
    public static void sendSingleThread(String phone, byte[] data) {
        sendBinarySms(phone, data, Configuration.SMS_PORT);
    }

    public static void send(final String phone, final byte[] data) {
        new Thread() {

            public void run() {
                sendBinarySms(phone, data, Configuration.SMS_PORT);
            }
        }.start();

    }

    private static void sendBinarySms(String phone, byte[] data, int port) {

        try {
            String addr = "sms://" + phone + ":" + port;
            MessageConnection conn = (MessageConnection) Connector.open(addr);
            BinaryMessage msg = (BinaryMessage) conn.newMessage(MessageConnection.BINARY_MESSAGE);

            msg.setPayloadData(data);

            conn.send(msg);
        } catch (IllegalArgumentException iae) {
        //do something

        } catch (Exception e) {
        //do something
        }

    }
}
