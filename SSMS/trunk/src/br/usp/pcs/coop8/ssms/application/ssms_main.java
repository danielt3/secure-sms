/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.application;

import br.usp.pcs.coop8.ssms.util.Output;
import br.usp.larc.smspairing.*;
import br.usp.pcs.coop8.ssms.data.Contact;
import br.usp.pcs.coop8.ssms.data.MyPrivateData;
import br.usp.pcs.coop8.ssms.messaging.SigncryptedMessage;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import net.sourceforge.floggy.persistence.Filter;
import net.sourceforge.floggy.persistence.FloggyException;
import net.sourceforge.floggy.persistence.ObjectSet;
import net.sourceforge.floggy.persistence.Persistable;
import net.sourceforge.floggy.persistence.PersistableManager;
import org.netbeans.microedition.util.SimpleCancellableTask;

/**
 * @author nano
 */
public class ssms_main extends MIDlet implements CommandListener {

    private boolean midletPaused = false;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private List ListaInicial;
    private Form form;
    private TextField textField;
    private Form FormAutenticarContato;
    private TextField txtContactPhone;
    private TextField txtContactName;
    private Alert alert4;
    private Form form1;
    private TextField txtXA;
    private TextField txtMyId;
    private Alert alert5;
    private List listContacts;
    private Form form2;
    private TextField txtXA_GerarPubs;
    private List listMessages;
    private Form form3;
    private TextField txtXA_SendMessage;
    private TextField txtMessage;
    private StringItem stringItem;
    private Form form5;
    private TextField txtXA_ReadMessage;
    private Form form4;
    private TextField textField1;
    private StringItem stringItem1;
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
    private Command okCommand5;
    private Command okCommand6;
    private Command cancelCommand5;
    private Command cancelCommand6;
    private Command okCommand7;
    private Command cancelCommand7;
    private Command okCommand8;
    private Command okCommand10;
    private Command helpCommand;
    private Command cancelCommand9;
    private Command okCommand9;
    private Command cancelCommand8;
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
        txtXA_GerarPubs = new TextField("Digite sua senha secreta:", null, 32, TextField.ANY);//GEN-LINE:|0-initialize|1|0-postInitialize
    // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        Controller.startApplication(this);

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
                Controller.addNewContact(txtContactName.getString(), txtContactPhone.getString());//GEN-LINE:|7-commandAction|4|66-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|28-preAction
        } else if (displayable == ListaInicial) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|5|28-preAction
                // write pre-action user code here
                ListaInicialAction();//GEN-LINE:|7-commandAction|6|28-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|7|142-preAction
        } else if (displayable == alert5) {
            if (command == okCommand5) {//GEN-END:|7-commandAction|7|142-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|8|142-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|9|55-preAction
        } else if (displayable == form) {
            if (command == cancelCommand1) {//GEN-END:|7-commandAction|9|55-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|10|55-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|11|112-preAction
        } else if (displayable == form1) {
            if (command == cancelCommand4) {//GEN-END:|7-commandAction|11|112-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|12|112-postAction
            // write post-action user code here
            } else if (command == okCommand4) {//GEN-LINE:|7-commandAction|13|110-preAction
                // write pre-action user code here
                method2();//GEN-LINE:|7-commandAction|14|110-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|15|149-preAction
        } else if (displayable == form2) {
            if (command == cancelCommand5) {//GEN-END:|7-commandAction|15|149-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|16|149-postAction
            // write post-action user code here
            } else if (command == okCommand6) {//GEN-LINE:|7-commandAction|17|147-preAction
                // write pre-action user code here
                Controller.finalizeFirstConfig(txtXA_GerarPubs.getString());//GEN-LINE:|7-commandAction|18|147-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|19|170-preAction
        } else if (displayable == form3) {
            if (command == cancelCommand6) {//GEN-END:|7-commandAction|19|170-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|20|170-postAction
            // write post-action user code here
            } else if (command == okCommand7) {//GEN-LINE:|7-commandAction|21|168-preAction
                // write pre-action user code here
                Controller.sendSigncryptedMessage(txtMessage.getString(), txtXA_SendMessage.getString());//GEN-LINE:|7-commandAction|22|168-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|23|193-preAction
        } else if (displayable == form4) {
            if (command == okCommand9) {//GEN-END:|7-commandAction|23|193-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|24|193-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|25|188-preAction
        } else if (displayable == form5) {
            if (command == cancelCommand7) {//GEN-END:|7-commandAction|25|188-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|26|188-postAction
            // write post-action user code here
            } else if (command == okCommand8) {//GEN-LINE:|7-commandAction|27|186-preAction
                // write pre-action user code here
                switchDisplayable(null, getForm4());//GEN-LINE:|7-commandAction|28|186-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|29|158-preAction
        } else if (displayable == listContacts) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|29|158-preAction
                // write pre-action user code here
                listContactsAction();//GEN-LINE:|7-commandAction|30|158-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|31|175-preAction
        } else if (displayable == listMessages) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|31|175-preAction
                // write pre-action user code here
                listMessagesAction();//GEN-LINE:|7-commandAction|32|175-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|33|7-postCommandAction
        }//GEN-END:|7-commandAction|33|7-postCommandAction
    // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|34|
    //</editor-fold>//GEN-END:|7-commandAction|34|





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
            ListaInicial.append("Enviar torpedo", null);
            ListaInicial.append("Ver mensagens", null);
            ListaInicial.append("Ver OutPut", null);
            ListaInicial.append("Executar testes", null);
            ListaInicial.append("Limpar dados", null);
            ListaInicial.setCommandListener(this);
            ListaInicial.setSelectedFlags(new boolean[] { false, false, false, false, false, false, false });//GEN-END:|26-getter|1|26-postInit
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
                method3();//GEN-LINE:|26-action|2|105-postAction
            // write post-action user code here
            } else if (__selectedString.equals("Autenticar Contato")) {//GEN-LINE:|26-action|3|57-preAction
                // write pre-action user code here
                switchDisplayable(null, getFormAutenticarContato());//GEN-LINE:|26-action|4|57-postAction
            // write post-action user code here
            } else if (__selectedString.equals("Enviar torpedo")) {//GEN-LINE:|26-action|5|32-preAction
                // write pre-action user code here
                switchDisplayable(null, getListContacts());//GEN-LINE:|26-action|6|32-postAction
            // write post-action user code here
            } else if (__selectedString.equals("Ver mensagens")) {//GEN-LINE:|26-action|7|177-preAction
                // write pre-action user code here
                switchDisplayable(null, getListMessages());//GEN-LINE:|26-action|8|177-postAction
            // write post-action user code here
            } else if (__selectedString.equals("Ver OutPut")) {//GEN-LINE:|26-action|9|35-preAction
                // write pre-action user code here
                switchDisplayable(null, getForm());//GEN-LINE:|26-action|10|35-postAction
            // write post-action user code here
            } else if (__selectedString.equals("Executar testes")) {//GEN-LINE:|26-action|11|127-preAction
                // write pre-action user code here
                Controller.testinho();//GEN-LINE:|26-action|12|127-postAction
            // write post-action user code here
            } else if (__selectedString.equals("Limpar dados")) {//GEN-LINE:|26-action|13|204-preAction
                // write pre-action user code here
                Controller.resetData();//GEN-LINE:|26-action|14|204-postAction
                // write post-action user code here
            }//GEN-BEGIN:|26-action|15|26-postAction
        }//GEN-END:|26-action|15|26-postAction
    // enter post-action user code here
    }//GEN-BEGIN:|26-action|16|
    //</editor-fold>//GEN-END:|26-action|16|
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
            textField = new TextField("textField", Output.getOutput(), ((Output.getOutput().length() != 0)//GEN-BEGIN:|52-getter|1|52-postInit
                    ? Output.getOutput().length() : 1), TextField.ANY);//GEN-END:|52-getter|1|52-postInit
        // write post-init user code here
        } else {
             // Atualiza o texto            
            textField.setMaxSize(((Output.getOutput().length() != 0)
                    ? Output.getOutput().length() : 1));
            textField.setString(Output.getOutput());
            
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
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: FormAutenticarContato ">//GEN-BEGIN:|61-getter|0|61-preInit
    /**
     * Returns an initiliazed instance of FormAutenticarContato component.
     * @return the initialized component instance
     */
    public Form getFormAutenticarContato() {
        if (FormAutenticarContato == null) {//GEN-END:|61-getter|0|61-preInit
            // write pre-init user code here
            FormAutenticarContato = new Form("FormAuthenticateContact", new Item[] { getTxtContactPhone(), getTxtContactName() });//GEN-BEGIN:|61-getter|1|61-postInit
            FormAutenticarContato.addCommand(getOkCommand());
            FormAutenticarContato.addCommand(getCancelCommand2());
            FormAutenticarContato.setCommandListener(this);//GEN-END:|61-getter|1|61-postInit
        // write post-init user code here
        }//GEN-BEGIN:|61-getter|2|
        return FormAutenticarContato;
    }
    //</editor-fold>//GEN-END:|61-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtContactPhone ">//GEN-BEGIN:|63-getter|0|63-preInit
    /**
     * Returns an initiliazed instance of txtContactPhone component.
     * @return the initialized component instance
     */
    public TextField getTxtContactPhone() {
        if (txtContactPhone == null) {//GEN-END:|63-getter|0|63-preInit
            // write pre-init user code here
            txtContactPhone = new TextField("Telefone do contato", null, 10, TextField.ANY);//GEN-LINE:|63-getter|1|63-postInit
        // write post-init user code here
        }//GEN-BEGIN:|63-getter|2|
        return txtContactPhone;
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
        if (((txtXA.getString() != null &&//GEN-BEGIN:|114-if|1|115-preAction
                txtXA.getString().length()  >= 8)
                &&
                (txtMyId.getString() != null &&
                txtMyId.getString().length()  == 10))) {//GEN-END:|114-if|1|115-preAction
            // write pre-action user code here
            Controller.firstTimeUse(txtXA.getString(), txtMyId.getString());//GEN-LINE:|114-if|2|115-postAction
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
            form1 = new Form("form1", new Item[] { getTxtXA(), getTxtMyId() });//GEN-BEGIN:|107-getter|1|107-postInit
            form1.addCommand(getOkCommand4());
            form1.addCommand(getCancelCommand4());
            form1.setCommandListener(this);//GEN-END:|107-getter|1|107-postInit
        // write post-init user code here
        }//GEN-BEGIN:|107-getter|2|
        return form1;
    }
    //</editor-fold>//GEN-END:|107-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtXA ">//GEN-BEGIN:|108-getter|0|108-preInit
    /**
     * Returns an initiliazed instance of txtXA component.
     * @return the initialized component instance
     */
    public TextField getTxtXA() {
        if (txtXA == null) {//GEN-END:|108-getter|0|108-preInit
            // write pre-init user code here
            txtXA = new TextField("Entre com sua senha secreta:", null, 32, TextField.ANY);//GEN-LINE:|108-getter|1|108-postInit
        // write post-init user code here
        }//GEN-BEGIN:|108-getter|2|
        return txtXA;
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

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtMyId ">//GEN-BEGIN:|129-getter|0|129-preInit
    /**
     * Returns an initiliazed instance of txtMyId component.
     * @return the initialized component instance
     */
    public TextField getTxtMyId() {
        if (txtMyId == null) {//GEN-END:|129-getter|0|129-preInit
            // write pre-init user code here
            txtMyId = new TextField("Entre com seu numero de telefone:", null, 32, TextField.ANY);//GEN-LINE:|129-getter|1|129-postInit
        // write post-init user code here
        }//GEN-BEGIN:|129-getter|2|
        return txtMyId;
    }
    //</editor-fold>//GEN-END:|129-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: method3 ">//GEN-BEGIN:|130-if|0|130-preIf
    /**
     * Performs an action assigned to the method3 if-point.
     */
    public void method3() {//GEN-END:|130-if|0|130-preIf
        // enter pre-if user code here
        if (MyPrivateData.getInstance().getQA() == null) {//GEN-LINE:|130-if|1|131-preAction
            // write pre-action user code here
            switchDisplayable(null, getForm1());//GEN-LINE:|130-if|2|131-postAction
        // write post-action user code here
        } else {//GEN-LINE:|130-if|3|132-preAction
            // write pre-action user code here
            method4();//GEN-LINE:|130-if|4|132-postAction
        // write post-action user code here
        }//GEN-LINE:|130-if|5|130-postIf
    // enter post-if user code here
    }//GEN-BEGIN:|130-if|6|
    //</editor-fold>//GEN-END:|130-if|6|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: method4 ">//GEN-BEGIN:|135-if|0|135-preIf
    /**
     * Performs an action assigned to the method4 if-point.
     */
    public void method4() {//GEN-END:|135-if|0|135-preIf
        // enter pre-if user code here
        if (MyPrivateData//GEN-BEGIN:|135-if|1|136-preAction
                .getInstance().getTA() == null) {//GEN-END:|135-if|1|136-preAction
            // write pre-action user code here
            switchDisplayable(null, getForm2());//GEN-LINE:|135-if|2|136-postAction
        // write post-action user code here
        } else {//GEN-LINE:|135-if|3|137-preAction
            // write pre-action user code here
            switchDisplayable(null, getAlert5());//GEN-LINE:|135-if|4|137-postAction
        // write post-action user code here
        }//GEN-LINE:|135-if|5|135-postIf
    // enter post-if user code here
    }//GEN-BEGIN:|135-if|6|
    //</editor-fold>//GEN-END:|135-if|6|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: alert5 ">//GEN-BEGIN:|139-getter|0|139-preInit
    /**
     * Returns an initiliazed instance of alert5 component.
     * @return the initialized component instance
     */
    public Alert getAlert5() {
        if (alert5 == null) {//GEN-END:|139-getter|0|139-preInit
            // write pre-init user code here
            alert5 = new Alert("alert5", "Voc\u00EA j\u00E1 fez a configura\u00E7\u00E3o.", null, null);//GEN-BEGIN:|139-getter|1|139-postInit
            alert5.addCommand(getOkCommand5());
            alert5.setCommandListener(this);
            alert5.setTimeout(Alert.FOREVER);//GEN-END:|139-getter|1|139-postInit
        // write post-init user code here
        }//GEN-BEGIN:|139-getter|2|
        return alert5;
    }
    //</editor-fold>//GEN-END:|139-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: form2 ">//GEN-BEGIN:|144-getter|0|144-preInit
    /**
     * Returns an initiliazed instance of form2 component.
     * @return the initialized component instance
     */
    public Form getForm2() {
        if (form2 == null) {//GEN-END:|144-getter|0|144-preInit
            // write pre-init user code here
            form2 = new Form("form2", new Item[] { txtXA_GerarPubs });//GEN-BEGIN:|144-getter|1|144-postInit
            form2.addCommand(getOkCommand6());
            form2.addCommand(getCancelCommand5());
            form2.setCommandListener(this);//GEN-END:|144-getter|1|144-postInit
        // write post-init user code here
        }//GEN-BEGIN:|144-getter|2|
        return form2;
    }
    //</editor-fold>//GEN-END:|144-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand5 ">//GEN-BEGIN:|141-getter|0|141-preInit
    /**
     * Returns an initiliazed instance of okCommand5 component.
     * @return the initialized component instance
     */
    public Command getOkCommand5() {
        if (okCommand5 == null) {//GEN-END:|141-getter|0|141-preInit
            // write pre-init user code here
            okCommand5 = new Command("Ok", Command.OK, 0);//GEN-LINE:|141-getter|1|141-postInit
        // write post-init user code here
        }//GEN-BEGIN:|141-getter|2|
        return okCommand5;
    }
    //</editor-fold>//GEN-END:|141-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand6 ">//GEN-BEGIN:|146-getter|0|146-preInit
    /**
     * Returns an initiliazed instance of okCommand6 component.
     * @return the initialized component instance
     */
    public Command getOkCommand6() {
        if (okCommand6 == null) {//GEN-END:|146-getter|0|146-preInit
            // write pre-init user code here
            okCommand6 = new Command("Ok", Command.OK, 0);//GEN-LINE:|146-getter|1|146-postInit
        // write post-init user code here
        }//GEN-BEGIN:|146-getter|2|
        return okCommand6;
    }
    //</editor-fold>//GEN-END:|146-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand5 ">//GEN-BEGIN:|148-getter|0|148-preInit
    /**
     * Returns an initiliazed instance of cancelCommand5 component.
     * @return the initialized component instance
     */
    public Command getCancelCommand5() {
        if (cancelCommand5 == null) {//GEN-END:|148-getter|0|148-preInit
            // write pre-init user code here
            cancelCommand5 = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|148-getter|1|148-postInit
        // write post-init user code here
        }//GEN-BEGIN:|148-getter|2|
        return cancelCommand5;
    }
    //</editor-fold>//GEN-END:|148-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtContactName ">//GEN-BEGIN:|154-getter|0|154-preInit
    /**
     * Returns an initiliazed instance of txtContactName component.
     * @return the initialized component instance
     */
    public TextField getTxtContactName() {
        if (txtContactName == null) {//GEN-END:|154-getter|0|154-preInit
            // write pre-init user code here
            txtContactName = new TextField("Nome do contato", null, 32, TextField.ANY);//GEN-LINE:|154-getter|1|154-postInit
        // write post-init user code here
        }//GEN-BEGIN:|154-getter|2|
        return txtContactName;
    }
    //</editor-fold>//GEN-END:|154-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: listContacts ">//GEN-BEGIN:|157-getter|0|157-preInit
    /**
     * Returns an initiliazed instance of listContacts component.
     * @return the initialized component instance
     */
    public List getListContacts() {
        if (listContacts == null) {//GEN-END:|157-getter|0|157-preInit
            // write pre-init user code here
            listContacts = new List("ListContacts", Choice.IMPLICIT);//GEN-BEGIN:|157-getter|1|157-postInit
            listContacts.setCommandListener(this);
            listContacts.setSelectedFlags(new boolean[] {  });//GEN-END:|157-getter|1|157-postInit
            // write post-init user code here
            try {
                PersistableManager perMan = PersistableManager.getInstance();
                ObjectSet results = perMan.find(Contact.class, null, null);

                for (int i = 0; i < results.size(); i++) {
                    listContacts.append(((Contact) results.get(i)).getName() + "/" + ((Contact) results.get(i)).getPhone(), null);
                }
            } catch (FloggyException ex) {
                ex.printStackTrace();
            }
        
        }//GEN-BEGIN:|157-getter|2|
        return listContacts;
    }
    //</editor-fold>//GEN-END:|157-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listContactsAction ">//GEN-BEGIN:|157-action|0|157-preAction
    /**
     * Performs an action assigned to the selected list element in the listContacts component.
     */
    public void listContactsAction() {//GEN-END:|157-action|0|157-preAction
        // enter pre-action user code here
        if (true) {
            final String __selectedString = getListContacts().getString(getListContacts().getSelectedIndex());
            if (__selectedString != null) {
                try {
                    PersistableManager perMan = PersistableManager.getInstance();
                    ObjectSet results = perMan.find(Contact.class, new Filter() {

                        public boolean matches(Persistable arg0) {
                            return ((Contact) arg0).getPhone().equals(__selectedString.substring(__selectedString.length() - 10, __selectedString.length()));
                        }
                    }, null);

                    Controller.setSelectedContact((Contact) results.get(0));

                    switchDisplayable(null, getForm3());
                } catch (FloggyException ex) {
                    ex.printStackTrace();
                }
            }
            return;
        }

        String __selectedString = getListContacts().getString(getListContacts().getSelectedIndex());//GEN-LINE:|157-action|1|157-postAction
    // enter post-action user code here
    }//GEN-BEGIN:|157-action|2|
    //</editor-fold>//GEN-END:|157-action|2|
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: form3 ">//GEN-BEGIN:|162-getter|0|162-preInit
    /**
     * Returns an initiliazed instance of form3 component.
     * @return the initialized component instance
     */
    public Form getForm3() {
        if (form3 == null) {//GEN-END:|162-getter|0|162-preInit
            // write pre-init user code here
            form3 = new Form("form3", new Item[] { getStringItem(), getTxtMessage(), getTxtXA_SendMessage() });//GEN-BEGIN:|162-getter|1|162-postInit
            form3.addCommand(getOkCommand7());
            form3.addCommand(getCancelCommand6());
            form3.setCommandListener(this);//GEN-END:|162-getter|1|162-postInit
        // write post-init user code here
        }//GEN-BEGIN:|162-getter|2|
        return form3;
    }
    //</editor-fold>//GEN-END:|162-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem ">//GEN-BEGIN:|164-getter|0|164-preInit
    /**
     * Returns an initiliazed instance of stringItem component.
     * @return the initialized component instance
     */
    public StringItem getStringItem() {
        if (stringItem == null) {//GEN-END:|164-getter|0|164-preInit
            // write pre-init user code here
            stringItem = new StringItem(Controller.getSelectedContact().getName() + "/" + Controller.getSelectedContact().getPhone(), "");//GEN-LINE:|164-getter|1|164-postInit
        // write post-init user code here
        }//GEN-BEGIN:|164-getter|2|
        return stringItem;
    }
    //</editor-fold>//GEN-END:|164-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtMessage ">//GEN-BEGIN:|165-getter|0|165-preInit
    /**
     * Returns an initiliazed instance of txtMessage component.
     * @return the initialized component instance
     */
    public TextField getTxtMessage() {
        if (txtMessage == null) {//GEN-END:|165-getter|0|165-preInit
            // write pre-init user code here
            txtMessage = new TextField("Mensagem:", "", 140, TextField.ANY);//GEN-LINE:|165-getter|1|165-postInit
        // write post-init user code here
        }//GEN-BEGIN:|165-getter|2|
        return txtMessage;
    }
    //</editor-fold>//GEN-END:|165-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtXA_SendMessage ">//GEN-BEGIN:|166-getter|0|166-preInit
    /**
     * Returns an initiliazed instance of txtXA_SendMessage component.
     * @return the initialized component instance
     */
    public TextField getTxtXA_SendMessage() {
        if (txtXA_SendMessage == null) {//GEN-END:|166-getter|0|166-preInit
            // write pre-init user code here
            txtXA_SendMessage = new TextField("Senha secreta:", null, 32, TextField.ANY);//GEN-LINE:|166-getter|1|166-postInit
        // write post-init user code here
        }//GEN-BEGIN:|166-getter|2|
        return txtXA_SendMessage;
    }
    //</editor-fold>//GEN-END:|166-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand7 ">//GEN-BEGIN:|167-getter|0|167-preInit
    /**
     * Returns an initiliazed instance of okCommand7 component.
     * @return the initialized component instance
     */
    public Command getOkCommand7() {
        if (okCommand7 == null) {//GEN-END:|167-getter|0|167-preInit
            // write pre-init user code here
            okCommand7 = new Command("Ok", Command.OK, 0);//GEN-LINE:|167-getter|1|167-postInit
        // write post-init user code here
        }//GEN-BEGIN:|167-getter|2|
        return okCommand7;
    }
    //</editor-fold>//GEN-END:|167-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand6 ">//GEN-BEGIN:|169-getter|0|169-preInit
    /**
     * Returns an initiliazed instance of cancelCommand6 component.
     * @return the initialized component instance
     */
    public Command getCancelCommand6() {
        if (cancelCommand6 == null) {//GEN-END:|169-getter|0|169-preInit
            // write pre-init user code here
            cancelCommand6 = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|169-getter|1|169-postInit
        // write post-init user code here
        }//GEN-BEGIN:|169-getter|2|
        return cancelCommand6;
    }
    //</editor-fold>//GEN-END:|169-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: listMessages ">//GEN-BEGIN:|174-getter|0|174-preInit
    /**
     * Returns an initiliazed instance of listMessages component.
     * @return the initialized component instance
     */
    public List getListMessages() {
        if (listMessages == null) {//GEN-END:|174-getter|0|174-preInit
            // write pre-init user code here
            listMessages = new List("listMessages", Choice.IMPLICIT);//GEN-BEGIN:|174-getter|1|174-postInit
            listMessages.setCommandListener(this);
            listMessages.setSelectedFlags(new boolean[] {  });//GEN-END:|174-getter|1|174-postInit
            // write post-init user code here
            try {
                PersistableManager perMan = PersistableManager.getInstance();
                ObjectSet results = perMan.find(SigncryptedMessage.class, null, null);

                for (int i = 0; i < results.size(); i++) {
                    listMessages.append(((SigncryptedMessage) results.get(i)).getSender() + "/" + ((SigncryptedMessage) results.get(i)).getDate(), null);
                }
            } catch (FloggyException ex) {
                ex.printStackTrace();
            }
        }//GEN-BEGIN:|174-getter|2|
        return listMessages;
    }
    //</editor-fold>//GEN-END:|174-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listMessagesAction ">//GEN-BEGIN:|174-action|0|174-preAction
    /**
     * Performs an action assigned to the selected list element in the listMessages component.
     */
    public void listMessagesAction() {//GEN-END:|174-action|0|174-preAction
        // enter pre-action user code here
        if (true) {
            final String __selectedString = getListMessages().getString(getListMessages().getSelectedIndex());
            if (__selectedString != null) {
                try {
                    PersistableManager perMan = PersistableManager.getInstance();
                    ObjectSet results = perMan.find(SigncryptedMessage.class, new Filter() {

                        public boolean matches(Persistable arg0) {
                            return __selectedString.equals(((SigncryptedMessage) arg0).getSender() + "/" + ((SigncryptedMessage) arg0).getDate());
                        }
                    }, null);

                    Controller.setSelectedMessage((SigncryptedMessage) results.get(0));

                    switchDisplayable(null, getForm5());
                } catch (FloggyException ex) {
                    ex.printStackTrace();
                }
            }
            return;
        }
//GEN-LINE:|174-action|1|174-postAction
    // enter post-action user code here
    }//GEN-BEGIN:|174-action|2|
    //</editor-fold>//GEN-END:|174-action|2|
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: form4 ">//GEN-BEGIN:|180-getter|0|180-preInit
    /**
     * Returns an initiliazed instance of form4 component.
     * @return the initialized component instance
     */
    public Form getForm4() {
        if (form4 == null) {//GEN-END:|180-getter|0|180-preInit
            // write pre-init user code here
            form4 = new Form("form4", new Item[] { getStringItem1(), getTextField1() });//GEN-BEGIN:|180-getter|1|180-postInit
            form4.addCommand(getOkCommand9());
            form4.setCommandListener(this);//GEN-END:|180-getter|1|180-postInit
        // write post-init user code here 
        } else {
            // Garante que irá atualizar o texto
            ((TextField)form4.get(1)).setString(Controller.getUnsigncryptedText(txtXA_ReadMessage.getString()));            
        }//GEN-BEGIN:|180-getter|2|
        return form4;
    }
    //</editor-fold>//GEN-END:|180-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem1 ">//GEN-BEGIN:|181-getter|0|181-preInit
    /**
     * Returns an initiliazed instance of stringItem1 component.
     * @return the initialized component instance
     */
    public StringItem getStringItem1() {
        if (stringItem1 == null) {//GEN-END:|181-getter|0|181-preInit
            // write pre-init user code here
            stringItem1 = new StringItem(Controller.getSelectedMessage().getSender(), Controller.getSelectedMessage().getDate().toString());//GEN-LINE:|181-getter|1|181-postInit
        // write post-init user code here
        }//GEN-BEGIN:|181-getter|2|
        return stringItem1;
    }
    //</editor-fold>//GEN-END:|181-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField1 ">//GEN-BEGIN:|182-getter|0|182-preInit
    /**
     * Returns an initiliazed instance of textField1 component.
     * @return the initialized component instance
     */
    public TextField getTextField1() {
        if (textField1 == null) {//GEN-END:|182-getter|0|182-preInit
            // write pre-init user code here
            textField1 = new TextField("Mensagem:", Controller.getUnsigncryptedText(txtXA_ReadMessage.getString()), 140, TextField.ANY);//GEN-LINE:|182-getter|1|182-postInit
        // write post-init user code here
        } else {
            // Garante que irá atualizar o texto
            textField1.setString(Controller.getUnsigncryptedText(txtXA_ReadMessage.getString()));
        }//GEN-BEGIN:|182-getter|2|
        return textField1;
    }
    //</editor-fold>//GEN-END:|182-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: form5 ">//GEN-BEGIN:|183-getter|0|183-preInit
    /**
     * Returns an initiliazed instance of form5 component.
     * @return the initialized component instance
     */
    public Form getForm5() {
        if (form5 == null) {//GEN-END:|183-getter|0|183-preInit
            // write pre-init user code here
            form5 = new Form("form5", new Item[] { getTxtXA_ReadMessage() });//GEN-BEGIN:|183-getter|1|183-postInit
            form5.addCommand(getOkCommand8());
            form5.addCommand(getCancelCommand7());
            form5.setCommandListener(this);//GEN-END:|183-getter|1|183-postInit
        // write post-init user code here
        }//GEN-BEGIN:|183-getter|2|
        return form5;
    }
    //</editor-fold>//GEN-END:|183-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtXA_ReadMessage ">//GEN-BEGIN:|184-getter|0|184-preInit
    /**
     * Returns an initiliazed instance of txtXA_ReadMessage component.
     * @return the initialized component instance
     */
    public TextField getTxtXA_ReadMessage() {
        if (txtXA_ReadMessage == null) {//GEN-END:|184-getter|0|184-preInit
            // write pre-init user code here
            txtXA_ReadMessage = new TextField("Senha secreta:", null, 32, TextField.ANY);//GEN-LINE:|184-getter|1|184-postInit
        // write post-init user code here
        }//GEN-BEGIN:|184-getter|2|
        return txtXA_ReadMessage;
    }
    //</editor-fold>//GEN-END:|184-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand8 ">//GEN-BEGIN:|185-getter|0|185-preInit
    /**
     * Returns an initiliazed instance of okCommand8 component.
     * @return the initialized component instance
     */
    public Command getOkCommand8() {
        if (okCommand8 == null) {//GEN-END:|185-getter|0|185-preInit
            // write pre-init user code here
            okCommand8 = new Command("Ok", Command.OK, 0);//GEN-LINE:|185-getter|1|185-postInit
        // write post-init user code here
        }//GEN-BEGIN:|185-getter|2|
        return okCommand8;
    }
    //</editor-fold>//GEN-END:|185-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand7 ">//GEN-BEGIN:|187-getter|0|187-preInit
    /**
     * Returns an initiliazed instance of cancelCommand7 component.
     * @return the initialized component instance
     */
    public Command getCancelCommand7() {
        if (cancelCommand7 == null) {//GEN-END:|187-getter|0|187-preInit
            // write pre-init user code here
            cancelCommand7 = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|187-getter|1|187-postInit
        // write post-init user code here
        }//GEN-BEGIN:|187-getter|2|
        return cancelCommand7;
    }
    //</editor-fold>//GEN-END:|187-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand9 ">//GEN-BEGIN:|192-getter|0|192-preInit
    /**
     * Returns an initiliazed instance of okCommand9 component.
     * @return the initialized component instance
     */
    public Command getOkCommand9() {
        if (okCommand9 == null) {//GEN-END:|192-getter|0|192-preInit
            // write pre-init user code here
            okCommand9 = new Command("Ok", Command.OK, 0);//GEN-LINE:|192-getter|1|192-postInit
        // write post-init user code here
        }//GEN-BEGIN:|192-getter|2|
        return okCommand9;
    }
    //</editor-fold>//GEN-END:|192-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: helpCommand ">//GEN-BEGIN:|197-getter|0|197-preInit
    /**
     * Returns an initiliazed instance of helpCommand component.
     * @return the initialized component instance
     */
    public Command getHelpCommand() {
        if (helpCommand == null) {//GEN-END:|197-getter|0|197-preInit
            // write pre-init user code here
            helpCommand = new Command("Ajuda", Command.HELP, 0);//GEN-LINE:|197-getter|1|197-postInit
            // write post-init user code here
        }//GEN-BEGIN:|197-getter|2|
        return helpCommand;
    }
    //</editor-fold>//GEN-END:|197-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand9 ">//GEN-BEGIN:|199-getter|0|199-preInit
    /**
     * Returns an initiliazed instance of cancelCommand9 component.
     * @return the initialized component instance
     */
    public Command getCancelCommand9() {
        if (cancelCommand9 == null) {//GEN-END:|199-getter|0|199-preInit
            // write pre-init user code here
            cancelCommand9 = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|199-getter|1|199-postInit
            // write post-init user code here
        }//GEN-BEGIN:|199-getter|2|
        return cancelCommand9;
    }
    //</editor-fold>//GEN-END:|199-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand8 ">//GEN-BEGIN:|195-getter|0|195-preInit
    /**
     * Returns an initiliazed instance of cancelCommand8 component.
     * @return the initialized component instance
     */
    public Command getCancelCommand8() {
        if (cancelCommand8 == null) {//GEN-END:|195-getter|0|195-preInit
            // write pre-init user code here
            cancelCommand8 = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|195-getter|1|195-postInit
            // write post-init user code here
        }//GEN-BEGIN:|195-getter|2|
        return cancelCommand8;
    }
    //</editor-fold>//GEN-END:|195-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand10 ">//GEN-BEGIN:|203-getter|0|203-preInit
    /**
     * Returns an initiliazed instance of okCommand10 component.
     * @return the initialized component instance
     */
    public Command getOkCommand10() {
        if (okCommand10 == null) {//GEN-END:|203-getter|0|203-preInit
            // write pre-init user code here
            okCommand10 = new Command("Ok", Command.OK, 0);//GEN-LINE:|203-getter|1|203-postInit
            // write post-init user code here
        }//GEN-BEGIN:|203-getter|2|
        return okCommand10;
    }
    //</editor-fold>//GEN-END:|203-getter|2|
    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay() {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable(null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet();
        } else {
            initialize();
            startMIDlet();
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
