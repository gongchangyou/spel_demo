package com.mouse.spel_demo;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StopWatch;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@SpringBootTest
class ReentrantLockTests {
    @Test
    void test() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = new Thread(() -> {
            lock.lock();

            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("thread {}", Thread.currentThread());
            }
            lock.unlock();
        });

        Thread thread2 = new Thread(() -> {
            lock.lock(); //会阻塞  tryLock 不会阻塞
            log.info("thread2 start");
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("thread {}", Thread.currentThread());
            }
            lock.unlock();
        });
        thread1.start();
        thread2.start();

        Thread.sleep(10000L);
    }


    }
