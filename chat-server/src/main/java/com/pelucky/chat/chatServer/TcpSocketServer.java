package com.pelucky.chat.chatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpSocketServer implements Runnable {

    private Logger logger = LoggerFactory.getLogger(TcpSocketServer.class);
    private Collection<Client> clientArray = new ArrayList<Client>();

    public Collection<Client> getClientArray() {
        return clientArray;
    }

    private String userList = "";
    private ServerSocket serverSocket;
    private Thread serverThread;
    private ServerController serverController;

    public static class singletonHolder {
        private static final TcpSocketServer INSTANCE = new TcpSocketServer();
    }

    private TcpSocketServer() {
        serverThread = new Thread(this);
    }

    public static TcpSocketServer getInstance() {
        return singletonHolder.INSTANCE;
    }

    public boolean startServer(ServerController serverController, String portNumber) {
        try {
            this.serverController = serverController;
            init(Integer.valueOf(portNumber));
            serverThread.start();
            logger.info("The Server is running in port: {}", portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                stop();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }
        return true;
    }

    private void init(int portNumber) throws IOException {
        serverSocket = new ServerSocket(portNumber);
    }

    @Override
    public void run() {
        for (;;) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                logger.info("Accept client: {}, port: {}", socket.getInetAddress().toString(), socket.getPort());
                Client client = new Client(socket);
                client.startThread();
                logger.info("StartThread..");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }

    public void updateUserList(Client client) {
        clientArray.add(client);
        String username = client.getUsername();
        logger.info("Username is: {}", username);
        userList = serverController.getServerView().getjTextAreaUserList().getText();
        userList += username + "\r\n";
        serverController.getServerView().getjTextAreaUserList().setText(userList);
    }

    public void removeUserList(Client client) {
        clientArray.remove(client);
        String username = client.getUsername();
        logger.info("Remove username is: {}", username);
        userList = userList.replace(username + "\r\n", "");
        serverController.getServerView().getjTextAreaUserList().setText(userList);
    }

    public String getUserList() {
        return userList;
    }
}
