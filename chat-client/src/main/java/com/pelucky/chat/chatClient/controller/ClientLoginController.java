package com.pelucky.chat.chatClient.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pelucky.chat.chatClient.TcpSocketClient;
import com.pelucky.chat.chatClient.view.ClientChatView;
import com.pelucky.chat.chatClient.view.ClientLoginView;

public class ClientLoginController implements ActionListener {

    private ClientLoginView clientLoginView;
    private TcpSocketClient tcpSocketClient;
    private Logger logger = LoggerFactory.getLogger(ClientChatController.class);

    public ClientLoginController(ClientLoginView clientLoginView) {
        this.clientLoginView = clientLoginView;
    }

    /*
     * When button is pressed, actionPerformed will execute
     * TODO: rework this function, so ugly
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("login".equals(e.getActionCommand())) {
            String port = clientLoginView.getjTextFieldPort().getText();
            String host = clientLoginView.getjTextFieldHost().getText();
            String username = clientLoginView.getjTextFieldUsername().getText();
            if (isPortNumber(port) && !username.isEmpty()) {
                tcpSocketClient = new TcpSocketClient(host, port, username);
                // Establish the connect with server
                if (tcpSocketClient.startClient()) {
                    // Start a thread to receive server's userList
                    tcpSocketClient.receiveUserListThread();

                    // Delay for Server send userList to client;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e1) {
                        logger.info(e1.getMessage());
                    }

                    // Check getUserList or not
                    while (tcpSocketClient.getUserList() == null) {}

                    // Close client login view and open client chat view
                    if (tcpSocketClient.checkUsernameAvaliable()){
                        clientLoginView.getFrame().dispose();
                        ClientChatView clientChatView = new ClientChatView(tcpSocketClient);
                        clientChatView.run();

                        // Start to send userName and receive
                        tcpSocketClient.startThread();
                        clientChatView.getjTextAreaUserList().setText(tcpSocketClient.getUserList());
                    // if userName is in use, close tcpSocketClient and show tips
                    } else {
                        clientLoginView.getjLabelInfo().setText("Username has existed, change one!");
                        tcpSocketClient.stop();
                    }

                } else {
                    clientLoginView.getjLabelInfo().setText("Can't connect server!");
                }

            } else {
                clientLoginView.getjLabelInfo().setText("Port: 1025-65535, type a username!");
            }
        }
    }


    /**
     * @param portNumber
     * @return whether portNumber is validate
     */
    private boolean isPortNumber(String portNumber) {
        if (portNumber != null && !portNumber.isEmpty() && portNumber.matches("\\d+")
                && Integer.parseInt(portNumber) > 1024 && Integer.parseInt(portNumber) < 65535) {
            return true;
        }
        return false;
    }
}
