package com.pelucky.chat.chatClient.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pelucky.chat.chatClient.TcpSocketClient;
import com.pelucky.chat.chatClient.thread.SendThread;
import com.pelucky.chat.chatClient.view.ClientChatView;

public class ClientChatController  extends WindowAdapter implements ActionListener {

    private Logger logger = LoggerFactory.getLogger(ClientChatController.class);
    private ClientChatView clientChatView;
    private TcpSocketClient tcpSocketClient;

    /*
     * TODO: rework constructor of ClientChatController
     * How to pass tcpSocketClient to ClientCHatController more gracefully?
     */
    public ClientChatController(ClientChatView clientChatView, TcpSocketClient tcpSocketClient) {
        this.clientChatView = clientChatView;
        this.tcpSocketClient = tcpSocketClient;
        this.tcpSocketClient.setClientChatController(this);
    }

    /*
     * Send message to server by #2.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("send".equals(e.getActionCommand())) {
            String message = clientChatView.getJtextFieldSendMessage().getText();
            message = "#2." + tcpSocketClient.getUsername() + ": " + message;
            message.replace("\r\n", "#@");
            try {
                new Thread(new SendThread(tcpSocketClient.getSocket().getOutputStream(), message)).start();
            } catch (IOException e1) {
                logger.info(e1.getMessage());
            }
            clientChatView.getJtextFieldSendMessage().setText("");
        }
    }

    public void updatejTextAreaUserList(String userList) {
        clientChatView.getjTextAreaUserList().setText(userList);
    }

    /*
     * Use append to add
     */
    public void updatejTextAreaReceive(String receiveMessage) {
        clientChatView.getjTextAreaReceive().append(receiveMessage + "\r\n");
    }

    /*
     * Add for click close window button
     */
    @Override
    public void windowClosing(WindowEvent e) {
        // Send close message to server and close socket;
        String message = "#3." + tcpSocketClient.getUsername();
        message = message.replace("\r\n", "#@");
        try {
            new Thread(new SendThread(tcpSocketClient.getSocket().getOutputStream(), message)).start();
        } catch (IOException e1) {
            logger.info(e1.getMessage());
        }

        // Sleep a while for making sure to send the server completely
        try {
            Thread.sleep(500);
        } catch (InterruptedException e1) {
            logger.info(e1.getMessage());
        }
        tcpSocketClient.stop();

        // Close frame and exit;
        clientChatView.getFrame().dispose();
        System.exit(0);
    }
}
