package org.example;

import org.example.simpleecho.SimpleEchoClient;
import org.example.simpleecho.SimpleEchoServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2); // 2개의 스레드로 구성된 스레드 풀 생성

        Runnable serverTask = () -> new SimpleEchoServer().start();
        Runnable clientTask = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            new SimpleEchoClient().start();
        };

        executorService.submit(serverTask);
        executorService.submit(clientTask);
    }

}
