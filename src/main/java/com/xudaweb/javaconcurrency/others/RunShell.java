package com.xudaweb.javaconcurrency.others;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunShell {
    public static void main(String[] args) {
        ExecutorService ee = Executors.newFixedThreadPool(20);
        while (true) {
            ee.execute(() -> {
                try {
                    String shpath = "/Users/xuda/Downloads/test.sh";
                    Process ps = Runtime.getRuntime().exec(shpath);
                    BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    String result = sb.toString();
                    System.out.println(result);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}