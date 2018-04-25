package com.xudaweb.javaconcurrency.others;

import com.xudaweb.javaconcurrency.others.SynchronizedExample;
import org.junit.Test;

import java.util.concurrent.Executors;

public class SynchronizedExampleNormalTest {
    @Test
    public void test0() {
        Executors.newCachedThreadPool().execute(()->new SynchronizedExample().fun());
    }

    @Test
    public void test1() {
        Executors.newCachedThreadPool().execute(()->new SynchronizedExample().fun());
    }
}
