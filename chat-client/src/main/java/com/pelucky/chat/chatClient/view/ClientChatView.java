package com.pelucky.chat.chatClient.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.pelucky.chat.chatClient.TcpSocketClient;
import com.pelucky.chat.chatClient.controller.ClientChatController;

/*
 * Client chat view is almost same as client login view
 */
public class ClientChatView {

    private JFrame frame;
    private JLabel jLabelWelcome;
    private JTextArea jTextAreaReceive;
    private JTextField jtextFieldSendMessage;
    private JButton jButtonSend;
    private JLabel jLabelUserList;
    private JTextArea jTextAreaUserList;
    private JScrollPane jScrollReceive;
    private JScrollPane jScrollUserList;
    private ClientChatController clientChatController;

    public ClientChatView(TcpSocketClient tcpSocketClient) {
        clientChatController = new ClientChatController(this, tcpSocketClient);

        jLabelWelcome = new JLabel("Welcome to Chat Client");
        jLabelWelcome.setBounds(10, 10, 350, 20);
        jLabelWelcome.setFont(new Font("Tahoma", Font.BOLD, 20));

        // set ScrollPane, add TextArea into ScrollPane, and add ScrollPane into jPanel
        jTextAreaReceive = new JTextArea();
        jTextAreaReceive.setEditable(false);
        jScrollReceive = new JScrollPane(jTextAreaReceive);
        jScrollReceive.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollReceive.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollReceive.setBounds(10, 50, 500, 500);

        jtextFieldSendMessage = new JTextField();
        jtextFieldSendMessage.setBounds(10, 580, 500, 30);

        jButtonSend = new JButton("Send");
        jButtonSend.setBounds(530, 580, 80, 30);
        jButtonSend.addActionListener(clientChatController);
        jButtonSend.setActionCommand("send");

        jLabelUserList = new JLabel("UserList");
        jLabelUserList.setBounds(530, 10, 80, 20);
        jLabelUserList.setFont(new Font("Tahoma", Font.PLAIN, 14));

        jTextAreaUserList = new JTextArea();
        jTextAreaUserList.setEditable(false);
        jScrollUserList = new JScrollPane(jTextAreaUserList);
        jScrollUserList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollUserList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollUserList.setBounds(530, 50, 100, 500);
    }

    private void createAndShowGUI() {
        frame = new JFrame("ChatClient");
        // Set default close operation as do nothing, and add window listener for more things
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(clientChatController);
        JPanel jPanel = new JPanel();
        frame.setContentPane(jPanel);
        jPanel.setLayout(null);

        jPanel.add(jLabelWelcome);
        jPanel.add(jScrollReceive);
        jPanel.add(jtextFieldSendMessage);
        jPanel.add(jButtonSend);
        jPanel.add(jLabelUserList);
        jPanel.add(jScrollUserList);

        frame.pack();
        frame.setSize(670, 670);
        frame.setLocationRelativeTo(null);
        frame.getRootPane().setDefaultButton(jButtonSend);
        jButtonSend.requestFocus();
        frame.setVisible(true);
    }

    public void run() {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public JTextArea getjTextAreaReceive() {
        return jTextAreaReceive;
    }

    public JTextField getJtextFieldSendMessage() {
        return jtextFieldSendMessage;
    }

    public JTextArea getjTextAreaUserList() {
        return jTextAreaUserList;
    }

    public JFrame getFrame() {
        return frame;
    }

}
