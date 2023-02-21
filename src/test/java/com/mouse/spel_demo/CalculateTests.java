package com.mouse.spel_demo;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest
class CalculateTests {

    @Test
    void listFilterContext1() throws NoSuchMethodException {
        StandardEvaluationContext context = new StandardEvaluationContext();
        double a = 30000000;
        context.setVariable("qty", a);
        context.setVariable("price", 103);
        context.setVariable("CDCMM", true);


        ExpressionParser parser = new SpelExpressionParser();
        val filterList = parser.parseExpression("((#qty*#price*2.5/1000000 < 1000 ? #qty*#price*2.5/1000000 : 1000) + 150 ) * (#CDCMM ? 0.8 : 1)").getValue(context);
        log.info(filterList.toString());

        context.registerFunction("min", SpelFunctions.class.getDeclaredMethod("min", double.class, double.class));
        context.registerFunction("ceil", SpelFunctions.class.getDeclaredMethod("ceil", double.class));

        val min = parser.parseExpression("#ceil(age)").getValue(context);
        log.info(min.toString());


        val userA = new User(10, "mouse");
        val usera = parser.parseExpression("age").getValue(context, userA);
        log.info(usera.toString());



    }

    @Test
    public void test2() {
        ExpressionParser parser = new SpelExpressionParser();
        String randomPhrase = parser.parseExpression(
                "random number is #{T(java.lang.Math).random()}",
                new TemplateParserContext()).getValue(String.class);
        System.out.println(randomPhrase);
    }


    @Test
    void listFilterContext3() throws NoSuchMethodException {
        val i = "3Y".substring(0, "3Y".length() - 1);
        StandardEvaluationContext context = new StandardEvaluationContext();
        double a = 30000000;
        context.setVariable("qty", a);
        context.setVariable("price", 103);
        context.setVariable("CDCMM", false);
        context.setVariable("age", 10.2);



        ExpressionParser parser = new SpelExpressionParser();
        val filterList = parser.parseExpression("((#qty*#price*2.5/1000000 < 1000 ? #qty*#price*2.5/1000000 : 1000) + 150 ) * (#CDCMM ? 0.8 : 1)").getValue(context);
        log.info(filterList.toString());

        context.registerFunction("min", SpelFunctions.class.getDeclaredMethod("min", double.class, double.class));
//        context.registerFunction("ceil", SpelFunctions.class.getDeclaredMethod("ceil", double.class));

        val min = parser.parseExpression("T(java.lang.Math).ceil(#age)").getValue(context);
        log.info(min.toString());


        val userA = new User(10, "mouse");
        val usera = parser.parseExpression("age").getValue(context, userA);
        log.info(usera.toString());



    }
}
