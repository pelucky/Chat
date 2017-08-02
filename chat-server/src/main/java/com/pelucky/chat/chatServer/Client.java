package com.pelucky.chat.chatServer;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private String username;
    private String host;
    private int port;
    private Socket socket;
    private ReceiveThread receiveThread;
    private Thread readThread;

    public Client(Socket socket) {
        this.socket = socket;
        host = socket.getInetAddress().getHostAddress();
        port = socket.getPort();
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

    public void startThread() throws IOException {
        String message = "#3." + TcpSocketServer.getInstance().getUserList();
        message = message.replace("\r\n", "#@");
        new Thread(new SendThread(socket.getOutputStream(), message)).start();

        receiveThread = new ReceiveThread(this);
        readThread = new Thread(receiveThread);
        readThread.start();
    }

    public void stopThread() {
        readThread.interrupt();
    }

    @Override
    public String toString() {
        return "host: " + host + ", port: " + port + ", username: " + username;
    }

}
