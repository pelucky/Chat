package com.pelucky.chat.chatServer.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pelucky.chat.chatServer.Client;
import com.pelucky.chat.chatServer.TcpSocketServer;

public class ReceiveThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ReceiveThread.class);
    private Client client;

    public ReceiveThread(Client client) {
        this.client = client;
    }

    /*
     * Receive data from client
     * #1. for updateUserList
     * #2. for receiveMessage
     * #3. for client close
     */
    public void run() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replace("#@", "\r\n");
                if (line.startsWith("#1.")) {
                    updateUserList(line.substring(3));
                } else if (line.startsWith("#2.")) {
                    updateReceiveMessage(line.substring(3));
                } else if (line.startsWith("#3.")) {
                    removeUserList(line.substring(3));
                } else {
                    logger.info("Error message types, Please check!");
                }
            }
        } catch (IOException e) {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                logger.info(e1.getMessage());
            }
        }
    }

    /*
     * Set userName to client
     * Add client's userName into userList
     * Send new userList to all client by #1.
     */
    private void updateUserList(String username) throws IOException {
        client.setUsername(username);
        TcpSocketServer.getInstance().updateUserList(client);
        sendToAllClient("#1." + TcpSocketServer.getInstance().getUserList());
    }

    /*
     * Send new messages to all client by #2.
     */
    private void updateReceiveMessage(String message) throws IOException {
        sendToAllClient("#2." + message);
    }

    /*
     * Remove client out to userList
     * Send new userList to all client by #1.
     * Close client's thread and socket
     */
    private void removeUserList(String username) throws IOException {
        TcpSocketServer.getInstance().removeUserList(client);
        sendToAllClient("#1." + TcpSocketServer.getInstance().getUserList());
        client.stop();
    }

    /*
     * Iterate all client
     * Send message to all client
     */
    private void sendToAllClient(String message) throws IOException {
        Collection<Client> clientArray = TcpSocketServer.getInstance().getClientArray();
        for (Client client : clientArray) {
            new Thread(new SendThread(client.getSocket().getOutputStream(), message)).start();
        }
    }
}
