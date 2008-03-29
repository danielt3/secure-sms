package sms_package;
/*
 * @(#)SMSPushDemo.java	1.6 03/01/22
 *
 * Copyright (c) 1999-2003 Sun Microsystems, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL
 * Use is subject to license terms
 */

import javax.microedition.midlet.*;
import javax.microedition.io.*;
import javax.microedition.lcdui.*;
import javax.wireless.messaging.*;

import java.io.IOException;
import java.util.Random;
import sms_package.BigInteger;
import sms_package.SecureRandom;

/**
 * An example MIDlet displays text from an SMS MessageConnection
 */
public class SMSPushDemo extends MIDlet 
    implements CommandListener, Runnable, MessageListener {
   
    /** user interface command for indicating Exit request. */
    Command exitCommand  = new Command("Exit", Command.EXIT, 2);
    /** user interface text box for the contents of the fetched URL. */
    Alert content;
    /** current display. */
    Display display;
    /** instance of a thread for asynchronous networking and user interface. */
    Thread thread;
    /** Connections detected at start up. */
    String[] connections;
    /** Flag to signal end of processing. */
    boolean done;
    /** SMS message connection for inbound text messages. */
    MessageConnection smsconn;
    /** Current message read from the network. */
    Message msg;
    /** Address of the message's sender */
    String senderAddress;
    /** The screen to display when we return from being paused */
    Displayable resumeScreen;

    /**
     * Initialize the MIDlet with the current display object and
     * graphical components. 
     */
    public SMSPushDemo() {

        display = Display.getDisplay(this);

        content = new Alert("SMS Receive");
        content.setTimeout(Alert.FOREVER);
        content.addCommand(exitCommand);
        content.setCommandListener(this);
        content.setString("Receiving...");
                
        resumeScreen = content;
    }

    /**
     * Start creates the thread to do the MessageConnection receive
     * text.
     * It should return immediately to keep the dispatcher
     * from hanging.
     */
    public void startApp() {
        /** SMS connection to be read. */
	    String smsConnection = "sms://:" + "50000";
        /** Open the message connection. */
        if (smsconn == null) {
            try {
                smsconn = (MessageConnection) Connector.open(smsConnection);
                smsconn.setMessageListener(this);
                //content.setString(String.valueOf(testaBenchMark()));
                
                resumeScreen = content;
            } catch (IOException ioe) {
                //System.out.println("BLA" + String.valueOf(testaBenchMark()));
            	ioe.printStackTrace();
            }
        }

        /** Initialize the text if we were started manually. */
        connections = PushRegistry.listConnections(true);
        if (connections == null || connections.length == 0) {
            content.setString(String.valueOf(testaBenchMark())+"Waiting for SMS on port 50000...");
        }
        done = false;
        thread = new Thread(this);
        thread.start();

        display.setCurrent(resumeScreen);
    }

    /** 
     * Notification that a message arrived.
     * @param conn the connection with messages available
     */
    public void notifyIncomingMessage(MessageConnection conn) {
        if (thread == null) {
            done = false;
            thread = new Thread(this);
            thread.start();
        }
    }

    /** Message reading thread. */
    public void run() {		    
        /** Check for sms connection. */
        try {
            msg = smsconn.receive();
            if (msg != null && msg instanceof TextMessage) {
                senderAddress = msg.getAddress(); 
                content.setTitle("From: " + senderAddress);
                content.setString(((TextMessage)msg).getPayloadText());
                display.setCurrent(content);
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    /**
     * Pause signals the thread to stop by clearing the thread field.
     * If stopped before done with the iterations it will
     * be restarted from scratch later.
     */
    public void pauseApp() {
        done = true;
        thread = null;
        resumeScreen = display.getCurrent();
    }

    /**
     * Destroy must cleanup everything.  The thread is signaled
     * to stop and no result is produced.
     * @param unconditional true if a forced shutdown was requested
     */
    public void destroyApp(boolean unconditional) {
        done = true; 
        thread = null;
        if (smsconn != null) {
            try {
                smsconn.close();
            } catch (IOException e) {
            	System.out.println("BLA" + String.valueOf(testaBenchMark()));
            	// Ignore any errors on shutdown
            }
        }
    }

    /**
     * Respond to commands, including exit
     * @param c user interface command requested
     * @param s screen object initiating the request
     */
    public void commandAction(Command c, Displayable s) {
        try {
            if (c == exitCommand || c == Alert.DISMISS_COMMAND) {
                destroyApp(false);
                notifyDestroyed();
            }
        } catch (Exception ex) {
        	System.out.println("BLA" + String.valueOf(testaBenchMark()));
        	ex.printStackTrace();
        }
    }
    
    /**
     * Allow the user to reply to the received message
     */
    
	public static long testaBenchMark() {
		Random rnd = new Random();
		BigInteger p;
		BigInteger a;		
		int i;
		long startTime, finalTime, elapsed;
		
		int numBits = 160;
		
		a = BigInteger.valueOf(2L);
		p = new BigInteger(numBits , rnd);
		
		System.out.println("Gerou o randômico " + p);
		
		startTime = System.currentTimeMillis();
		while (!p.isProbablePrime(5)) {
			p = p.add(BigInteger.ONE);
		}
		finalTime = System.currentTimeMillis();		
		
		elapsed = finalTime - startTime;		
		System.out.println("Encontrou o primo " + p + " em " + elapsed +" milisegundos.");
		
		startTime = System.currentTimeMillis();		
		for(i = 0; i < 100; i++) {
			a = a.modPow(a, p);
			//a^a mod p		
		}		
		finalTime = System.currentTimeMillis();
		
		elapsed = finalTime - startTime;		
		
		System.out.println("Decorridos " + elapsed + " milisigundos para executar o loop " + i + " vezes.");
		return elapsed;
	}
}
