package com.pelucky.chat.chatServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class SendThread implements Runnable {

    private String message;
    private BufferedWriter bufferedWriter = null;

    public SendThread(OutputStream outputStream, String message) {
        this.message = message.replace("\r\n", "#@");
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    @Override
    public void run() {
        try {
            bufferedWriter.write(message, 0, message.length());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
