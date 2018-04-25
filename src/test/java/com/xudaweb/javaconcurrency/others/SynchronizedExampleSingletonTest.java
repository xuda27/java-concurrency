package com.xudaweb.javaconcurrency.others;

import com.xudaweb.javaconcurrency.others.SynchronizedExample;
import org.junit.Test;

import java.util.concurrent.Executors;

public class SynchronizedExampleSingletonTest {
    @Test
    public void test0() {
        Executors.newCachedThreadPool().execute(()-> SynchronizedExample.newInstance().fun());
    }

    @Test
    public void test1() {
        Executors.newCachedThreadPool().execute(()->SynchronizedExample.newInstance().fun());
    }
}
