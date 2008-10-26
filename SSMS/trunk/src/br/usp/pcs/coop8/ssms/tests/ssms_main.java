/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.usp.pcs.coop8.ssms.tests;

import br.usp.larc.smspairing.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import org.netbeans.microedition.lcdui.wma.SMSComposer;
import org.netbeans.microedition.util.SimpleCancellableTask;

/**
 * @author nano
 */
public class ssms_main extends MIDlet implements CommandListener {

    private boolean midletPaused = false;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Form SSMS_Results;
    private TextField txtResults;
    private List ListaInicial;
    private Form form;
    private TextField textField;
    private Form FormAutenticarContato;
    private TextField txtContatPhone;
    private Alert alert;
    private Alert alert1;
    private Alert alert2;
    private Alert alert3;
    private Alert alert4;
    private Form form1;
    private TextField textField1;
    private Command cancelCommand;
    private Command cancelCommand1;
    private Command okCommand;
    private Command cancelCommand2;
    private Command okCommand1;
    private Command okCommand2;
    private Command backCommand;
    private Command cancelCommand3;
    private Command okCommand3;
    private Command cancelCommand4;
    private Command okCommand4;
    private SimpleCancellableTask task;
    private Image image;
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
        
        switchDisplayable(null, getListaInicial());//GEN-LINE:|3-startMIDlet|1|3-postAction
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
        if (displayable == FormAutenticarContato) {//GEN-BEGIN:|7-commandAction|1|68-preAction
            if (command == cancelCommand2) {//GEN-END:|7-commandAction|1|68-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|2|68-postAction
                // write post-action user code here
            } else if (command == okCommand) {//GEN-LINE:|7-commandAction|3|66-preAction
                // write pre-action user code here
                method();//GEN-LINE:|7-commandAction|4|66-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|28-preAction
        } else if (displayable == ListaInicial) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|5|28-preAction
                // write pre-action user code here
                ListaInicialAction();//GEN-LINE:|7-commandAction|6|28-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|7|23-preAction
        } else if (displayable == SSMS_Results) {
            if (command == cancelCommand) {//GEN-END:|7-commandAction|7|23-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|8|23-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|9|77-preAction
        } else if (displayable == alert) {
            if (command == okCommand1) {//GEN-END:|7-commandAction|9|77-preAction
                // write pre-action user code here
                switchDisplayable(null, getFormAutenticarContato());//GEN-LINE:|7-commandAction|10|77-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|11|88-preAction
        } else if (displayable == alert1) {
            if (command == backCommand) {//GEN-END:|7-commandAction|11|88-preAction
                // write pre-action user code here
                switchDisplayable(null, getFormAutenticarContato());//GEN-LINE:|7-commandAction|12|88-postAction
                // write post-action user code here
            } else if (command == okCommand2) {//GEN-LINE:|7-commandAction|13|86-preAction
                // write pre-action user code here
                method1();//GEN-LINE:|7-commandAction|14|86-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|15|98-preAction
        } else if (displayable == alert2) {
            if (command == okCommand3) {//GEN-END:|7-commandAction|15|98-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|16|98-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|17|103-preAction
        } else if (displayable == alert3) {
            if (command == cancelCommand3) {//GEN-END:|7-commandAction|17|103-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|18|103-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|19|55-preAction
        } else if (displayable == form) {
            if (command == cancelCommand1) {//GEN-END:|7-commandAction|19|55-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|20|55-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|21|112-preAction
        } else if (displayable == form1) {
            if (command == cancelCommand4) {//GEN-END:|7-commandAction|21|112-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|22|112-postAction
                // write post-action user code here
            } else if (command == okCommand4) {//GEN-LINE:|7-commandAction|23|110-preAction
                // write pre-action user code here
                method2();//GEN-LINE:|7-commandAction|24|110-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|25|7-postCommandAction
        }//GEN-END:|7-commandAction|25|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|26|
    //</editor-fold>//GEN-END:|7-commandAction|26|

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

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: ListaInicial ">//GEN-BEGIN:|26-getter|0|26-preInit
    /**
     * Returns an initiliazed instance of ListaInicial component.
     * @return the initialized component instance
     */
    public List getListaInicial() {
        if (ListaInicial == null) {//GEN-END:|26-getter|0|26-preInit
            // write pre-init user code here
            ListaInicial = new List("list", Choice.IMPLICIT);//GEN-BEGIN:|26-getter|1|26-postInit
            ListaInicial.append("Primeiro uso", null);
            ListaInicial.append("Autenticar Contato", null);
            ListaInicial.append("Come\u00E7ar Escuta", null);
            ListaInicial.append("Parar escuta", null);
            ListaInicial.append("Enviar torpedo", null);
            ListaInicial.append("Ver OutPut", null);
            ListaInicial.setCommandListener(this);
            ListaInicial.setSelectedFlags(new boolean[] { false, false, false, false, false, false });//GEN-END:|26-getter|1|26-postInit
            // write post-init user code here
        }//GEN-BEGIN:|26-getter|2|
        return ListaInicial;
    }
    //</editor-fold>//GEN-END:|26-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: ListaInicialAction ">//GEN-BEGIN:|26-action|0|26-preAction
    /**
     * Performs an action assigned to the selected list element in the ListaInicial component.
     */
    public void ListaInicialAction() {//GEN-END:|26-action|0|26-preAction
        // enter pre-action user code here
        String __selectedString = getListaInicial().getString(getListaInicial().getSelectedIndex());//GEN-BEGIN:|26-action|1|105-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("Primeiro uso")) {//GEN-END:|26-action|1|105-preAction
                // write pre-action user code here
                switchDisplayable(null, getForm1());//GEN-LINE:|26-action|2|105-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Autenticar Contato")) {//GEN-LINE:|26-action|3|57-preAction
                // write pre-action user code here
                switchDisplayable(null, getFormAutenticarContato());//GEN-LINE:|26-action|4|57-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Come\u00E7ar Escuta")) {//GEN-LINE:|26-action|5|30-preAction
                // write pre-action user code here
                Controller.receberSms();//GEN-LINE:|26-action|6|30-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Parar escuta")) {//GEN-LINE:|26-action|7|31-preAction
                // write pre-action user code here
//GEN-LINE:|26-action|8|31-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Enviar torpedo")) {//GEN-LINE:|26-action|9|32-preAction
                // write pre-action user code here
                Controller.enviarSms("1174749679", "GEO GEO GEOVEMDETERRA", 2102);//GEN-LINE:|26-action|10|32-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Ver OutPut")) {//GEN-LINE:|26-action|11|35-preAction
                // write pre-action user code here
                switchDisplayable(null, getForm());//GEN-LINE:|26-action|12|35-postAction
                // write post-action user code here
            }//GEN-BEGIN:|26-action|13|26-postAction
        }//GEN-END:|26-action|13|26-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|26-action|14|
    //</editor-fold>//GEN-END:|26-action|14|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: form ">//GEN-BEGIN:|51-getter|0|51-preInit
    /**
     * Returns an initiliazed instance of form component.
     * @return the initialized component instance
     */
    public Form getForm() {
        if (form == null) {//GEN-END:|51-getter|0|51-preInit
            // write pre-init user code here
            form = new Form("form", new Item[] { getTextField() });//GEN-BEGIN:|51-getter|1|51-postInit
            form.addCommand(getCancelCommand1());
            form.setCommandListener(this);//GEN-END:|51-getter|1|51-postInit
            // write post-init user code here
        }//GEN-BEGIN:|51-getter|2|
        return form;
    }
    //</editor-fold>//GEN-END:|51-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField ">//GEN-BEGIN:|52-getter|0|52-preInit
    /**
     * Returns an initiliazed instance of textField component.
     * @return the initialized component instance
     */
    public TextField getTextField() {
        if (textField == null) {//GEN-END:|52-getter|0|52-preInit
            // write pre-init user code here
            textField = new TextField("textField", Output.getOutput(), Output.getOutput().length(), TextField.ANY);//GEN-LINE:|52-getter|1|52-postInit
            // write post-init user code here
        }//GEN-BEGIN:|52-getter|2|
        return textField;
    }
    //</editor-fold>//GEN-END:|52-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand1 ">//GEN-BEGIN:|54-getter|0|54-preInit
    /**
     * Returns an initiliazed instance of cancelCommand1 component.
     * @return the initialized component instance
     */
    public Command getCancelCommand1() {
        if (cancelCommand1 == null) {//GEN-END:|54-getter|0|54-preInit
            // write pre-init user code here
            cancelCommand1 = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|54-getter|1|54-postInit
            // write post-init user code here
        }//GEN-BEGIN:|54-getter|2|
        return cancelCommand1;
    }
    //</editor-fold>//GEN-END:|54-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: method ">//GEN-BEGIN:|69-if|0|69-preIf
    /**
     * Performs an action assigned to the method if-point.
     */
    public void method() {//GEN-END:|69-if|0|69-preIf
        // enter pre-if user code here
        if (//GEN-BEGIN:|69-if|1|70-preAction
                (txtContatPhone.getString() != null)
                &&
                (txtContatPhone.getString().length() == 10)
                &&
                (
                Util.isLong(txtContatPhone.getString())
                )
                ) {//GEN-END:|69-if|1|70-preAction
            // write pre-action user code here
            switchDisplayable(null, getAlert1());//GEN-LINE:|69-if|2|70-postAction
            // write post-action user code here
        } else {//GEN-LINE:|69-if|3|71-preAction
            // write pre-action user code here
            switchDisplayable(null, getAlert());//GEN-LINE:|69-if|4|71-postAction
            // write post-action user code here
        }//GEN-LINE:|69-if|5|69-postIf
        // enter post-if user code here
    }//GEN-BEGIN:|69-if|6|
    //</editor-fold>//GEN-END:|69-if|6|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: FormAutenticarContato ">//GEN-BEGIN:|61-getter|0|61-preInit
    /**
     * Returns an initiliazed instance of FormAutenticarContato component.
     * @return the initialized component instance
     */
    public Form getFormAutenticarContato() {
        if (FormAutenticarContato == null) {//GEN-END:|61-getter|0|61-preInit
            // write pre-init user code here
            FormAutenticarContato = new Form("FormAuthenticateContact", new Item[] { getTxtContatPhone() });//GEN-BEGIN:|61-getter|1|61-postInit
            FormAutenticarContato.addCommand(getOkCommand());
            FormAutenticarContato.addCommand(getCancelCommand2());
            FormAutenticarContato.setCommandListener(this);//GEN-END:|61-getter|1|61-postInit
            // write post-init user code here
        }//GEN-BEGIN:|61-getter|2|
        return FormAutenticarContato;
    }
    //</editor-fold>//GEN-END:|61-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtContatPhone ">//GEN-BEGIN:|63-getter|0|63-preInit
    /**
     * Returns an initiliazed instance of txtContatPhone component.
     * @return the initialized component instance
     */
    public TextField getTxtContatPhone() {
        if (txtContatPhone == null) {//GEN-END:|63-getter|0|63-preInit
            // write pre-init user code here
            txtContatPhone = new TextField("Telefone do contato", null, 10, TextField.ANY);//GEN-LINE:|63-getter|1|63-postInit
            // write post-init user code here
        }//GEN-BEGIN:|63-getter|2|
        return txtContatPhone;
    }
    //</editor-fold>//GEN-END:|63-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand ">//GEN-BEGIN:|65-getter|0|65-preInit
    /**
     * Returns an initiliazed instance of okCommand component.
     * @return the initialized component instance
     */
    public Command getOkCommand() {
        if (okCommand == null) {//GEN-END:|65-getter|0|65-preInit
            // write pre-init user code here
            okCommand = new Command("Ok", Command.OK, 0);//GEN-LINE:|65-getter|1|65-postInit
            // write post-init user code here
        }//GEN-BEGIN:|65-getter|2|
        return okCommand;
    }
    //</editor-fold>//GEN-END:|65-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand2 ">//GEN-BEGIN:|67-getter|0|67-preInit
    /**
     * Returns an initiliazed instance of cancelCommand2 component.
     * @return the initialized component instance
     */
    public Command getCancelCommand2() {
        if (cancelCommand2 == null) {//GEN-END:|67-getter|0|67-preInit
            // write pre-init user code here
            cancelCommand2 = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|67-getter|1|67-postInit
            // write post-init user code here
        }//GEN-BEGIN:|67-getter|2|
        return cancelCommand2;
    }
    //</editor-fold>//GEN-END:|67-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: image ">//GEN-BEGIN:|58-getter|0|58-preInit
    /**
     * Returns an initiliazed instance of image component.
     * @return the initialized component instance
     */
    public Image getImage() {
        if (image == null) {//GEN-END:|58-getter|0|58-preInit
            // write pre-init user code here
            image = Image.createImage(1, 1);//GEN-LINE:|58-getter|1|58-postInit
            // write post-init user code here
        }//GEN-BEGIN:|58-getter|2|
        return image;
    }
    //</editor-fold>//GEN-END:|58-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: alert ">//GEN-BEGIN:|75-getter|0|75-preInit
    /**
     * Returns an initiliazed instance of alert component.
     * @return the initialized component instance
     */
    public Alert getAlert() {
        if (alert == null) {//GEN-END:|75-getter|0|75-preInit
            // write pre-init user code here
            alert = new Alert("Bad use", "Please type a number in the contact\'s phone field", null, null);//GEN-BEGIN:|75-getter|1|75-postInit
            alert.addCommand(getOkCommand1());
            alert.setCommandListener(this);
            alert.setTimeout(Alert.FOREVER);//GEN-END:|75-getter|1|75-postInit
            // write post-init user code here
        }//GEN-BEGIN:|75-getter|2|
        return alert;
    }
    //</editor-fold>//GEN-END:|75-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand1 ">//GEN-BEGIN:|76-getter|0|76-preInit
    /**
     * Returns an initiliazed instance of okCommand1 component.
     * @return the initialized component instance
     */
    public Command getOkCommand1() {
        if (okCommand1 == null) {//GEN-END:|76-getter|0|76-preInit
            // write pre-init user code here
            okCommand1 = new Command("Ok", Command.OK, 0);//GEN-LINE:|76-getter|1|76-postInit
            // write post-init user code here
        }//GEN-BEGIN:|76-getter|2|
        return okCommand1;
    }
    //</editor-fold>//GEN-END:|76-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Method: method1 ">//GEN-BEGIN:|92-if|0|92-preIf
    /**
     * Performs an action assigned to the method1 if-point.
     */
    public void method1() {//GEN-END:|92-if|0|92-preIf
        // enter pre-if user code here
        if (true) {//GEN-LINE:|92-if|1|93-preAction
            // write pre-action user code here
            switchDisplayable(null, getAlert2());//GEN-LINE:|92-if|2|93-postAction
            // write post-action user code here
        } else {//GEN-LINE:|92-if|3|94-preAction
            // write pre-action user code here
            switchDisplayable(null, getAlert3());//GEN-LINE:|92-if|4|94-postAction
            // write post-action user code here
        }//GEN-LINE:|92-if|5|92-postIf
        // enter post-if user code here
    }//GEN-BEGIN:|92-if|6|
    //</editor-fold>//GEN-END:|92-if|6|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: alert1 ">//GEN-BEGIN:|83-getter|0|83-preInit
    /**
     * Returns an initiliazed instance of alert1 component.
     * @return the initialized component instance
     */
    public Alert getAlert1() {
        if (alert1 == null) {//GEN-END:|83-getter|0|83-preInit
            // write pre-init user code here
            alert1 = new Alert("alert1", "Voc\u00EA est\u00E1 pronto. Cerifique-se de que seu contato est\u00E1 pronto e pressione OK.", null, null);//GEN-BEGIN:|83-getter|1|83-postInit
            alert1.addCommand(getOkCommand2());
            alert1.addCommand(getBackCommand());
            alert1.setCommandListener(this);
            alert1.setTimeout(Alert.FOREVER);//GEN-END:|83-getter|1|83-postInit
            // write post-init user code here
        }//GEN-BEGIN:|83-getter|2|
        return alert1;
    }
    //</editor-fold>//GEN-END:|83-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: alert2 ">//GEN-BEGIN:|90-getter|0|90-preInit
    /**
     * Returns an initiliazed instance of alert2 component.
     * @return the initialized component instance
     */
    public Alert getAlert2() {
        if (alert2 == null) {//GEN-END:|90-getter|0|90-preInit
            // write pre-init user code here
            alert2 = new Alert("alert2", "Authentication successful", null, null);//GEN-BEGIN:|90-getter|1|90-postInit
            alert2.addCommand(getOkCommand3());
            alert2.setCommandListener(this);
            alert2.setTimeout(Alert.FOREVER);//GEN-END:|90-getter|1|90-postInit
            // write post-init user code here
        }//GEN-BEGIN:|90-getter|2|
        return alert2;
    }
    //</editor-fold>//GEN-END:|90-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: alert3 ">//GEN-BEGIN:|100-getter|0|100-preInit
    /**
     * Returns an initiliazed instance of alert3 component.
     * @return the initialized component instance
     */
    public Alert getAlert3() {
        if (alert3 == null) {//GEN-END:|100-getter|0|100-preInit
            // write pre-init user code here
            alert3 = new Alert("alert3", "Aguardando chegar o SMS com a chave do contato", null, null);//GEN-BEGIN:|100-getter|1|100-postInit
            alert3.addCommand(getCancelCommand3());
            alert3.setCommandListener(this);
            alert3.setTimeout(Alert.FOREVER);//GEN-END:|100-getter|1|100-postInit
            // write post-init user code here
        }//GEN-BEGIN:|100-getter|2|
        return alert3;
    }
    //</editor-fold>//GEN-END:|100-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand2 ">//GEN-BEGIN:|85-getter|0|85-preInit
    /**
     * Returns an initiliazed instance of okCommand2 component.
     * @return the initialized component instance
     */
    public Command getOkCommand2() {
        if (okCommand2 == null) {//GEN-END:|85-getter|0|85-preInit
            // write pre-init user code here
            okCommand2 = new Command("Ok", Command.OK, 0);//GEN-LINE:|85-getter|1|85-postInit
            // write post-init user code here
        }//GEN-BEGIN:|85-getter|2|
        return okCommand2;
    }
    //</editor-fold>//GEN-END:|85-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|87-getter|0|87-preInit
    /**
     * Returns an initiliazed instance of backCommand component.
     * @return the initialized component instance
     */
    public Command getBackCommand() {
        if (backCommand == null) {//GEN-END:|87-getter|0|87-preInit
            // write pre-init user code here
            backCommand = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|87-getter|1|87-postInit
            // write post-init user code here
        }//GEN-BEGIN:|87-getter|2|
        return backCommand;
    }
    //</editor-fold>//GEN-END:|87-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand3 ">//GEN-BEGIN:|97-getter|0|97-preInit
    /**
     * Returns an initiliazed instance of okCommand3 component.
     * @return the initialized component instance
     */
    public Command getOkCommand3() {
        if (okCommand3 == null) {//GEN-END:|97-getter|0|97-preInit
            // write pre-init user code here
            okCommand3 = new Command("Ok", Command.OK, 0);//GEN-LINE:|97-getter|1|97-postInit
            // write post-init user code here
        }//GEN-BEGIN:|97-getter|2|
        return okCommand3;
    }
    //</editor-fold>//GEN-END:|97-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand3 ">//GEN-BEGIN:|102-getter|0|102-preInit
    /**
     * Returns an initiliazed instance of cancelCommand3 component.
     * @return the initialized component instance
     */
    public Command getCancelCommand3() {
        if (cancelCommand3 == null) {//GEN-END:|102-getter|0|102-preInit
            // write pre-init user code here
            cancelCommand3 = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|102-getter|1|102-postInit
            // write post-init user code here
        }//GEN-BEGIN:|102-getter|2|
        return cancelCommand3;
    }
    //</editor-fold>//GEN-END:|102-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: method2 ">//GEN-BEGIN:|114-if|0|114-preIf
    /**
     * Performs an action assigned to the method2 if-point.
     */
    public void method2() {//GEN-END:|114-if|0|114-preIf
        // enter pre-if user code here
        if (true) {//GEN-LINE:|114-if|1|115-preAction
            // write pre-action user code here
//GEN-LINE:|114-if|2|115-postAction
            // write post-action user code here
        } else {//GEN-LINE:|114-if|3|116-preAction
            // write pre-action user code here
            switchDisplayable(null, getAlert4());//GEN-LINE:|114-if|4|116-postAction
            // write post-action user code here
        }//GEN-LINE:|114-if|5|114-postIf
        // enter post-if user code here
    }//GEN-BEGIN:|114-if|6|
    //</editor-fold>//GEN-END:|114-if|6|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: alert4 ">//GEN-BEGIN:|106-getter|0|106-preInit
    /**
     * Returns an initiliazed instance of alert4 component.
     * @return the initialized component instance
     */
    public Alert getAlert4() {
        if (alert4 == null) {//GEN-END:|106-getter|0|106-preInit
            // write pre-init user code here
            alert4 = new Alert("alert4", "A senha n\u00E3o atende os requisitos de seguran\u00E7a da aplica\u00E7\u00E3o. Digite outra senha", null, null);//GEN-BEGIN:|106-getter|1|106-postInit
            alert4.setTimeout(Alert.FOREVER);//GEN-END:|106-getter|1|106-postInit
            // write post-init user code here
        }//GEN-BEGIN:|106-getter|2|
        return alert4;
    }
    //</editor-fold>//GEN-END:|106-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: form1 ">//GEN-BEGIN:|107-getter|0|107-preInit
    /**
     * Returns an initiliazed instance of form1 component.
     * @return the initialized component instance
     */
    public Form getForm1() {
        if (form1 == null) {//GEN-END:|107-getter|0|107-preInit
            // write pre-init user code here
            form1 = new Form("form1", new Item[] { getTextField1() });//GEN-BEGIN:|107-getter|1|107-postInit
            form1.addCommand(getOkCommand4());
            form1.addCommand(getCancelCommand4());
            form1.setCommandListener(this);//GEN-END:|107-getter|1|107-postInit
            // write post-init user code here
        }//GEN-BEGIN:|107-getter|2|
        return form1;
    }
    //</editor-fold>//GEN-END:|107-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField1 ">//GEN-BEGIN:|108-getter|0|108-preInit
    /**
     * Returns an initiliazed instance of textField1 component.
     * @return the initialized component instance
     */
    public TextField getTextField1() {
        if (textField1 == null) {//GEN-END:|108-getter|0|108-preInit
            // write pre-init user code here
            textField1 = new TextField("Entre com sua senha secreta:", null, 32, TextField.ANY);//GEN-LINE:|108-getter|1|108-postInit
            // write post-init user code here
        }//GEN-BEGIN:|108-getter|2|
        return textField1;
    }
    //</editor-fold>//GEN-END:|108-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand4 ">//GEN-BEGIN:|109-getter|0|109-preInit
    /**
     * Returns an initiliazed instance of okCommand4 component.
     * @return the initialized component instance
     */
    public Command getOkCommand4() {
        if (okCommand4 == null) {//GEN-END:|109-getter|0|109-preInit
            // write pre-init user code here
            okCommand4 = new Command("Ok", Command.OK, 0);//GEN-LINE:|109-getter|1|109-postInit
            // write post-init user code here
        }//GEN-BEGIN:|109-getter|2|
        return okCommand4;
    }
    //</editor-fold>//GEN-END:|109-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand4 ">//GEN-BEGIN:|111-getter|0|111-preInit
    /**
     * Returns an initiliazed instance of cancelCommand4 component.
     * @return the initialized component instance
     */
    public Command getCancelCommand4() {
        if (cancelCommand4 == null) {//GEN-END:|111-getter|0|111-preInit
            // write pre-init user code here
            cancelCommand4 = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|111-getter|1|111-postInit
            // write post-init user code here
        }//GEN-BEGIN:|111-getter|2|
        return cancelCommand4;
    }
    //</editor-fold>//GEN-END:|111-getter|2|







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
