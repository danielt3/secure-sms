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
package br.usp.pcs.coop8.ssms.application;

import br.usp.pcs.coop8.ssms.util.Output;
import br.usp.pcs.coop8.ssms.data.Contact;
import br.usp.pcs.coop8.ssms.data.PrivateData;
import br.usp.pcs.coop8.ssms.messaging.SigncryptedMessage;
import br.usp.pcs.coop8.ssms.protocol.exception.CipherException;
import javax.microedition.io.PushRegistry;
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
public class SSMSMain extends MIDlet implements CommandListener {

    private boolean midletPaused = false;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">                      
    private List listaInicial;
    private Form formFirstUse;
    private Form formFirstUse2;
    private Form formOutput;
    private Form formAutenticarContato;
    private Form formSendMessage;
    private Form formXAReadMessage;
    private Form formReadMessage;
    private TextField txtOutput;
    private TextField txtContactPhone;
    private TextField txtContactName;
    private TextField txtMessage;
    private TextField txtMyId;
    private TextField txtKgbId;
    private TextField txtXAFirstTime1;
    private TextField txtXAFirstTime2;
    private TextField txtXASendMessage;
    private TextField txtXAReadMessage;
    private TextField textField1;
    private List listContacts;
    private List listMessages;
    private Alert alert5;
    private Alert alert4;
    private Command okCommand;
    private Command okCommandFirstScreen = new Command("Ok", Command.OK, 0);
    private Command cancelCommandFirstScreen = new Command("Cancelar", Command.CANCEL, 0);
    //</editor-fold>                    
    //</editor-fold>
    /**
     * The ssms_main constructor.
     */
    public SSMSMain() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">                       
    //</editor-fold>                     

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">                                           
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {
        // write pre-initialize user code here
        txtXAFirstTime1 = new TextField("Senha secreta:", null, 32, TextField.PASSWORD);
        txtXAFirstTime2 = new TextField("Senha secreta:", null, 32, TextField.PASSWORD);
        txtXASendMessage = new TextField("Senha secreta:", null, 32, TextField.PASSWORD);
        txtXAReadMessage = new TextField("Senha secreta:", null, 32, TextField.PASSWORD);

    // write post-initialize user code here
    }
    //</editor-fold>                          

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">                                        
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {

        String[] connections = PushRegistry.listConnections(true);

        if ((connections == null) || (connections.length == 0)) {
        // Foi iniciado pelo usuário
        } else {
            //Foi iniciado pelo push registry
            Output.println("Iniciado pelo push registry!");
        }
        Controller.startApplication(this);

        switchDisplayable(null, getListaInicial());




    // write post-action user code here
    }
    //</editor-fold>                           

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">                                         
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {
    // write pre-action user code here

    // write post-action user code here
    }
    //</editor-fold>                            

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">                                              
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {
        // write pre-switch user code here
        Display display = getDisplay();
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }
    // write post-switch user code here
    }
    //</editor-fold>                                 
    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">                                                 
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {
        // write pre-action user code here

        if ((command == cancelCommandFirstScreen) || (command == okCommandFirstScreen)) {
            switchDisplayable(null, getListaInicial());
        } else if (displayable == alert5) {
            if (command == okCommand) {
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());
            // write post-action user code here
            }
        } else if (displayable == formAutenticarContato) {
            if (command == okCommand) {
                // write pre-action user code here
                Controller.addNewContact(txtContactName.getString(), txtContactPhone.getString());
                switchDisplayable(null, getListaInicial());
            // write post-action user code here
            }
        } else if (displayable == formFirstUse) {
            if (command == okCommand) {
                // write pre-action user code here
                method2();
            // write post-action user code here
            }
        } else if (displayable == formFirstUse2) {
            if (command == okCommand) {
                // write pre-action user code here
                Controller.finalizeFirstConfig(txtXAFirstTime2.getString());
                switchDisplayable(null, getListaInicial());
            // write post-action user code here
            }
        } else if (displayable == formReadMessage) {
            if (command == okCommand) {
                // write pre-action user code here
                switchDisplayable(null, getListaInicial());
            // write post-action user code here
            }
        } else if (displayable == formSendMessage) {
            if (command == okCommand) {
                // write pre-action user code here
                Controller.sendSigncryptedMessage(txtMessage.getString(), txtXASendMessage.getString());
                switchDisplayable(null, getListaInicial());
            // write post-action user code here
            }
        } else if (displayable == formXAReadMessage) {
            if (command == okCommand) {
                // write pre-action user code here
                switchDisplayable(null, getFormReadMessage());
            // write post-action user code here
            }
        } else if (displayable == listContacts) {
            if (command == List.SELECT_COMMAND) {
                // write pre-action user code here
                listContactsAction();
            // write post-action user code here
            }
        } else if (displayable == listMessages) {
            if (command == List.SELECT_COMMAND) {
                // write pre-action user code here
                listMessagesAction();
            // write post-action user code here
            }
        } else if (displayable == listaInicial) {
            if (command == List.SELECT_COMMAND) {
                // write pre-action user code here
                listaInicialAction();
            // write post-action user code here
            }
        }
    // write post-action user code here
    }
    //</editor-fold>                              
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: listaInicial ">                                   
    /**
     * Returns an initiliazed instance of listaInicial component.
     * @return the initialized component instance
     */
    public List getListaInicial() {
        if (listaInicial == null) {
            // write pre-init user code here
            listaInicial = new List("SMS Seguro", Choice.IMPLICIT);
            listaInicial.append("Enviar torpedo", null);
            listaInicial.append("Ver mensagens", null);
            listaInicial.append("Autenticar Contato", null);
            listaInicial.append("Primeiro uso", null);
            listaInicial.append("Executar testes", null);
            listaInicial.append("Ver Output", null);
            listaInicial.append("Limpar dados", null);
            listaInicial.setCommandListener(this);
            listaInicial.setSelectedFlags(new boolean[]{false, false, false, false, false, false, false});
        // write post-init user code here
        }
        return listaInicial;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listaInicialAction ">                                     
    /**
     * Performs an action assigned to the selected list element in the listaInicial component.
     */
    public void listaInicialAction() {
        // enter pre-action user code here
        String __selectedString = getListaInicial().getString(getListaInicial().getSelectedIndex());
        if (__selectedString != null) {
            if (__selectedString.equals("Primeiro uso")) {
                if (PrivateData.getInstance().getEncryptedQA_c() == null && PrivateData.getInstance().getQA() == null) {
                    switchDisplayable(null, getFormFirstUse());
                } else {
                    if (PrivateData.getInstance().getTA() == null) {
                        switchDisplayable(null, getFormFirstUse2());
                    } else {
                        switchDisplayable(null, getAlert5());
                    }
                }
            } else if (__selectedString.equals("Autenticar Contato")) {

                if (PrivateData.getInstance().getTA() == null) {
                    Alert alert = new Alert("Aviso", "Você ainda não concluiu a configuração inicial. Por favor selecione a opção \"Primeiro uso\".", null, null);
                    alert.addCommand(okCommandFirstScreen);
                    alert.setCommandListener(this);
                    alert.setTimeout(Alert.FOREVER);
                    switchDisplayable(null, alert);
                } else {
                    switchDisplayable(null, getFormAutenticarContato());
                }
            } else if (__selectedString.equals("Enviar torpedo")) {
                if (PrivateData.getInstance().getTA() == null) {
                    Alert alert = new Alert("Aviso", "Você ainda não concluiu a configuração inicial. Por favor selecione a opção \"Primeiro uso\".", null, null);
                    alert.addCommand(okCommandFirstScreen);
                    alert.setCommandListener(this);
                    alert.setTimeout(Alert.FOREVER);
                    switchDisplayable(null, alert);
                } else {
                    switchDisplayable(null, getListContacts());
                }
            } else if (__selectedString.equals("Ver mensagens")) {
                if (PrivateData.getInstance().getTA() == null) {
                    Alert alert = new Alert("Aviso", "Você ainda não concluiu a configuração inicial. Por favor selecione a opção \"Primeiro uso\".", null, null);
                    alert.addCommand(okCommandFirstScreen);
                    alert.setCommandListener(this);
                    alert.setTimeout(Alert.FOREVER);
                    switchDisplayable(null, alert);
                } else {

                    switchDisplayable(null, getListMessages());
                }

            } else if (__selectedString.equals("Ver Output")) {

                switchDisplayable(null, getFormOutput());

            } else if (__selectedString.equals("Executar testes")) {

                Controller.runTestSuite();

            } else if (__selectedString.equals("Limpar dados")) {

                Controller.resetData();

            }
        }
    // enter post-action user code here
    }
    //</editor-fold>                        
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formOutput ">                                   
    /**
     * Returns an initiliazed instance of formOutput component.
     * @return the initialized component instance
     */
    public Form getFormOutput() {
        if (formOutput == null) {
            // write pre-init user code here
            formOutput = new Form("Output", new Item[]{getTxtOutput()});
            formOutput.addCommand(cancelCommandFirstScreen);
            formOutput.setCommandListener(this);
        // write post-init user code here
        } else {
            //Reinicializa o campo texto:            
            txtOutput.setMaxSize(((Output.getOutput().length() != 0)
                    ? Output.getOutput().length() : 1));
            txtOutput.setString(Output.getOutput());
        }
        return formOutput;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField ">                                   
    /**
     * Returns an initiliazed instance of textField component.
     * @return the initialized component instance
     */
    public TextField getTxtOutput() {
        if (txtOutput == null) {
            // write pre-init user code here
            txtOutput = new TextField("textField", Output.getOutput(), ((Output.getOutput().length() != 0)
                    ? Output.getOutput().length() : 1), TextField.ANY);
        // write post-init user code here        

        }
        return txtOutput;
    }
    //</editor-fold>                       
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formAutenticarContato ">                                   
    /**
     * Returns an initiliazed instance of formAutenticarContato component.
     * @return the initialized component instance
     */
    public Form getFormAutenticarContato() {
        if (formAutenticarContato == null) {
            // write pre-init user code here
            formAutenticarContato = new Form("Autenticar contato", new Item[]{getTxtContactPhone(), getTxtContactName()});
            formAutenticarContato.addCommand(getOkCommand());
            formAutenticarContato.addCommand(cancelCommandFirstScreen);
            formAutenticarContato.setCommandListener(this);
        // write post-init user code here
        }
        return formAutenticarContato;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtContactPhone ">                                   
    /**
     * Returns an initiliazed instance of txtContactPhone component.
     * @return the initialized component instance
     */
    public TextField getTxtContactPhone() {
        if (txtContactPhone == null) {
            // write pre-init user code here
            txtContactPhone = new TextField("Telefone do contato", null, 10, TextField.PHONENUMBER);
        // write post-init user code here
        }
        return txtContactPhone;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand ">                                   
    /**
     * Returns an initiliazed instance of okCommand component.
     * @return the initialized component instance
     */
    public Command getOkCommand() {
        if (okCommand == null) {
            // write pre-init user code here
            okCommand = new Command("Ok", Command.OK, 0);
        // write post-init user code here
        }
        return okCommand;
    }
    //</editor-fold>                       
    //<editor-fold defaultstate="collapsed" desc=" Generated Method: method2 ">                               
    /**
     * Performs an action assigned to the method2 if-point.
     */
    public void method2() {
        // enter pre-if user code here
        if (((txtXAFirstTime1.getString() != null &&
                txtXAFirstTime1.getString().length() >= 8) &&
                (txtMyId.getString() != null &&
                txtMyId.getString().length() == 10) && (txtKgbId.getString() != null &&
                txtKgbId.getString().length() == 10))) {
            // write pre-action user code here
            PrivateData.getInstance().setKgbPhone(txtKgbId.getString());
            Controller.firstTimeUse(txtXAFirstTime1.getString(), txtMyId.getString());
            switchDisplayable(null, getListaInicial());
        // write post-action user code here
        } else if (txtXAFirstTime1.getString() == null ||
                txtXAFirstTime1.getString().length() < 8) {
            // write pre-action user code here
            switchDisplayable(null, getAlert4("A senha n\u00E3o atende os requisitos de seguran\u00E7a da aplica\u00E7\u00E3o. Digite outra senha"));
        // write post-action user code here
        } else {
            switchDisplayable(null, getAlert4("Os números devem conter 10 dígitos cada."));
        }
    // enter post-if user code here
    }
    //</editor-fold>                    

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: alert4 ">                                     
    /**
     * Returns an initiliazed instance of alert4 component.
     * @return the initialized component instance
     */
    public Alert getAlert4(String texto) {        
        if (alert4 == null) {
            // write pre-init user code here
            alert4 = new Alert("Alerta", texto, null, null);
            alert4.setTimeout(Alert.FOREVER);
        // write post-init user code here
        } else {
            alert4.setString(texto);
        }        
        return alert4;
    }
    //</editor-fold>                        

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formFirstUse ">                                     
    /**
     * Returns an initiliazed instance of formFirstUse component.
     * @return the initialized component instance
     */
    public Form getFormFirstUse() {
        if (formFirstUse == null) {
            // write pre-init user code here
            formFirstUse = new Form("Primeiro uso", new Item[]{txtXAFirstTime1, getTxtMyId(), getTxtKgbId()});
            formFirstUse.addCommand(getOkCommand());
            formFirstUse.addCommand(cancelCommandFirstScreen);
            formFirstUse.setCommandListener(this);
        // write post-init user code here
        }
        return formFirstUse;
    }
    //</editor-fold>                        
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtMyId ">                                     
    /**
     * Returns an initiliazed instance of txtMyId component.
     * @return the initialized component instance
     */
    public TextField getTxtMyId() {
        if (txtMyId == null) {
            // write pre-init user code here
            txtMyId = new TextField("Entre com seu numero de telefone:", null, 32, TextField.PHONENUMBER);
        // write post-init user code here
        }
        return txtMyId;
    }
    //</editor-fold>              
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtKgbId ">                                     
    /**
     * Returns an initiliazed instance of txtMyId component.
     * @return the initialized component instance
     */
    public TextField getTxtKgbId() {
        if (txtKgbId == null) {
            // write pre-init user code here
            txtKgbId = new TextField("Entre com o numero de telefone da KGB:", null, 32, TextField.PHONENUMBER);
        // write post-init user code here
        }
        txtKgbId.setString(PrivateData.getInstance().getKgbPhone());
        return txtKgbId;
    }
    //</editor-fold>  
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: alert5 ">                                     
    /**
     * Returns an initiliazed instance of alert5 component.
     * @return the initialized component instance
     */
    public Alert getAlert5() {
        if (alert5 == null) {
            // write pre-init user code here
            alert5 = new Alert("Alerta", "Voc\u00EA j\u00E1 fez a configura\u00E7\u00E3o.", null, null);
            alert5.addCommand(getOkCommand());
            alert5.setCommandListener(this);
            alert5.setTimeout(Alert.FOREVER);
        // write post-init user code here
        }
        return alert5;
    }
    //</editor-fold>                        

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formFirstUse2 ">                                     
    /**
     * Returns an initiliazed instance of formFirstUse2 component.
     * @return the initialized component instance
     */
    public Form getFormFirstUse2() {
        if (formFirstUse2 == null) {
            // write pre-init user code here
            formFirstUse2 = new Form("Calcular par\u00E2metros", new Item[]{txtXAFirstTime2});
            formFirstUse2.addCommand(getOkCommand());
            formFirstUse2.addCommand(cancelCommandFirstScreen);
            formFirstUse2.setCommandListener(this);
        // write post-init user code here
        }
        return formFirstUse2;
    }
    //</editor-fold>                        
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtContactName ">                                     
    /**
     * Returns an initiliazed instance of txtContactName component.
     * @return the initialized component instance
     */
    public TextField getTxtContactName() {
        if (txtContactName == null) {
            // write pre-init user code here
            txtContactName = new TextField("Nome do contato", null, 32, TextField.ANY);
        // write post-init user code here
        }
        return txtContactName;
    }
    //</editor-fold>                        

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: listContacts ">                                     
    /**
     * Returns an initiliazed instance of listContacts component.
     * @return the initialized component instance
     */
    public List getListContacts() {
        if (listContacts == null) {
            // write pre-init user code here
            listContacts = new List("Contatos", Choice.IMPLICIT);
            listContacts.setCommandListener(this);
            listContacts.setSelectedFlags(new boolean[]{});
            // write post-init user code here
            listContacts.addCommand(cancelCommandFirstScreen);
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

        }
        return listContacts;
    }
    //</editor-fold>                        

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listContactsAction ">                                       
    /**
     * Performs an action assigned to the selected list element in the listContacts component.
     */
    public void listContactsAction() {
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

                    if (((Contact) results.get(0)).getYA() != null) {
                        //Tudo bem, ele já é autenticado                        
                        Controller.setSelectedContact((Contact) results.get(0));

                        switchDisplayable(null, getFormSendMessage());
                    } else {
                        //Contato ainda não autenticado!
                        Alert alert = new Alert("Aviso", "Este contato ainda não está autenticado. Impossível enviar mensagem.", null, null);
                        alert.addCommand(okCommandFirstScreen);
                        alert.setCommandListener(this);
                        alert.setTimeout(Alert.FOREVER);
                        switchDisplayable(null, alert);
                    }


                } catch (FloggyException ex) {
                    ex.printStackTrace();
                }
            }
            return;
        }

    // enter post-action user code here
    }
    //</editor-fold>                        
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formSendMessage ">                                     
    /**
     * Returns an initiliazed instance of formSendMessage component.
     * @return the initialized component instance
     */
    public Form getFormSendMessage() {
        if (formSendMessage == null) {
            // write pre-init user code here
            formSendMessage = new Form("Enviar mensagem", new Item[]{new StringItem(Controller.getSelectedContact().getName() + "/" + Controller.getSelectedContact().getPhone(), ""), getTxtMessage(), txtXASendMessage});
            formSendMessage.addCommand(getOkCommand());
            formSendMessage.addCommand(cancelCommandFirstScreen);
            formSendMessage.setCommandListener(this);
        // write post-init user code here
        }
        return formSendMessage;
    }
    //</editor-fold>                        

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtMessage ">                                     
    /**
     * Returns an initiliazed instance of txtMessage component.
     * @return the initialized component instance
     */
    public TextField getTxtMessage() {
        if (txtMessage == null) {
            // write pre-init user code here
            txtMessage = new TextField("Mensagem:", "", 140, TextField.ANY);
        // write post-init user code here
        }
        return txtMessage;
    }
    //</editor-fold>                        
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: listMessages ">                                     
    /**
     * Returns an initiliazed instance of listMessages component.
     * @return the initialized component instance
     */
    public List getListMessages() {
        if (listMessages == null) {
            // write pre-init user code here
            listMessages = new List("Mensagens recebidas", Choice.IMPLICIT);
            listMessages.setCommandListener(this);
            listMessages.setSelectedFlags(new boolean[]{});
            // write post-init user code here
            listMessages.addCommand(cancelCommandFirstScreen);
        } else {
            //Atualizar a lista
            listMessages.deleteAll();
        }
        {
            //Sempre preenche a lista
            try {
                PersistableManager perMan = PersistableManager.getInstance();
                ObjectSet results = perMan.find(SigncryptedMessage.getThisClass(), null, null);

                for (int i = 0; i < results.size(); i++) {
                    listMessages.append(((SigncryptedMessage) results.get(i)).getSender() + "/" + ((SigncryptedMessage) results.get(i)).getDate(), null);
                }
            } catch (FloggyException ex) {
                ex.printStackTrace();
            }
        }
        return listMessages;
    }
    //</editor-fold>                        

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listMessagesAction ">                                       
    /**
     * Performs an action assigned to the selected list element in the listMessages component.
     */
    public void listMessagesAction() {
        // enter pre-action user code here
        if (true) {
            final String __selectedString = listMessages.getString(listMessages.getSelectedIndex());
            if (__selectedString != null) {
                try {
                    PersistableManager perMan = PersistableManager.getInstance();
                    ObjectSet results = perMan.find(SigncryptedMessage.getThisClass(), new Filter() {

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

    // enter post-action user code here
    }
    //</editor-fold>                        
    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formReadMessage ">                                     
    /**
     * Returns an initiliazed instance of formReadMessage component.
     * @return the initialized component instance
     */
    public Form getFormReadMessage() {
        if (formReadMessage == null) {
            // write pre-init user code here
            formReadMessage = new Form("Mensagem", new Item[]{new StringItem(Controller.getSelectedMessage().getSender(), Controller.getSelectedMessage().getDate().toString()), getTextField1()});
            formReadMessage.addCommand(getOkCommand());
            formReadMessage.setCommandListener(this);
        // write post-init user code here 
        }
        // Garante que irá atualizar o texto
        String clearText;
        try {
            clearText = Controller.getUnsigncryptedText(txtXAReadMessage.getString());
        } catch (CipherException ex) {
            clearText = "Erro na vericifração, senha inválida ou o remetente não é confiável.";
        }
        textField1.setString(clearText);

        return formReadMessage;
    }
    //</editor-fold>                        

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField1 ">                                     
    /**
     * Returns an initiliazed instance of textField1 component.
     * @return the initialized component instance
     */
    public TextField getTextField1() {
        if (textField1 == null) {
            // write pre-init user code here
            textField1 = new TextField("Mensagem:", null, 140, TextField.ANY);
        // write post-init user code here
        }
        return textField1;
    }
    //</editor-fold>                        

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formXAReadMessage ">                                     
    /**
     * Returns an initiliazed instance of formXAReadMessage component.
     * @return the initialized component instance
     */
    public Form getFormXAReadMessage() {
        if (formXAReadMessage == null) {
            // write pre-init user code here
            formXAReadMessage = new Form("Ler mensagem", new Item[]{txtXAReadMessage});
            formXAReadMessage.addCommand(getOkCommand());
            formXAReadMessage.addCommand(cancelCommandFirstScreen);
            formXAReadMessage.setCommandListener(this);
        // write post-init user code here
        }
        return formXAReadMessage;
    }
    //</editor-fold>                        
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
