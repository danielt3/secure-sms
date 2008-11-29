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

    private String filename;

    public FileWriter(String filename) {
        this.filename = filename;
    }

    public void write(final String content) {
        new Thread() {

            public void run() {
                try {

                    Enumeration e = FileSystemRegistry.listRoots();
                    String device;
                    while (e.hasMoreElements()) {

                        device = (String) e.nextElement();

                        FileConnection fconn = (FileConnection) Connector.open("file:///" + device + filename);
                        System.out.println("Abriu conexão com: " + "file:///" + device + filename);
                        // If no exception is thrown, then the URI is valid, but the file may or may not exist.
                        if (!fconn.exists()) {
                            fconn.create();
                        }  // create the file if it doesn't exist
                        fconn.openDataOutputStream().write(content.getBytes(), 0, content.getBytes().length);
                        fconn.close();
                    }


                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }.start();
    }
}
