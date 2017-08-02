package com.pelucky.chat.chatServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerController implements ActionListener {

    private ServerView serverView;
    private String portNumber;
    private TcpSocketServer tcpSocketServer;

    public ServerController(ServerView serverView) {
        this.serverView = serverView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("start".equals(e.getActionCommand())) {
            portNumber = serverView.getjTextFieldPort().getText();
            if (isPortNumber(portNumber)) {
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

    public ServerView getServerView() {
        return serverView;
    }
}
