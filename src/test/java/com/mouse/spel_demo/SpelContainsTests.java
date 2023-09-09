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


}
