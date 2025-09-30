package org.example.echoServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 3000);
        Scanner scanner = new Scanner(System.in);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        while (true) {
            System.out.print("Enter massage: ");
            outputStream.write(scanner.nextLine().getBytes());

            byte[] buffer = new byte[100];
            int byteCount = inputStream.read(buffer);
            String inputMessage = new String(buffer, 0, byteCount);
            System.out.println(inputMessage);
            System.out.println();
        }
    }
}
