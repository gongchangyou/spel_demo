package com.mouse.spel_demo;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
@SpringBootTest
public class FileTest {

    @Test
    void test() throws InterruptedException {
        try {
            File tmpFile = File.createTempFile("test", ".so");
            System.out.println(tmpFile.toPath());
            System.out.println(tmpFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取so文件
     */
    @Test
    public void loadSO() {
                try {
                    String jarFilePath = "/path/to/your/jarfile.jar";  // 替换为您的 JAR 文件路径
                    String soFileName = "library.so";  // 替换为您的 .so 文件名称

                    // 打开 JAR 文件
                    JarFile jarFile = new JarFile(jarFilePath);
                    JarEntry jarEntry = jarFile.getJarEntry(soFileName);

                    if (jarEntry != null) {
                        // 获取 .so 文件的输入流
                        InputStream inputStream = jarFile.getInputStream(jarEntry);

                        // 读取 .so 文件的内容
                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                        int nRead;
                        byte[] data = new byte[1024];
                        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                            buffer.write(data, 0, nRead);
                        }
                        buffer.flush();

                        // 输出 .so 文件内容
                        System.out.println(new String(buffer.toByteArray(), "UTF-8"));

                        // 关闭输入流
                        inputStream.close();
                    } else {
                        System.err.println("SO file not found in the JAR.");
                    }

                    // 关闭 JAR 文件
                    jarFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
}
