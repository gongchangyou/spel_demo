package com.mouse.spel_demo;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest
class SpelMethodTests {
    //问题就是 filter在哪里做的，java method代码中，还是SpEL中
    //回答： code胜出

    static List<User> list;
    static {
        list = new ArrayList<User>();
        for (int i=0;i<10000;i++) {
            list.add(User.builder()
                    .age(i)
                    .name("mouse" + i)
                    .build());
        }
    }
    @Test
    public void sum() throws NoSuchMethodException {

        // 创建 SpEL 表达式解析器
        SpelExpressionParser parser = new SpelExpressionParser();

        // 创建 SpEL 表达式，引用 Java 代码中的方法来计算总和
        Expression expression = parser.parseExpression("#calculateSum(#userList)");

        // 创建评估上下文并设置方法
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("calculateSum", SpelMethodTests.class.getDeclaredMethod("calculateSum", List.class));

        // 设置变量
        context.setVariable("userList", list);

        // 通过评估上下文计算表达式的值
        Integer result = expression.getValue(context, Integer.class);

        System.out.println("Sum: " + result);
    }

    public static Integer calculateSum(List<User> numbers) {
        return numbers.stream().map(User::getAge).reduce(0, Integer::sum);
    }

    public static Integer calculateSumFilterByAge(List<User> numbers, int age) {
        return numbers.stream().map(User::getAge).filter(userAge -> userAge >age).reduce(0, Integer::sum);
    }
    @Test
    public void sumFilterBySpElMulti() throws NoSuchMethodException {
        for (int i=0;i<10;i++) {
            sumFilterBySpEl();
        }
    }

    @Test
    public void sumFilterByCodeMulti() throws NoSuchMethodException {
        for (int i=0;i<10;i++) {
            sumFilterByCode();
        }
    }
    @Test
    public void sumFilterBySpEl() throws NoSuchMethodException {
        StopWatch sw = new StopWatch();
        sw.start();
        System.out.println(System.currentTimeMillis());

        // 创建 SpEL 表达式解析器
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
                this.getClass().getClassLoader());

        SpelExpressionParser parser = new SpelExpressionParser(config);
//        SpelExpressionParser parser = new SpelExpressionParser();

        // 创建 SpEL 表达式，引用 Java 代码中的方法来计算总和
        Expression expression = parser.parseExpression("#calculateSum(#userList.?[age>100])");

        // 创建评估上下文并设置方法
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("calculateSum", SpelMethodTests.class.getDeclaredMethod("calculateSum", List.class));

        // 设置变量
        context.setVariable("userList", list);

        // 通过评估上下文计算表达式的值
        Integer result = expression.getValue(context, Integer.class);

        System.out.println(System.currentTimeMillis() + "Sum: " + result);
        sw.stop();
        System.out.println(sw.prettyPrint());
    }

    @Test
    public void sumFilterByCode() throws NoSuchMethodException {
        StopWatch sw = new StopWatch();
        sw.start();
        // 创建 SpEL 表达式解析器
        SpelExpressionParser parser = new SpelExpressionParser();

        // 创建 SpEL 表达式，引用 Java 代码中的方法来计算总和
        Expression expression = parser.parseExpression("#calculateSumFilterByAge(#userList, 100)");

        // 创建评估上下文并设置方法
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("calculateSumFilterByAge", SpelMethodTests.class.getDeclaredMethod("calculateSumFilterByAge", List.class, int.class));

        // 设置变量
        context.setVariable("userList", list);

        // 通过评估上下文计算表达式的值
        Integer result = expression.getValue(context, Integer.class);

        System.out.println("Sum: " + result);
        sw.stop();
        System.out.println(sw.prettyPrint());
    }

}
