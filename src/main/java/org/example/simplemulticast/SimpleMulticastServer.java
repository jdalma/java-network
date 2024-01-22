package org.example.simplemulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;

public class SimpleMulticastServer {

    public void start() {
        System.out.println("[Multicast Time Server]");

        try(DatagramSocket serverSocket = new DatagramSocket()) {
            for (int i = 0 ; i < 10 ; i++) {
                String dateText = LocalDateTime.now().toString();
                byte[] buffer = dateText.getBytes();

                // 멀티캐스트 그룹
                InetAddress group = InetAddress.getByName("224.0.0.0");

                // 멀티캐스트 그룹과 buffer 배열로 패킷을 생성
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, 8888);

                serverSocket.send(packet);
                System.out.println("[Multicast Time Server] Time sent: " + dateText);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("[Multicast Time Server] Terminated");
    }
}
