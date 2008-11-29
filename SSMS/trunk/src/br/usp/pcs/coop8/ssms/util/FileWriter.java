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
package br.usp.pcs.coop8.ssms.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;

/**
 *
 * @author rodrigo
 */
public class FileWriter {

    private FileConnection fconn;
    private String filename;

    public FileWriter(String filename) {
        this.filename = filename;
    }

    private FileConnection getConnection() {
        FileConnection fconn = null;
        try {
            Enumeration e = FileSystemRegistry.listRoots();
            String device;
            if (e.hasMoreElements()) {
                device = (String) e.nextElement();
            } else {
                throw new IOException();
            }
            fconn = (FileConnection) Connector.open("file:///" + device + filename);
            // If no exception is thrown, then the URI is valid, but the file may or may not exist.
            if (!fconn.exists()) {
                fconn.create();
            }  // create the file if it doesn't exist



        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return fconn;
    }

    private DataOutputStream getOutputStream() throws IOException {
        if (fconn == null || !fconn.isOpen()) {
            fconn = getConnection();
        }
        return fconn.openDataOutputStream();
    }

    public void saveOutput(String content) {
        final FileWriter _this = this;
        new Thread() {

            private String content;

            public Thread setContent(String content) {
                this.content = content;
                return this;
            }

            public void run() {
                try {
                    _this.getOutputStream().writeUTF(content);
                    fconn.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }.setContent(content).start();


    }
}
