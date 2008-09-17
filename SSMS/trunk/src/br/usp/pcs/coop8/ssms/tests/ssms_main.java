/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.usp.pcs.coop8.ssms.tests;

import br.usp.larc.smspairing.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import org.netbeans.microedition.util.SimpleCancellableTask;

/**
 * @author nano
 */
public class ssms_main extends MIDlet implements CommandListener {

    private boolean midletPaused = false;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command cancelCommand;
    private SimpleCancellableTask task;
    private Form SSMS_Results;
    private TextField txtResults;
    //</editor-fold>//GEN-END:|fields|0|
   
    //</editor-fold>

    /**
     * The ssms_main constructor.
     */
    public ssms_main() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        
        switchDisplayable(null, getSSMS_Results());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand ">//GEN-BEGIN:|22-getter|0|22-preInit
    /**
     * Returns an initiliazed instance of cancelCommand component.
     * @return the initialized component instance
     */
    public Command getCancelCommand() {
        if (cancelCommand == null) {//GEN-END:|22-getter|0|22-preInit
            // write pre-init user code here
            cancelCommand = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|22-getter|1|22-postInit
            // write post-init user code here
        }//GEN-BEGIN:|22-getter|2|
        return cancelCommand;
    }
    //</editor-fold>//GEN-END:|22-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: task ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of task component.
     * @return the initialized component instance
     */
    public SimpleCancellableTask getTask() {
        if (task == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
            task = new SimpleCancellableTask();//GEN-BEGIN:|18-getter|1|18-execute
            task.setExecutable(new org.netbeans.microedition.util.Executable() {
                public void execute() throws Exception {//GEN-END:|18-getter|1|18-execute
                    // write task-execution user code here
                }//GEN-BEGIN:|18-getter|2|18-postInit
            });//GEN-END:|18-getter|2|18-postInit
            // write post-init user code here
        }//GEN-BEGIN:|18-getter|3|
        return task;
    }
    //</editor-fold>//GEN-END:|18-getter|3|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        if (displayable == SSMS_Results) {//GEN-BEGIN:|7-commandAction|1|23-preAction
            if (command == cancelCommand) {//GEN-END:|7-commandAction|1|23-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|2|23-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|3|7-postCommandAction
        }//GEN-END:|7-commandAction|3|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|4|
    //</editor-fold>//GEN-END:|7-commandAction|4|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: SSMS_Results ">//GEN-BEGIN:|20-getter|0|20-preInit
    /**
     * Returns an initiliazed instance of SSMS_Results component.
     * @return the initialized component instance
     */
    public Form getSSMS_Results() {
        if (SSMS_Results == null) {//GEN-END:|20-getter|0|20-preInit
            // write pre-init user code here
            
            SSMS_Results = new Form("SSMS_Results", new Item[] { getTxtResults() });//GEN-BEGIN:|20-getter|1|20-postInit
            SSMS_Results.addCommand(getCancelCommand());
            SSMS_Results.setCommandListener(this);//GEN-END:|20-getter|1|20-postInit
            // write post-init user code here
        }//GEN-BEGIN:|20-getter|2|
        return SSMS_Results;
    }
    //</editor-fold>//GEN-END:|20-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtResults ">//GEN-BEGIN:|25-getter|0|25-preInit
    /**
     * Returns an initiliazed instance of txtResults component.
     * @return the initialized component instance
     */
    public TextField getTxtResults() {
        if (txtResults == null) {//GEN-END:|25-getter|0|25-preInit
            // write pre-init user code here
            try {
                SMSTests.main(null);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
                    
            txtResults = new TextField("txtResults", Output.getOutput(), Output.getOutput().length(), TextField.ANY);//GEN-LINE:|25-getter|1|25-postInit
            // write post-init user code here
        }//GEN-BEGIN:|25-getter|2|
        return txtResults;
    }
    //</editor-fold>//GEN-END:|25-getter|2|

    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }

}
