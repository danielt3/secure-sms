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
import br.usp.pcs.coop8.ssms.protocol.exception.CipherException;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import net.sourceforge.floggy.persistence.Filter;
import net.sourceforge.floggy.persistence.FloggyException;
import net.sourceforge.floggy.persistence.ObjectSet;
import net.sourceforge.floggy.persistence.Persistable;
import net.sourceforge.floggy.persistence.PersistableManager;

/**
 * @author nano
 */
public class ssms_main extends MIDlet implements CommandListener {

    private boolean midletPaused = false;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private List listaInicial;
    private Form formOutput;
    private TextField textField;
    private Form formAutenticarContato;
    private TextField txtContactPhone;
    private TextField txtContactName;
    private Alert alert4;
    private Form formFirstUse;
    private TextField txtXA;
    private TextField txtMyId;
    private Alert alert5;
    private List listContacts;
    private Form formFirstUse2;
    private TextField txtXA_GerarPubs;
    private List listMessages;
    private Form formSendMessage;
    private TextField txtXA_SendMessage;
    private TextField txtMessage;
    private StringItem stringItem;
    private Form formXAReadMessage;
    private TextField txtXA_ReadMessage;
    private Form formReadMessage;
    private TextField textField1;
    private StringItem stringItem1;
    private Alert alert;
    private Command cancelCommand;
    private Command okCommand;
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



    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        
        if (command == cancelCommand && 
                (displayable == listContacts || displayable == listMessages)) {
            switchDisplayable(null, getListaInicial());
        }
            
        
        if (displayable == alert5) {//GEN-BEGIN:|7-commandAction|1|213-preAction
            if (command == okCommand) {//GEN-END:|7-commandAction|1|213-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|2|213-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|3|211-preAction
        } else if (displayable == formAutenticarContato) {
            if (command == cancelCommand) {//GEN-END:|7-commandAction|3|211-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|4|211-postAction
                // write post-action user code here
            } else if (command == okCommand) {//GEN-LINE:|7-commandAction|5|66-preAction
                // write pre-action user code here
                Controller.addNewContact(txtContactName.getString(), txtContactPhone.getString());//GEN-LINE:|7-commandAction|6|66-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|7|207-preAction
        } else if (displayable == formFirstUse) {
            if (command == cancelCommand) {//GEN-END:|7-commandAction|7|207-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|8|207-postAction
                // write post-action user code here
            } else if (command == okCommand) {//GEN-LINE:|7-commandAction|9|206-preAction
                // write pre-action user code here
                method2();//GEN-LINE:|7-commandAction|10|206-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|11|216-preAction
        } else if (displayable == formFirstUse2) {
            if (command == cancelCommand) {//GEN-END:|7-commandAction|11|216-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|12|216-postAction
                // write post-action user code here
            } else if (command == okCommand) {//GEN-LINE:|7-commandAction|13|215-preAction
                // write pre-action user code here
                Controller.finalizeFirstConfig(txtXA_GerarPubs.getString());//GEN-LINE:|7-commandAction|14|215-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|15|219-preAction
        } else if (displayable == formOutput) {
            if (command == cancelCommand) {//GEN-END:|7-commandAction|15|219-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|16|219-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|17|223-preAction
        } else if (displayable == formReadMessage) {
            if (command == okCommand) {//GEN-END:|7-commandAction|17|223-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|18|223-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|19|231-preAction
        } else if (displayable == formSendMessage) {
            if (command == cancelCommand) {//GEN-END:|7-commandAction|19|231-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|20|231-postAction
                // write post-action user code here
            } else if (command == okCommand) {//GEN-LINE:|7-commandAction|21|230-preAction
                // write pre-action user code here
                Controller.sendSigncryptedMessage(txtMessage.getString(), txtXA_SendMessage.getString());//GEN-LINE:|7-commandAction|22|230-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|23|227-preAction
        } else if (displayable == formXAReadMessage) {
            if (command == cancelCommand) {//GEN-END:|7-commandAction|23|227-preAction
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());//GEN-LINE:|7-commandAction|24|227-postAction
                // write post-action user code here
            } else if (command == okCommand) {//GEN-LINE:|7-commandAction|25|225-preAction
                // write pre-action user code here
                switchDisplayable(null, getFormReadMessage());//GEN-LINE:|7-commandAction|26|225-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|27|158-preAction
        } else if (displayable == listContacts) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|27|158-preAction
                // write pre-action user code here
                listContactsAction();//GEN-LINE:|7-commandAction|28|158-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|29|175-preAction
        } else if (displayable == listMessages) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|29|175-preAction
                // write pre-action user code here
                listMessagesAction();//GEN-LINE:|7-commandAction|30|175-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|31|28-preAction
        } else if (displayable == listaInicial) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|31|28-preAction
                // write pre-action user code here
                listaInicialAction();//GEN-LINE:|7-commandAction|32|28-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|33|7-postCommandAction
        }//GEN-END:|7-commandAction|33|7-postCommandAction
    // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|34|
    //</editor-fold>//GEN-END:|7-commandAction|34|
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: listaInicial ">//GEN-BEGIN:|26-getter|0|26-preInit
    /**
     * Returns an initiliazed instance of listaInicial component.
     * @return the initialized component instance
     */
    public List getListaInicial() {
        if (listaInicial == null) {//GEN-END:|26-getter|0|26-preInit
            // write pre-init user code here
            listaInicial = new List("SSMS", Choice.IMPLICIT);//GEN-BEGIN:|26-getter|1|26-postInit
            listaInicial.append("Primeiro uso", null);
            listaInicial.append("Autenticar Contato", null);
            listaInicial.append("Enviar torpedo", null);
            listaInicial.append("Ver mensagens", null);
            listaInicial.append("Ver OutPut", null);
            listaInicial.append("Executar testes", null);
            listaInicial.append("Limpar dados", null);
            listaInicial.setCommandListener(this);
            listaInicial.setSelectedFlags(new boolean[] { false, false, false, false, false, false, false });//GEN-END:|26-getter|1|26-postInit
        // write post-init user code here
        }//GEN-BEGIN:|26-getter|2|
        return listaInicial;
    }
    //</editor-fold>//GEN-END:|26-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listaInicialAction ">//GEN-BEGIN:|26-action|0|26-preAction
    /**
     * Performs an action assigned to the selected list element in the listaInicial component.
     */
    public void listaInicialAction() {//GEN-END:|26-action|0|26-preAction
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
                switchDisplayable(null, getFormOutput());//GEN-LINE:|26-action|10|35-postAction
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
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formOutput ">//GEN-BEGIN:|51-getter|0|51-preInit
    /**
     * Returns an initiliazed instance of formOutput component.
     * @return the initialized component instance
     */
    public Form getFormOutput() {
        if (formOutput == null) {//GEN-END:|51-getter|0|51-preInit
            // write pre-init user code here
            formOutput = new Form("Output", new Item[] { getTextField() });//GEN-BEGIN:|51-getter|1|51-postInit
            formOutput.addCommand(getCancelCommand());
            formOutput.setCommandListener(this);//GEN-END:|51-getter|1|51-postInit
        // write post-init user code here
        } else {
            //Reinicializa o campo texto:            
            textField.setMaxSize(((Output.getOutput().length() != 0)
                    ? Output.getOutput().length() : 1));
            textField.setString(Output.getOutput());
        }//GEN-BEGIN:|51-getter|2|
        return formOutput;
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
            
        }//GEN-BEGIN:|52-getter|2|
        return textField;
    }
    //</editor-fold>//GEN-END:|52-getter|2|


    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formAutenticarContato ">//GEN-BEGIN:|61-getter|0|61-preInit
    /**
     * Returns an initiliazed instance of formAutenticarContato component.
     * @return the initialized component instance
     */
    public Form getFormAutenticarContato() {
        if (formAutenticarContato == null) {//GEN-END:|61-getter|0|61-preInit
            // write pre-init user code here
            formAutenticarContato = new Form("Autenticar contato", new Item[] { getTxtContactPhone(), getTxtContactName() });//GEN-BEGIN:|61-getter|1|61-postInit
            formAutenticarContato.addCommand(getOkCommand());
            formAutenticarContato.addCommand(getCancelCommand());
            formAutenticarContato.setCommandListener(this);//GEN-END:|61-getter|1|61-postInit
        // write post-init user code here
        }//GEN-BEGIN:|61-getter|2|
        return formAutenticarContato;
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
            alert4 = new Alert("Alerta", "A senha n\u00E3o atende os requisitos de seguran\u00E7a da aplica\u00E7\u00E3o. Digite outra senha", null, null);//GEN-BEGIN:|106-getter|1|106-postInit
            alert4.setTimeout(Alert.FOREVER);//GEN-END:|106-getter|1|106-postInit
        // write post-init user code here
        }//GEN-BEGIN:|106-getter|2|
        return alert4;
    }
    //</editor-fold>//GEN-END:|106-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formFirstUse ">//GEN-BEGIN:|107-getter|0|107-preInit
    /**
     * Returns an initiliazed instance of formFirstUse component.
     * @return the initialized component instance
     */
    public Form getFormFirstUse() {
        if (formFirstUse == null) {//GEN-END:|107-getter|0|107-preInit
            // write pre-init user code here
            formFirstUse = new Form("Primeiro uso", new Item[] { getTxtXA(), getTxtMyId() });//GEN-BEGIN:|107-getter|1|107-postInit
            formFirstUse.addCommand(getOkCommand());
            formFirstUse.addCommand(getCancelCommand());
            formFirstUse.setCommandListener(this);//GEN-END:|107-getter|1|107-postInit
        // write post-init user code here
        }//GEN-BEGIN:|107-getter|2|
        return formFirstUse;
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
            switchDisplayable(null, getFormFirstUse());//GEN-LINE:|130-if|2|131-postAction
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
            switchDisplayable(null, getFormFirstUse2());//GEN-LINE:|135-if|2|136-postAction
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
            alert5 = new Alert("Alerta", "Voc\u00EA j\u00E1 fez a configura\u00E7\u00E3o.", null, null);//GEN-BEGIN:|139-getter|1|139-postInit
            alert5.addCommand(getOkCommand());
            alert5.setCommandListener(this);
            alert5.setTimeout(Alert.FOREVER);//GEN-END:|139-getter|1|139-postInit
        // write post-init user code here
        }//GEN-BEGIN:|139-getter|2|
        return alert5;
    }
    //</editor-fold>//GEN-END:|139-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formFirstUse2 ">//GEN-BEGIN:|144-getter|0|144-preInit
    /**
     * Returns an initiliazed instance of formFirstUse2 component.
     * @return the initialized component instance
     */
    public Form getFormFirstUse2() {
        if (formFirstUse2 == null) {//GEN-END:|144-getter|0|144-preInit
            // write pre-init user code here
            formFirstUse2 = new Form("Calcular par\u00E2metros p\u00FAblicos", new Item[] { txtXA_GerarPubs });//GEN-BEGIN:|144-getter|1|144-postInit
            formFirstUse2.addCommand(getOkCommand());
            formFirstUse2.addCommand(getCancelCommand());
            formFirstUse2.setCommandListener(this);//GEN-END:|144-getter|1|144-postInit
        // write post-init user code here
        }//GEN-BEGIN:|144-getter|2|
        return formFirstUse2;
    }
    //</editor-fold>//GEN-END:|144-getter|2|







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
            listContacts = new List("Contatos", Choice.IMPLICIT);//GEN-BEGIN:|157-getter|1|157-postInit
            listContacts.setCommandListener(this);
            listContacts.setSelectedFlags(new boolean[] {  });//GEN-END:|157-getter|1|157-postInit
        // write post-init user code here
            listContacts.addCommand(getCancelCommand());
        } else {
            listContacts.deleteAll();
        }
        {
            //Sempre preenche
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
            final String __selectedString = listContacts.getString(listContacts.getSelectedIndex());
            if (__selectedString != null) {
                try {
                    PersistableManager perMan = PersistableManager.getInstance();
                    ObjectSet results = perMan.find(Contact.class, new Filter() {

                        public boolean matches(Persistable arg0) {
                            return ((Contact) arg0).getPhone().equals(__selectedString.substring(__selectedString.length() - 10, __selectedString.length()));
                        }
                    }, null);

                    Controller.setSelectedContact((Contact) results.get(0));

                    switchDisplayable(null, getFormSendMessage());
                } catch (FloggyException ex) {
                    ex.printStackTrace();
                }
            }
            return;
        }
        /*

String __selectedString = getListContacts ().getString (getListContacts ().getSelectedIndex ());//GEN-LINE:|157-action|1|157-postAction
    */
         // enter post-action user code here
    }//GEN-BEGIN:|157-action|2|
    //</editor-fold>//GEN-END:|157-action|2|
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formSendMessage ">//GEN-BEGIN:|162-getter|0|162-preInit
    /**
     * Returns an initiliazed instance of formSendMessage component.
     * @return the initialized component instance
     */
    public Form getFormSendMessage() {
        if (formSendMessage == null) {//GEN-END:|162-getter|0|162-preInit
            // write pre-init user code here
            formSendMessage = new Form("Enviar mensagem", new Item[] { getStringItem(), getTxtMessage(), getTxtXA_SendMessage() });//GEN-BEGIN:|162-getter|1|162-postInit
            formSendMessage.addCommand(getOkCommand());
            formSendMessage.addCommand(getCancelCommand());
            formSendMessage.setCommandListener(this);//GEN-END:|162-getter|1|162-postInit
        // write post-init user code here
        }//GEN-BEGIN:|162-getter|2|
        return formSendMessage;
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





    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: listMessages ">//GEN-BEGIN:|174-getter|0|174-preInit
    /**
     * Returns an initiliazed instance of listMessages component.
     * @return the initialized component instance
     */
    public List getListMessages() {
        if (listMessages == null) {//GEN-END:|174-getter|0|174-preInit
            // write pre-init user code here
            listMessages = new List("Mensagens recebidas", Choice.IMPLICIT);//GEN-BEGIN:|174-getter|1|174-postInit
            listMessages.setCommandListener(this);
            listMessages.setSelectedFlags(new boolean[] {  });//GEN-END:|174-getter|1|174-postInit
        // write post-init user code here
            listMessages.addCommand(getCancelCommand());
        } else {
            //Atualizar a lista
            listMessages.deleteAll();
        }
        {
            //Sempre preenche a lista
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
            final String __selectedString = listMessages.getString(listMessages.getSelectedIndex());
            if (__selectedString != null) {
                try {
                    PersistableManager perMan = PersistableManager.getInstance();
                    ObjectSet results = perMan.find(SigncryptedMessage.class, new Filter() {

                        public boolean matches(Persistable arg0) {
                            return __selectedString.equals(((SigncryptedMessage) arg0).getSender() + "/" + ((SigncryptedMessage) arg0).getDate());
                        }
                    }, null);
                    
                    Controller.setSelectedMessage((SigncryptedMessage) results.get(0));

                    switchDisplayable(null, getFormXAReadMessage());
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
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formReadMessage ">//GEN-BEGIN:|180-getter|0|180-preInit
    /**
     * Returns an initiliazed instance of formReadMessage component.
     * @return the initialized component instance
     */
    public Form getFormReadMessage() {
        if (formReadMessage == null) {//GEN-END:|180-getter|0|180-preInit
            // write pre-init user code here
            formReadMessage = new Form("Mensagem", new Item[] { getStringItem1(), getTextField1() });//GEN-BEGIN:|180-getter|1|180-postInit
            formReadMessage.addCommand(getOkCommand());
            formReadMessage.setCommandListener(this);//GEN-END:|180-getter|1|180-postInit
        // write post-init user code here 
        } else {
            // Garante que irá atualizar o texto
            try {
                textField1.setString(Controller.getUnsigncryptedText(txtXA_ReadMessage.getString()));
            } catch (CipherException ex) {
                textField1 = new TextField("Mensagem:", "ERRO!", 140, TextField.ANY);                                     
            }

        }//GEN-BEGIN:|180-getter|2|
        return formReadMessage;
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
            try {
            textField1 = new TextField("Mensagem:", Controller.getUnsigncryptedText(txtXA_ReadMessage.getString()), 140, TextField.ANY);//GEN-LINE:|182-getter|1|182-postInit
        // write post-init user code here
            } catch (CipherException ex) {
                textField1 = new TextField("Mensagem:", "ERRO!", 140, TextField.ANY);                                     
            }
        
        }//GEN-BEGIN:|182-getter|2|
        return textField1;
    }
    //</editor-fold>//GEN-END:|182-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formXAReadMessage ">//GEN-BEGIN:|183-getter|0|183-preInit
    /**
     * Returns an initiliazed instance of formXAReadMessage component.
     * @return the initialized component instance
     */
    public Form getFormXAReadMessage() {
        if (formXAReadMessage == null) {//GEN-END:|183-getter|0|183-preInit
            // write pre-init user code here
            formXAReadMessage = new Form("Ler mensagem", new Item[] { getTxtXA_ReadMessage() });//GEN-BEGIN:|183-getter|1|183-postInit
            formXAReadMessage.addCommand(getOkCommand());
            formXAReadMessage.addCommand(getCancelCommand());
            formXAReadMessage.setCommandListener(this);//GEN-END:|183-getter|1|183-postInit
        // write post-init user code here
        }//GEN-BEGIN:|183-getter|2|
        return formXAReadMessage;
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

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: alert ">//GEN-BEGIN:|234-getter|0|234-preInit
    /**
     * Returns an initiliazed instance of alert component.
     * @return the initialized component instance
     */
    public Alert getAlert() {
        if (alert == null) {//GEN-END:|234-getter|0|234-preInit
            // write pre-init user code here
            alert = new Alert("alert", "Este contato ainda n\u00E3o est\u00E1 autenticado. Imposs\u00EDvel enviar mensagem.", null, null);//GEN-BEGIN:|234-getter|1|234-postInit
            alert.setTimeout(Alert.FOREVER);//GEN-END:|234-getter|1|234-postInit
            // write post-init user code here
        }//GEN-BEGIN:|234-getter|2|
        return alert;
    }
    //</editor-fold>//GEN-END:|234-getter|2|














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
