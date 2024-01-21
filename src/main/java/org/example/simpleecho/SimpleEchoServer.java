package org.example.simpleecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SimpleEchoServer {

    public void start() {
        System.out.println("[Simple Echo Server]");

        try(ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("[Simple Echo Server] Waiting for connection ...");

            // 클라이언트와 연결이 될 떄 까지 블로킹된다.
            Socket clientSocket = serverSocket.accept();
            System.out.println("[Simple Echo Server] Connected to client !!!");

            // 클라이언트의 메시지를 읽기 위한 BufferedReader를 생성 (InputStream)
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()))
            ) {
                // 클라이언트에 응답하기 위한 PrintWriter를 생성 (OutputStream)
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

//                traditionalImpl(br, out);
                functionalImpl(br, out);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("[Simple Echo Server] Terminating");
    }

    private void traditionalImpl(BufferedReader br, PrintWriter out) throws IOException {
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            System.out.println("[Simple Echo Server] Client request: " + inputLine);
            out.println(inputLine);
        }
    }

    private void functionalImpl(BufferedReader br, PrintWriter out) throws IOException {
        Supplier<String> socketInput = () -> {
            try {
                return br.readLine();
            } catch (IOException ex) {
                return null;
            }
        };

        Stream<String> stream = Stream.generate(socketInput);
        stream.peek(s -> {
            System.out.println("[Simple Echo Server] Client request: " + s);
            out.println(s);
        }).allMatch(Objects::nonNull);
    }
}
