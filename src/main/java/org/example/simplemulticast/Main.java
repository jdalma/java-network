package org.example.simplemulticast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable serverTask = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            new SimpleMulticastServer().start();
        };
        Runnable clientTask = () -> new SimpleMulticastClient().start();;

        executorService.submit(serverTask);
        executorService.submit(clientTask);

        executorService.shutdown();
    }

}
