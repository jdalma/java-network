package org.example.simpleecho;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

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

        executorService.shutdown();
    }

}
