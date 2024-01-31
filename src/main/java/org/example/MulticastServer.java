package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MulticastServer {
    private static final String MULTICAST_GROUP_ADDRESS = "239.0.0.1";
    private static final int PORT = 4446;

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP_ADDRESS);
            MulticastSocket multicastSocket = new MulticastSocket(PORT);

            multicastSocket.joinGroup(group);

            List<String> wordList = Arrays.asList("Hola", "Món", "Java", "Multicast", "Exemple");
            Random random = new Random();

            while (true) {
                String randomWord = wordList.get(random.nextInt(wordList.size()));
                byte[] buffer = randomWord.getBytes();

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                multicastSocket.send(packet);

                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}