package org.example.echoServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        while(true) {
            byte[] buffer = new byte[100];
            int countBytes = inputStream.read(buffer);
            String getMessage = new String(buffer, 0, countBytes);
            String outputMessage = "echo: " + getMessage;
            outputStream.write(outputMessage.getBytes());
        }
    }
}
