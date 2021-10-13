package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class TCPKlientEcho {
    public static void main(String args[]) throws IOException {
        //System.setProperty("line.separator", "\r\n"); // Mac users only

        String server_host_address = "172.21.48.140"; // IP: 127.0.0.1   loop back interface
        int port_on_server = 20008;

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        BufferedReader kbd_reader = new BufferedReader(new InputStreamReader(System.in));
        String kbd_line;
        System.out.println("Enter any text from keyboard, line by line and wait for response from server:");
        while ((kbd_line = kbd_reader.readLine()) != null) {
            // Open TCP connection
            try {
                socket = new Socket(server_host_address, port_on_server);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (UnknownHostException e) {
                System.out.println("Unknown host");
                System.exit(-1);
            } catch  (IOException e) {
                System.out.println("No I/O");
                System.exit(-1);
            }

            // Communicate
            try {
                out.println(21415);
                out.println("172.23.129.44:1234");
                out.println(kbd_line);

                String line;
                line = in.readLine();
                if (line != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Error during communication");
                System.exit(-1);
            }

            // Close TCP connection
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Cannot close the socket");
                System.exit(-1);
            }
        }
    }
}