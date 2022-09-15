package com.mouse.spel_demo;

import lombok.Builder;
import lombok.Data;

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

}
