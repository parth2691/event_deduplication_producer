package com.dedupassign;

import org.apache.commons.text.StrSubstitutor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;

public class KafkaMessagesTest {


    public static void main(String[] args) {
        BufferedReader reader;
        String line;
        try {
            reader = new BufferedReader(new
                    FileReader("/Users/parthkhatwani/Desktop/zlkafkatest/product_view_messages.json"));
            line = reader.readLine();
            int count = 20;
            BigInteger productId = new BigInteger("836677558");
            Long visitedAt = 1567082869612l;
            while (count>0) {
                HashMap<String,String> values = new HashMap<>();
                values.put("productViewId",productId.add(BigInteger.ONE).toString());
                values.put("visitedAt",visitedAt.toString());
                String result = StrSubstitutor.replace(line,values);
                System.out.println(result);
                productId = productId.add(BigInteger.ONE);
                visitedAt = visitedAt + 100l;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}