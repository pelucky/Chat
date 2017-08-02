package com.pelucky.chat.chatClient;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ClientLoginView extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String HOSTNAME = "127.0.0.1";
    private final String PORTNUMBER = "6666";

    private JFrame frame;
    private JLabel jLabelWelcome;
    private JSeparator jSeparatorTop;
    private JLabel jLabelHost;
    private JTextField jTextFieldHost;
    private JLabel jLabelPort;
    private JTextField jTextFieldPort;
    private JLabel jLabelUsername;
    private JTextField jTextFieldUsername;
    private JLabel jLabelInfo;
    private JButton jButtonLogin;
    private ClientLoginController clientLoginController;

    public ClientLoginView() {
        clientLoginController = new ClientLoginController(this);

        jLabelWelcome = new JLabel("Welcome to Chat Client");
        jLabelWelcome.setBounds(10, 10, 350, 20);
        jLabelWelcome.setFont(new Font("Tahoma", Font.BOLD, 20));

        jSeparatorTop = new JSeparator();
        jSeparatorTop.setBounds(0, 40, 350, 10);
        jSeparatorTop.setBackground(new Color(255, 255, 255));

        jLabelHost = new JLabel("Host:");
        jLabelHost.setBounds(10, 60, 80, 20);
        jLabelHost.setFont(new Font("Tahoma", Font.PLAIN, 14));

        jTextFieldHost = new JTextField(HOSTNAME);
        jTextFieldHost.setBounds(90, 60, 100, 20);
        jTextFieldHost.setToolTipText("The host must the localhost or IP");

        jLabelPort = new JLabel("Port:");
        jLabelPort.setBounds(10, 90, 80, 20);
        jLabelPort.setFont(new Font("Tahoma", Font.PLAIN, 14));

        jTextFieldPort = new JTextField(PORTNUMBER);
        jTextFieldPort.setBounds(90, 90, 100, 20);
        jTextFieldPort.setToolTipText("The port must between 1024 and 65535!");

        jLabelUsername = new JLabel("Username:");
        jLabelUsername.setBounds(10, 120, 80, 20);
        jLabelUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));

        jTextFieldUsername = new JTextField();
        jTextFieldUsername.setBounds(90, 120, 100, 20);
        jTextFieldPort.setToolTipText("You must have a unique name!");

        jLabelInfo = new JLabel();
        jLabelInfo.setBounds(10, 150, 350, 20);
        jLabelInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));

        jButtonLogin = new JButton("Login");
        jButtonLogin.setBounds(100, 190, 80, 30);
        jButtonLogin.addActionListener(clientLoginController);
        jButtonLogin.setActionCommand("login");
    }

    private void createAndShowGUI() {
        frame = new JFrame("ChatClient");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel();
        frame.setContentPane(jPanel);
        jPanel.setLayout(null);

        jPanel.add(jLabelWelcome);
        jPanel.add(jSeparatorTop);
        jPanel.add(jLabelHost);
        jPanel.add(jTextFieldHost);
        jPanel.add(jLabelPort);
        jPanel.add(jTextFieldPort);
        jPanel.add(jLabelUsername);
        jPanel.add(jTextFieldUsername);
        jPanel.add(jLabelInfo);
        jPanel.add(jButtonLogin);

        frame.pack();
        frame.setSize(300, 280);
        frame.setLocationRelativeTo(null);
        frame.getRootPane().setDefaultButton(jButtonLogin);
        jButtonLogin.requestFocus();
        frame.setVisible(true);
    }

    public void run() {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public JTextField getjTextFieldHost() {
        return jTextFieldHost;
    }

    public JTextField getjTextFieldUsername() {
        return jTextFieldUsername;
    }

    public JTextField getjTextFieldPort() {
        return jTextFieldPort;
    }

    public JLabel getjLabelInfo() {
        return jLabelInfo;
    }

    public JFrame getFrame() {
        return frame;
    }

}
