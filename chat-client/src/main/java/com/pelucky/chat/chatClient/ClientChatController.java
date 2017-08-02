package com.pelucky.chat.chatClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientChatController implements ActionListener {

    private Logger logger = LoggerFactory.getLogger(ClientChatController.class);
    private ClientChatView clientChatView;
    private TcpSocketClient tcpSocketClient;

    public ClientChatController(ClientChatView clientChatView, TcpSocketClient tcpSocketClient) {
        this.clientChatView = clientChatView;
        this.tcpSocketClient = tcpSocketClient;
        this.tcpSocketClient.setClientChatController(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("send".equals(e.getActionCommand())) {
            String message = clientChatView.getJtextFieldSendMessage().getText();
            logger.info("#2.{}: {}", tcpSocketClient.getUsername(), message);
            try {
                new Thread(new SendThread(tcpSocketClient.getSocket().getOutputStream(),
                        "#2." + tcpSocketClient.getUsername() + ": " + message)).start();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            clientChatView.getJtextFieldSendMessage().setText("");
        }
    }

    public void updatejTextAreaUserList(String userList) {
        clientChatView.getjTextAreaUserList().setText(userList);
    }

    public void updatejTextAreaReceive(String receiveMessage) {
        clientChatView.getjTextAreaReceive().append(receiveMessage + "\r\n");
    }
}
