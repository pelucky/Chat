package com.pelucky.chat.chatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiveThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ReceiveThread.class);
    private Client client;

    public ReceiveThread(Client client) {
        this.client = client;
    }

    public void run() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replace("#@", "\r\n");
                logger.info("Server: {}", line);
                if (line.startsWith("#1.")) {
                    updateUserList(line.substring(3));
                } else if (line.startsWith("#2.")) {
                    updateReceiveMessage(line.substring(3));
                } else {
                    logger.info("Error message types");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void updateUserList(String username) throws IOException {
        client.setUsername(username);
        TcpSocketServer.getInstance().updateUserList(client);
        sendToAllClient("#1." + TcpSocketServer.getInstance().getUserList());
    }

    private void updateReceiveMessage(String message) throws IOException {
        sendToAllClient("#2." + message);
    }

    private void sendToAllClient(String message) throws IOException {
        Collection<Client> clientArray = TcpSocketServer.getInstance().getClientArray();
        for (Client client : clientArray) {
            logger.info(client.toString());
            new Thread(new SendThread(client.getSocket().getOutputStream(), message)).start();
        }
    }
}
