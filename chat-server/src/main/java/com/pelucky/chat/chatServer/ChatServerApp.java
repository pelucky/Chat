package com.pelucky.chat.chatServer;

import com.pelucky.chat.chatServer.view.ServerView;

/**
 * ChatServerApp
 *
 */
public class ChatServerApp {
    public static void main(String[] args) {
        ServerView serverView = new ServerView();
        serverView.run();
    }
}
