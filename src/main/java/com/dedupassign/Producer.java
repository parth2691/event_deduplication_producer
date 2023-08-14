package com.dedupassign;

import org.apache.commons.text.StrSubstitutor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Producer extends Thread {
    private KafkaProducer<String, String> producer;
    private final String topic;
    private final String filePath;

    private final long messageDelay;
    private final String startViewedAtId;
    private final Long startviewAt;
    private long count;


    public Producer(String KAFKA_SERVER_URL, String KAFKA_SERVER_PORT, String topic, String filePath,
                    long count, long messageDelay, String startViewedAtId, Long startviewAt) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);
        this.topic = topic;
        this.filePath = filePath;
        this.messageDelay = messageDelay;
        this.count = count;
        this.startViewedAtId = startViewedAtId;
        this.startviewAt = startviewAt;
    }

    public void run() {
        long messageNo = 0L;
        String line;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            line = reader.readLine();
            BigInteger key1 = new BigInteger(startViewedAtId);
            Long viewedAt = startviewAt;
            int msgCount = 1;
            HashMap<String, String> values = new HashMap<String, String>();
            while (msgCount <= count) {
                values.put("key1", key1.toString());
                values.put("visitedAt", viewedAt.toString());
                String result = StrSubstitutor.replace(line, values);
                producer.send(new ProducerRecord<>(topic,
                        messageNo + "", result)).get();
                key1 = key1.add(BigInteger.ONE);
                viewedAt = viewedAt + 100L;
                ++msgCount;
                ++messageNo;
                Thread.sleep(messageDelay);
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
    }
}

