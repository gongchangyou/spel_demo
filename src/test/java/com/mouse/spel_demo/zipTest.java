package com.mouse.spel_demo;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
public class zipTest {

    @Test
    public void unZip() {

        String zipFilePath = "/Users/gongchangyou/Downloads/a.zip"; // 替换为您的 ZIP 文件路径

        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals("a/b/lib/test.txt")) {
                    byte[] b = zipInputStream.readAllBytes();
                    System.out.println(new String(b));
                }
                System.out.println(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
