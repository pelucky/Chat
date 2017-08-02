package com.pelucky.chat.chatClient.thread;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendThread implements Runnable {

    private String message;
    private BufferedWriter bufferedWriter = null;
    private Logger logger = LoggerFactory.getLogger(SendThread.class);

    /*
     * Change message's \r\n to #@ for avoiding receive a part of content
     * because receive is using readLine()
     */
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
            logger.info(e.getMessage());
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e1) {
                logger.info(e1.getMessage());
            }
        }
    }
}
