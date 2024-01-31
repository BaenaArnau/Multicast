package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.Map;

public class MulticastClient {
    private static final String MULTICAST_GROUP_ADDRESS = "239.0.0.1";
    private static final int PORT = 4446;
    private Map<String, Integer> repeticiones = new HashMap<>();

    public static void main(String[] args) {
        MulticastClient client = new MulticastClient();
        client.run();
    }

    public void run() {
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP_ADDRESS);
            MulticastSocket multicastSocket = new MulticastSocket(PORT);
            multicastSocket.joinGroup(group);

            byte[] buffer = new byte[1024];

            System.out.println("Connectat al grup multicast. Escoltant paraules...");

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);

                processData(packet.getData(), packet.getLength(), packet.getAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processData(byte[] data, int length, InetAddress senderAddress) {
        String receivedWord = new String(data, 0, length);
        System.out.println("Paraula rebuda: " + receivedWord);
        System.out.println("Adreça del remitent: " + senderAddress.getHostAddress());
        System.out.println("Port del remitent: " + PORT);

        countWord(receivedWord);
    }

    private void countWord(String word) {
        repeticiones.putIfAbsent(word, 0);
        repeticiones.replace(word, repeticiones.get(word) + 1);

        System.out.println(word + " --> " + repeticiones.get(word));
    }
}