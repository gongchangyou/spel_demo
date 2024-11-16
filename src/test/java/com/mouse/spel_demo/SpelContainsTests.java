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

    public static boolean like(String s, String p) {
        final boolean isMatched = FilenameUtils.wildcardMatch(s, p);
        return isMatched;
    }

    @Test
    void testContains100 () throws NoSuchMethodException {
        StopWatch sw = new StopWatch();
        for (int i=0;i<100;i++) {
            sw.start();
            testContains();
            sw.stop();
        }
        log.info(sw.prettyPrint());

    }

    /**
     * 执行时间0.2ms
     * @throws NoSuchMethodException
     */
    @Test
    void testContains() throws NoSuchMethodException {
        // Create a SpEL expression parser
        ExpressionParser parser = new SpelExpressionParser();

        // Enumerations in SpEL are accessed using their fully qualified names
        val enumExpression = parser.parseExpression("{'BOND', 'IRS'}.contains(#instrumentClass) and #like(#appId, 'peak*') and #side=='BUY' and #dv01==1");
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("instrumentClass", "BOND");
        context.setVariable("appId", "peak_algo");
        context.setVariable("side", "BUY");
        context.setVariable("dv01", 1);

        context.setVariable("like", SpelContainsTests.class.getDeclaredMethod("like", String.class, String.class));

        // Evaluate the SpEL expression
        boolean containsValue = enumExpression.getValue(context, Boolean.class);

        // Check if the enum contains the specified value
        if (containsValue) {
            System.out.println("contains.");
        } else {
            System.out.println("does not contain.");
        }
    }

    @Test
    void testEqual() {
        // Create a SpEL expression parser
        ExpressionParser parser = new SpelExpressionParser();
        val enumExpression11 = parser.parseExpression("false ? 3 : 4");
        int b = 2 ^ 3;
        int result = parser.parseExpression("5 ^ 3").getValue(Integer.class);
        //下面这个会抛异常
        //        val enumExpression = parser.parseExpression("T(com.mouse.MyEnum).valueOf('BUYxx') != null");

        // Evaluate the SpEL expression
        int a = enumExpression11.getValue(Integer.class);
        System.out.println(a);
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

            val enumExpression2 = parser.parseExpression("#a<1000 or #a>2000");
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setVariable("a", 2500);
            //下面这个会抛异常
            //        val enumExpression = parser.parseExpression("T(com.mouse.MyEnum).valueOf('BUYxx') != null");

            // Evaluate the SpEL expression
            boolean containsValue2 = enumExpression2.getValue(context, Boolean.class);
            System.out.println(containsValue2);
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
