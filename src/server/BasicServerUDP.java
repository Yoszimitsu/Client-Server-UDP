package server;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class BasicServerUDP {

    public static void main(String[] args) throws IOException {

        final int serverPort = 12345;
        int clientPort;
        InetAddress clientAddress;
        DatagramPacket dp_key;
        DatagramPacket dp_text;
        String key;
        String text;
        String respond;
        String init = "Welcome to the text transformation service";
        byte[] bufferForKey = new byte[20];
        byte[] bufferForText = new byte[20];

        // Creating Socket using an specific port
        DatagramSocket socket = new DatagramSocket(serverPort);

        while (true) {
            System.out.println("Waiting for a new client...");

            try {
                dp_key = receiveDatagramPacket(socket, bufferForKey);

                clientAddress = dp_key.getAddress();
                clientPort = dp_key.getPort();

                sendDatagramPacket(socket, clientAddress, clientPort, init);

                dp_text = receiveDatagramPacket(socket, bufferForText);

                key = datagramPacketToString(dp_key);
                text = datagramPacketToString(dp_text);

                System.out.println("Client message: " + "\nKey: \"" + key + "\"\nText: \"" + text + "\"");

                respond = messageAnalyzer(key, text);
                sendDatagramPacket(socket, clientAddress, clientPort, respond);
                System.out.println("\nServer response: \"" + respond + "\"");
            } catch (Exception e) {
                System.err.println("Error " + e.getClass() + " " + e.getMessage());
            }
        }
    }

    static public String textJoiner(ArrayList<String> arrayWithStrings) {
        StringJoiner sj = new StringJoiner(" ");
        for (String s : arrayWithStrings) {
            sj.add(s);
        }
        return sj.toString();
    }

    static public String datagramPacketToString(DatagramPacket packet) {

        return new String(Arrays.copyOfRange(packet.getData(), packet.getOffset(), packet.getLength() + packet.getOffset()));
    }

    static public DatagramPacket receiveDatagramPacket(DatagramSocket socket, byte[] buffer) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(datagramPacket);
        return datagramPacket;
    }

    static public void sendDatagramPacket(DatagramSocket socket, InetAddress inetAddress, int port, String respond) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(respond.getBytes(), respond.getBytes().length, inetAddress, port);
        socket.send(datagramPacket);
    }

    static public String messageAnalyzer(String key, String text) {
        String respond;
        ArrayList<String> message = new ArrayList<>();
        Scanner sc = new Scanner(text).useDelimiter("\\s+");

        while (sc.hasNext()) {
            message.add(sc.next());
        }
        if (key.equalsIgnoreCase("L"))
            respond = textJoiner(message);
        else if (key.equalsIgnoreCase("B")) {
            Collections.shuffle(message);
            respond = textJoiner(message);
        } else
            respond = "Not supported option";
        return respond;
    }
}
    

