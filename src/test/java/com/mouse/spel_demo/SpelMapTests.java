package com.mouse.spel_demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
class SpelMapTests {

    static List<User> list;
    static {
        list = new ArrayList<User>();
        for (int i=0;i<10000;i++) {
            list.add(User.builder()
                    .age(i)
                    .name("mouse" + i)
                            .map(new HashMap<>() {{
                                put("a", "b");
                                put("b", "z");
                            }})
                    .build());
        }
    }
    @Test
    public void getOne() throws NoSuchMethodException {

        // 创建 SpEL 表达式解析器
        SpelExpressionParser parser = new SpelExpressionParser();

        // 创建 SpEL 表达式，引用 Java 代码中的方法来计算总和
        Expression expression = parser.parseExpression("#userList[0].map.get(\"a\")");

        // 创建评估上下文并设置方法
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("calculateSum", SpelMapTests.class.getDeclaredMethod("calculateSum", List.class));

        // 设置变量
        context.setVariable("userList", list);

        // 通过评估上下文计算表达式的值
        String result = expression.getValue(context, String.class);

        System.out.println("Sum: " + result);
    }

    @Test
    public void getAllMap() throws NoSuchMethodException {

        // 创建 SpEL 表达式解析器
        SpelExpressionParser parser = new SpelExpressionParser();

        // 创建 SpEL 表达式，引用 Java 代码中的方法来计算总和
        Expression expression = parser.parseExpression("#userList.![map]");

        // 创建评估上下文并设置方法
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("calculateSum", SpelMapTests.class.getDeclaredMethod("calculateSum", List.class));

        // 设置变量
        context.setVariable("userList", list);

        // 通过评估上下文计算表达式的值
        Map result = expression.getValue(context, Map.class);

        System.out.println("Sum: " + result);
    }

    @Test
    public void getAllMapValueForOneKey() throws NoSuchMethodException {

        // 创建 SpEL 表达式解析器
        SpelExpressionParser parser = new SpelExpressionParser();

        // 创建 SpEL 表达式，引用 Java 代码中的方法来计算总和
        Expression expression = parser.parseExpression("#userList.![map].![#this[\"a\"]]");

        // 创建评估上下文并设置方法
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("calculateSum", SpelMapTests.class.getDeclaredMethod("calculateSum", List.class));

        // 设置变量
        context.setVariable("userList", list);

        // 通过评估上下文计算表达式的值
        List result = expression.getValue(context, List.class);

        System.out.println("Sum: " + result);
    }

    @Test
    public void getAllMapValueForSeveralKey() throws NoSuchMethodException {
        // 创建 SpEL 表达式解析器
        SpelExpressionParser parser = new SpelExpressionParser();

        // 创建 SpEL 表达式，引用 Java 代码中的方法来计算总和
        Expression expression = parser.parseExpression("#userList.![map].![{#this[\"a\"],#this[\"b\"]}]");

        // 创建评估上下文并设置方法
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("calculateSum", SpelMapTests.class.getDeclaredMethod("calculateSum", List.class));

        // 设置变量
        context.setVariable("userList", list);

        // 通过评估上下文计算表达式的值
        List result = expression.getValue(context, List.class);

        System.out.println("Sum: " + result);
    }


    public static Integer calculateSum(List<User> numbers) {
        return numbers.stream().map(User::getAge).reduce(0, Integer::sum);
    }

    public static Integer calculateSumFilterByAge(List<User> numbers, int age) {
        return numbers.stream().map(User::getAge).filter(userAge -> userAge >age).reduce(0, Integer::sum);
    }


}
