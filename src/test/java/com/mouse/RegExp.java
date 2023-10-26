package com.mouse;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {
    public static void main(String[] args) {
        String text = "sum(xxx)";
        Pattern pattern = Pattern.compile("\\bsum\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            System.out.println("Match found: " + matcher.group());
        }
    }

    @Test
    public void replace() {
        String text = "sum(xxx)";
        String text1 = text.replaceAll("(?i)SUM", "");
        System.out.println(text1);
    }

    @Test
    public void map2ListMulti() {
        for (int i=0;i<20;i++) {
            map2List();
        }
    }
    @Test
    public void map2List() {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i=0;i<100000;i++) {
            map.put(i, i);
        }
        StopWatch sw = new StopWatch();
        sw.start();
        List list = new ArrayList(map.values());
        sw.stop();
        System.out.println(sw.prettyPrint());

    }

    @Test
    public void doubleTest() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 使用 SimpleDateFormat 格式化 Date 对象为字符串
        String formattedDate = dateFormat.format(date);

        // 输出格式化后的日期时间字符串
        System.out.println("Formatted Date: " + formattedDate);
    }

}
