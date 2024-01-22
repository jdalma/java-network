package org.example.threadedecho;

import org.example.simpleecho.SimpleEchoClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadedEchoServer implements Runnable {

    private final Socket clientSocket;

    public ThreadedEchoServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable clientTask1 = () -> new SimpleEchoClient().start();
        Runnable clientTask2 = () -> new SimpleEchoClient().start();

        executorService.submit(clientTask1);
        executorService.submit(clientTask2);

        System.out.println("Threaded Echo Server");
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            while (true) {
                System.out.println("Waiting for connection.....");

                ThreadedEchoServer tes = new ThreadedEchoServer(serverSocket.accept());
                new Thread(tes).start();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Threaded Echo Server Terminating");

        executorService.shutdown();
    }

    @Override
    public void run() {
        System.out.println("Connected to client using [" + Thread.currentThread() + "]");
        try (
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                System.out.println("Client request [" + Thread.currentThread() + "]: " + inputLine);
                out.println(inputLine);
            }
            System.out.println("Client [" + Thread.currentThread() + " connection terminated");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
