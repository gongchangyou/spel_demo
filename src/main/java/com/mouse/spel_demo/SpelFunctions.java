package com.mouse.spel_demo;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2023/1/25 20:29
 */
public class SpelFunctions {
    public static boolean isEmpty(final String str) {
        return str == null || "".equals(str);
    }

    public static boolean isChild(final User user) {
        return user.getAge() < 18;
    }
}
