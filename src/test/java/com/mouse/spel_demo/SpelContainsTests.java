package com.mouse.spel_demo;

import com.mouse.MyEnum;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest
class SpelContainsTests {
    @Test
    void test() {
        // Create a SpEL expression parser
        ExpressionParser parser = new SpelExpressionParser();

        // Enumerations in SpEL are accessed using their fully qualified names
        // Replace 'com.example.MyEnum' with the actual package and enum name
        val enumExpression = parser.parseExpression("T(com.mouse.MyEnum).valueOf('BUY') != null");
        //下面这个会抛异常
        //        val enumExpression = parser.parseExpression("T(com.mouse.MyEnum).valueOf('BUYxx') != null");

        // Evaluate the SpEL expression
        boolean containsValue = enumExpression.getValue(Boolean.class);

        // Check if the enum contains the specified value
            if (containsValue) {
            System.out.println("The enum contains the specified value.");
        } else {
            System.out.println("The enum does not contain the specified value.");
        }
    }

    @Test
    void testEqual() {
        // Create a SpEL expression parser
        ExpressionParser parser = new SpelExpressionParser();

        // Enumerations in SpEL are accessed using their fully qualified names
        // Replace 'com.example.MyEnum' with the actual package and enum name
        StopWatch sw = new StopWatch();
        for (int i=0;i<10;i++) {
            sw.start();

            val enumExpression = parser.parseExpression("'B'=='B'");
            //下面这个会抛异常
            //        val enumExpression = parser.parseExpression("T(com.mouse.MyEnum).valueOf('BUYxx') != null");

            // Evaluate the SpEL expression
            boolean containsValue = enumExpression.getValue(Boolean.class);
            sw.stop();
            System.out.println(containsValue + sw.prettyPrint());
        }

        for (int i=0;i<10;i++) {

            sw.start();
            val enumExpression1 = parser.parseExpression("{'B'}.contains('B')");
            //下面这个会抛异常
            //        val enumExpression = parser.parseExpression("T(com.mouse.MyEnum).valueOf('BUYxx') != null");

            // Evaluate the SpEL expression
            boolean containsValue1 = enumExpression1.getValue(Boolean.class);
            sw.stop();
            System.out.println("r2"+containsValue1 + sw.prettyPrint());
        }
    }


    }
