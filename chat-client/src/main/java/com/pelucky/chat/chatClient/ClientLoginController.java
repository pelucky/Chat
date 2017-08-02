package com.pelucky.chat.chatClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientLoginController implements ActionListener {

    private ClientLoginView clientLoginView;
    private TcpSocketClient tcpSocketClient;
    private Logger logger = LoggerFactory.getLogger(ClientChatController.class);

    public ClientLoginController(ClientLoginView clientLoginView) {
        this.clientLoginView = clientLoginView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("login".equals(e.getActionCommand())) {
            String port = clientLoginView.getjTextFieldPort().getText();
            String host = clientLoginView.getjTextFieldHost().getText();
            String username = clientLoginView.getjTextFieldUsername().getText();
            if (isPortNumber(port) && !username.isEmpty()) {
                tcpSocketClient = new TcpSocketClient(host, port, username);
                if (tcpSocketClient.startClient()) {
                    logger.info(tcpSocketClient.toString());
                    tcpSocketClient.receiveUserListThread();

                    try {
                        // Delay for Server send userlist to client;
                        Thread.sleep(300);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    logger.info("UserList: {}" ,tcpSocketClient.getUserList());
                    while (tcpSocketClient.getUserList() == null) {}
                    if (tcpSocketClient.checkUsernameAvaliable()){
                        clientLoginView.getFrame().dispose();
                        ClientChatView clientChatView = new ClientChatView(tcpSocketClient);
                        clientChatView.run();
                        tcpSocketClient.startThread();
                        clientChatView.getjTextAreaUserList().setText(tcpSocketClient.getUserList());
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


    private boolean isPortNumber(String portNumber) {
        if (portNumber != null && !portNumber.isEmpty() && portNumber.matches("\\d+")
                && Integer.parseInt(portNumber) > 1024 && Integer.parseInt(portNumber) < 65535) {
            return true;
        }
        return false;
    }
}
