package com.pelucky.chat.chatClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpSocketClient {

    private Logger logger = LoggerFactory.getLogger(TcpSocketClient.class);
    private Socket socket;
    private Thread readThread;
    private ReceiveThread receiveThread;
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
            e.printStackTrace();
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
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startThread() {
        synchronized (socket) {
            try {
                new Thread(new SendThread(socket.getOutputStream(), "#1." + username)).start();

                receiveThread = new ReceiveThread(this);
                readThread = new Thread(receiveThread);
                readThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void receiveUserListThread() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                BufferedReader bufferedReader = null;
                String line;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while ((line = bufferedReader.readLine()) != null) {
                        logger.info("line: {}", line);
                        if (line.startsWith("#3.")) {
                            line = line.replace("#@", "\r\n");
                            userList = line.substring(3);
                            logger.info("userList: {}", userList);
                            logger.info("Check userList is null? {}", userList == null);
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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

    @Override
    public String toString() {
        return "host: " + host + ", locaLport: " + socket.getLocalPort() + ", port: " + port + ", username: "
                + username;
    }
}
