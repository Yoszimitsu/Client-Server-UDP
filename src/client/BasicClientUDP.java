package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

public class BasicClientUDP {

    public static void main(String[] args) throws IOException {

        String key;
        String text;
        String respond;
        byte[] bufferForInit = new byte[80];
        byte[] bufferForRespond = new byte[20];
        final int port = 12345;
        InetAddress serverAddress = InetAddress.getByName("127.0.0.1");

        // Creating Socket using an temporal port
        DatagramSocket socket = new DatagramSocket();

        System.out.println("Connected to: " + serverAddress.getHostAddress());

        Scanner sc = new Scanner(System.in);

        while (true) {
            key = sc.nextLine();
            try {
                if (!key.equals("F")) {

                    sendDataToServerFromKeyboard(socket, serverAddress, port, key);

                    text = sc.nextLine();
                    sendDataToServerFromKeyboard(socket, serverAddress, port, text);

                    System.out.println(receiveRespond(socket, bufferForInit));
                    System.out.println("Client request:\nKey: \"" + key + "\"\nText: \"" + text + "\"\n");

                    System.out.println("Waiting for a response...");
                    respond = receiveRespond(socket, bufferForRespond);
                    System.out.println("Server response: " + respond);
                } else
                    break;
            } catch (Exception e) {
                System.err.println("Error " + e.getClass() + " " + e.getMessage());
            }
        }
        // Closing the socket
        socket.close();
        System.out.println("Connection close.");
        System.exit(0);

    }

    static public void sendDataToServerFromKeyboard(DatagramSocket socket, InetAddress inetAddress, int port, String message) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.getBytes().length, inetAddress, port);
        socket.send(datagramPacket);
    }

    static public String receiveRespond(DatagramSocket socket, byte[] buffer) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(datagramPacket);
        return new String(Arrays.copyOfRange(datagramPacket.getData(), datagramPacket.getOffset(),
                datagramPacket.getLength() + datagramPacket.getOffset()));
    }
}
