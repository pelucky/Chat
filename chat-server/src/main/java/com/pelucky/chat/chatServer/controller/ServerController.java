package com.pelucky.chat.chatServer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.pelucky.chat.chatServer.TcpSocketServer;
import com.pelucky.chat.chatServer.view.ServerView;

public class ServerController implements ActionListener {

    private ServerView serverView;
    private TcpSocketServer tcpSocketServer;

    public ServerController(ServerView serverView) {
        this.serverView = serverView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("start".equals(e.getActionCommand())) {
            String portNumber = serverView.getjTextFieldPort().getText();
            if (isPortNumber(portNumber)) {
                // Get server's instance and start accept client
                tcpSocketServer = TcpSocketServer.getInstance();
                if (tcpSocketServer.startServer(this, portNumber)) {
                    serverView.getjButtonStart().setEnabled(false);
                    serverView.getjLabelStatusInfo().setText("Running");
                } else {
                    serverView.getjLabelStatusInfo().setText("Port is in used, change one");
                }
            } else {
                serverView.getjLabelStatusInfo().setText("Port: 1025 - 65535!");
            }
        }
    }

    private boolean isPortNumber(String portNumber) {
        if (portNumber != null && portNumber.length() != 0 && portNumber.matches("\\d+")
                && Integer.parseInt(portNumber) > 1024 && Integer.parseInt(portNumber) < 65535) {
            return true;
        }
        return false;
    }

    public void updateUserList(String userList) {
        serverView.getjTextAreaUserList().setText(userList);
    }

    public String getUserList() {
         return serverView.getjTextAreaUserList().getText();
    }

}
