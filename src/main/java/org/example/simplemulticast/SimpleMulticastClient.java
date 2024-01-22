package org.example.simplemulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SimpleMulticastClient {

    public void start() {
        System.out.println("[Multicast Time Client]");

        // 메시지를 수신하기 위해 클라이언트는 서버와 동일한 그룹의 주소 및 포트 번호를 사용한다.
        try(MulticastSocket socket = new MulticastSocket(8888)) {
            InetAddress group = InetAddress.getByName("224.0.0.0");
            socket.joinGroup(group);
            System.out.println("[Multicast Time Client] Multicast Group Joined");

            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // 메시지를 5회 수신해 출력하고 종료한다.
            for (int i = 0 ; i < 5; i++) {
                socket.receive(packet);

                String received = new String(packet.getData());
                System.out.printf("[Multicast Time Client] Received %s\n", received.trim());
            }

            socket.leaveGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[Multicast Time Client] Terminated");
    }
}
