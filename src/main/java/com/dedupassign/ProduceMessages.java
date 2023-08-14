package com.dedupassign;

public class ProduceMessages {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Inside the new Main.");
        Producer producer = new Producer(args[0], args[1], args[2], args[3], Long.parseLong(args[4]),
                Long.parseLong(args[5]), "836677558", 1567082869612L);
        producer.start();
        producer.join();

    }
}
