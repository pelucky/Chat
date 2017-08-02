package com.pelucky.chat.chatClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientChatController  extends WindowAdapter implements ActionListener {

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
            message = "#2." + tcpSocketClient.getUsername() + ": " + message;
            message.replace("\r\n", "#@");
            try {
                new Thread(new SendThread(tcpSocketClient.getSocket().getOutputStream(), message)).start();
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

    @Override
    public void windowClosing(WindowEvent e) {
        // Send close message to server and close socket;
        String message = "#3." + tcpSocketClient.getUsername();
        message = message.replace("\r\n", "#@");
        try {
            logger.info("Send message: {}", message);
            new Thread(new SendThread(tcpSocketClient.getSocket().getOutputStream(), message)).start();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        tcpSocketClient.stop();

        // Close frame and exit;
        clientChatView.getFrame().dispose();
        System.exit(0);
    }
}
