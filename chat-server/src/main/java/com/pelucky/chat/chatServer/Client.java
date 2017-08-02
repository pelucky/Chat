package com.pelucky.chat.chatServer;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pelucky.chat.chatServer.thread.ReceiveThread;
import com.pelucky.chat.chatServer.thread.SendThread;

public class Client {
    private String username;
    private String host;
    private int port;
    private Socket socket;
    private Thread readThread;
    private Logger logger = LoggerFactory.getLogger(Client.class);

    public Client(Socket socket) {
        this.socket = socket;
        host = socket.getInetAddress().getHostAddress();
        port = socket.getPort();
        readThread = new Thread(new ReceiveThread(this));
    }

    public Socket getSocket() {
        return socket;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*
     * Send #3. to client for check client's userName if it's in userList Start
     * receiving thread
     */
    public void startThread() throws IOException {
        String message = "#3." + TcpSocketServer.getInstance().getUserList();
        message = message.replace("\r\n", "#@");
        new Thread(new SendThread(socket.getOutputStream(), message)).start();

        readThread.start();
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
            logger.info(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "host: " + host + ", port: " + port + ", username: " + username;
    }

}
