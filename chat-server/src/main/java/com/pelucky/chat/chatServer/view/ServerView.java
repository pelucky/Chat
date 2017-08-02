package com.pelucky.chat.chatServer.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.pelucky.chat.chatServer.controller.ServerController;

public class ServerView extends JFrame {
    private static final long serialVersionUID = 1L;
    private final String PORTNUMBER = "6666";

    private JLabel jLabelWelcome;
    private JSeparator jSeparatorTop;
    private JLabel jLabelPort;
    private JTextField jTextFieldPort;
    private JLabel jLabelStatus;
    private JLabel jLabelStatusInfo;
    private JButton jButtonStart;
    private JSeparator jSeparatorMiddle;
    private JLabel jLabelUserList;
    private JTextArea jTextAreaUserList;
    private JScrollPane jScrollUserList;

    public ServerView() {
        jLabelWelcome = new JLabel("Welcome to Chat Server");
        jLabelWelcome.setBounds(10, 10, 350, 20);
        jLabelWelcome.setFont(new Font("Tahoma", Font.BOLD, 20));

        jSeparatorTop = new JSeparator();
        jSeparatorTop.setBounds(0, 40, 350, 10);
        jSeparatorTop.setBackground(new Color(255, 255, 255));

        jLabelPort = new JLabel("Port:");
        jLabelPort.setBounds(10, 60, 50, 20);
        jLabelPort.setFont(new Font("Tahoma", Font.PLAIN, 14));

        jTextFieldPort = new JTextField(PORTNUMBER);
        jTextFieldPort.setBounds(60, 60, 100, 20);
        jTextFieldPort.setToolTipText("The port must between 1024 and 65535!");

        jLabelStatus = new JLabel("Status:");
        jLabelStatus.setBounds(10, 90, 50, 20);
        jLabelStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));

        jLabelStatusInfo = new JLabel("Stoped");
        jLabelStatusInfo.setBounds(60, 90, 200, 20);
        jLabelStatusInfo.setFont(new Font("Tahoma", Font.BOLD, 14));

        jButtonStart = new JButton("Start");
        jButtonStart.setBounds(100, 130, 80, 30);
        jButtonStart.addActionListener(new ServerController(this));
        jButtonStart.setActionCommand("start");

        jSeparatorMiddle = new JSeparator();
        jSeparatorMiddle.setBounds(0, 190, 350, 10);
        jSeparatorMiddle.setBackground(new Color(255, 255, 255));

        jLabelUserList = new JLabel("UserList:");
        jLabelUserList.setBounds(10, 200, 250, 20);
        jLabelUserList.setFont(new Font("Tahoma", Font.BOLD, 14));

        jTextAreaUserList = new JTextArea();
        jTextAreaUserList.setEditable(false);
        jScrollUserList = new JScrollPane(jTextAreaUserList);
        jScrollUserList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollUserList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollUserList.setBounds(10, 230, 260, 220);
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("ChatServer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel();
        frame.setContentPane(jPanel);
        jPanel.setLayout(null);

        jPanel.add(jLabelWelcome);
        jPanel.add(jSeparatorTop);
        jPanel.add(jLabelPort);
        jPanel.add(jTextFieldPort);
        jPanel.add(jLabelStatus);
        jPanel.add(jLabelStatusInfo);
        jPanel.add(jButtonStart);
        jPanel.add(jSeparatorMiddle);
        jPanel.add(jLabelUserList);
        jPanel.add(jScrollUserList);

        frame.pack();
        frame.setSize(300, 500);
        frame.setLocationRelativeTo(null);
        frame.getRootPane().setDefaultButton(jButtonStart);
        jButtonStart.requestFocus();
        frame.setVisible(true);
    }

    public void run() {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public JTextField getjTextFieldPort() {
        return jTextFieldPort;
    }

    public JLabel getjLabelStatusInfo() {
        return jLabelStatusInfo;
    }

    public JButton getjButtonStart() {
        return jButtonStart;
    }

    public JTextArea getjTextAreaUserList() {
        return jTextAreaUserList;
    }

}
