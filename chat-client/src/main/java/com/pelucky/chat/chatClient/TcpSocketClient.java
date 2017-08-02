package com.pelucky.chat.chatClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pelucky.chat.chatClient.controller.ClientChatController;
import com.pelucky.chat.chatClient.thread.ReceiveThread;
import com.pelucky.chat.chatClient.thread.SendThread;

public class TcpSocketClient {

    private Logger logger = LoggerFactory.getLogger(TcpSocketClient.class);
    private Socket socket;
    private Thread readThread;
    private ClientChatController clientChatController;
    private String host;
    private String port;
    private String username;
    private String userList = null;

    public void setUserList(String userList) {
        this.userList = userList;
    }

    public void setClientChatController(ClientChatController clientChatController) {
        this.clientChatController = clientChatController;
    }

    public String getUsername() {
        return username;
    }

    public String getUserList() {
        return userList;
    }

    public Socket getSocket() {
        return socket;
    }

    public ClientChatController getClientChatController() {
        return clientChatController;
    }


    public TcpSocketClient(String host, String port, String username) {
        this.host = host;
        this.port = port;
        this.username = username;
    }

    public boolean startClient() {
        try {
            socket = new Socket(host, Integer.valueOf(port));
            logger.info("Connect server : {}", socket.getRemoteSocketAddress().toString());
        } catch (IOException e) {
            logger.info(e.getMessage());
            stop();
            return false;
        }
        return true;
    }

    public void stop() {
        if (readThread != null) {
            readThread.interrupt();
        }
        try {
            if (socket != null) {
                logger.info("Disconnect to server!");
                socket.close();
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    /*
     * Client will send its userName to server when start thread
     */
    public void startThread() {
        synchronized (socket) {
            try {
                new Thread(new SendThread(socket.getOutputStream(), "#1." + username)).start();

                readThread = new Thread(new ReceiveThread(this));
                readThread.start();
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }
    }

    /*
     * Client start a thread for receiving userList from server to check
     * userName is valid #3. used for receive userList
     */
    public void receiveUserListThread() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                BufferedReader bufferedReader = null;
                String line;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.startsWith("#3.")) {
                            line = line.replace("#@", "\r\n");
                            userList = line.substring(3);
                            break;
                        }
                    }
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }

        }).start();
    }

    public boolean checkUsernameAvaliable() {
        if (userList.contains(username)) {
            return false;
        }
        return true;
    }

    public void updatejTextAreaUserList(String userList) {
        clientChatController.updatejTextAreaUserList(userList);
    }

    public void updatejTextAreaReceive(String message) {
        clientChatController.updatejTextAreaReceive(message);
    }

    @Override
    public String toString() {
        return "host: " + host + ", locaLport: " + socket.getLocalPort() + ", port: " + port + ", username: "
                + username;
    }
}
