package com.pelucky.chat.chatClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiveThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ReceiveThread.class);
    private InputStream inputStream;
    private ClientChatController clientChatController;
    private BufferedReader bufferedReader = null;
    private TcpSocketClient tcpSocketClient;

    public ReceiveThread(TcpSocketClient tcpSocketClient) throws IOException {
        this.tcpSocketClient = tcpSocketClient;
        this.inputStream = tcpSocketClient.getSocket().getInputStream();
        this.clientChatController = tcpSocketClient.getClientChatController();
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replace("#@", "\r\n");
                logger.info("Client: {}", line);
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


    private void updateUserList(String userList) {
        tcpSocketClient.setUserList(userList);
        clientChatController.updatejTextAreaUserList(userList);
    }

    private void updateReceiveMessage(String message) {
        clientChatController.updatejTextAreaReceive(message);
    }
}
