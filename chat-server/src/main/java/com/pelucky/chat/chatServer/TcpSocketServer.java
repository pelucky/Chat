package com.pelucky.chat.chatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pelucky.chat.chatServer.controller.ServerController;

public class TcpSocketServer implements Runnable {

    private Logger logger = LoggerFactory.getLogger(TcpSocketServer.class);
    private Collection<Client> clientArray = new ArrayList<Client>();
    private String userList = "";
    private ServerSocket serverSocket;
    private Thread serverThread;
    private ServerController serverController;

    /*
     * Singleton server
     */
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
            logger.info(e.getMessage());
            try {
                stop();
            } catch (IOException e1) {
                logger.info(e1.getMessage());
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
            } catch (IOException e) {
                logger.info(e.getMessage());;
            }
        }
    }

    public void stop() throws IOException {
        if (serverThread != null) {
            serverThread.interrupt();
        }
        if (serverSocket != null) {
            logger.info("Close server...");
            serverSocket.close();
        }
    }

    /*
     * Add client into clientArray
     * Update UserList in serverView
     */
    public void updateUserList(Client client) {
        clientArray.add(client);
        String username = client.getUsername();
        userList = serverController.getUserList();
        userList += username + "\r\n";
        serverController.updateUserList(userList);
    }

    /*
     * Remove client in clientArray
     * Update userList in serverView
     */
    public void removeUserList(Client client) {
        clientArray.remove(client);
        String username = client.getUsername();
        userList = userList.replace(username + "\r\n", "");
        serverController.updateUserList(userList);
    }

    public String getUserList() {
        return userList;
    }

    public Collection<Client> getClientArray() {
        return clientArray;
    }
}
