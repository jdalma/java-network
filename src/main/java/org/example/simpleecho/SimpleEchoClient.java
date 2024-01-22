package org.example.simpleecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SimpleEchoClient {

    public void start() {
        System.out.println("[Simple Echo Client]");

        try {
            System.out.println("[Simple Echo Client] Waiting for connection ...");
            InetAddress localHost = InetAddress.getLocalHost();

            try(
                // 서버와 연결 생성
                Socket clientSocket = new Socket(localHost, 6000);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                System.out.println("[Simple Echo Client] Connected to server !!!");
                Scanner scanner = new Scanner(System.in);

//                traditionalImpl(scanner, out, br);
                functionalImpl(scanner, out, br);
            }
            System.out.println("[Simple Echo Client] Terminating");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void traditionalImpl(Scanner sc, PrintWriter out, BufferedReader br) throws IOException {
        while (true) {
            System.out.print("[Simple Echo Client] Enter text: ");
            String inputLine = sc.nextLine();
            if ("quit".equalsIgnoreCase(inputLine)) {
                break;
            }
            out.println(inputLine);

            String response = br.readLine();
            System.out.println("[Simple Echo Client] Server response: " + response);
        }
    }

    private void functionalImpl(Scanner scanner, PrintWriter out, BufferedReader br) {
        Supplier<String> scannerInput = scanner::nextLine;
        System.out.print("[Simple Echo Client] Enter text: ");

        Stream.generate(scannerInput)
                .map(s -> {
                    out.println(s);
                    System.out.println("[Simple Echo Client] Server response: " + s);
                    System.out.print("[Simple Echo Client] Enter text: ");
                    return s;
                })
                .allMatch(s -> !"quit".equalsIgnoreCase(s));
    }
}
