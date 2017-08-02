package com.pelucky.chat.chatClient.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pelucky.chat.chatClient.TcpSocketClient;

public class ReceiveThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ReceiveThread.class);
    private BufferedReader bufferedReader = null;
    private TcpSocketClient tcpSocketClient;

    public ReceiveThread(TcpSocketClient tcpSocketClient) throws IOException {
        this.tcpSocketClient = tcpSocketClient;
        bufferedReader = new BufferedReader(new InputStreamReader(tcpSocketClient.getSocket().getInputStream()));
    }

    /*
     * Receive data from server #1. for updateUserList #2. for receiveMessage
     */
    @Override
    public void run() {
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replace("#@", "\r\n");
                if (line.startsWith("#1.")) {
                    updateUserList(line.substring(3));
                } else if (line.startsWith("#2.")) {
                    updateReceiveMessage(line.substring(3));
                } else {
                    logger.info("Error message types, Please check!");
                }
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
            ;
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
     * Update userList in tcpSocketClient Update textAreaUserList in
     * clientChatView
     */
    private void updateUserList(String userList) {
        tcpSocketClient.setUserList(userList);
        tcpSocketClient.updatejTextAreaUserList(userList);
    }

    /*
     * Update textAreaReceive in clientChatView
     */
    private void updateReceiveMessage(String message) {
        tcpSocketClient.updatejTextAreaReceive(message);
    }
}
