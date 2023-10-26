package com.mouse.spel_demo;

import org.junit.jupiter.api.Test;

public class StringTest {
    @Test
    public void compare() {
        int b ="QE12_pilotUser123".compareTo("qw42_JETDEV");
        int b1 ="qw42_JETDEV".compareTo("qw42_pilotUser123");
        int b2 ="qw42_pilotUser123".compareTo("QE12_JETDEV");

        System.out.println(b);

    }
}
