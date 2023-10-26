package com.mouse.spel_demo;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@SpringBootTest
public class RemoveMap {

    @Test
    void test() throws InterruptedException {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i=0;i<1000;i++) {
            map.put(i,i);
        }

        Iterator<Integer> it = map.keySet().iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        //这里会抛异常         java.util.ConcurrentModificationException
        for (Integer i : map.keySet()) {
            map.remove(i);
        }
    }
}
