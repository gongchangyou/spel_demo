package com.mouse.spel_demo;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/9/15 20:16
 */
@Data
@Builder
public class User {
    private int age;
    private String name;

    private Map<String,String> map;

    private List<Integer> list;

    private Map<String, User> map2;



}
