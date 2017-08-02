package com.pelucky.chat.chatClient;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ClientChatView {

    private JLabel jLabelWelcome;
    private JTextArea jTextAreaReceive;
    private JTextField jtextFieldSendMessage;
    private JButton jButtonSend;
    private JLabel jLabelUserList;
    private JTextArea jTextAreaUserList;
    private JScrollPane jScrollReceive;
    private JScrollPane jScrollUserList;

    public ClientChatView(TcpSocketClient tcpSocketClient) {
        jLabelWelcome = new JLabel("Welcome to Chat Client");
        jLabelWelcome.setBounds(10, 10, 350, 20);
        jLabelWelcome.setFont(new Font("Tahoma", Font.BOLD, 20));

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
        jButtonSend.addActionListener(new ClientChatController(this, tcpSocketClient));
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
        JFrame frame = new JFrame("ChatClient");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

}
