package com.mouse.spel_demo;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class ObjectArrayEvaluationTests {

        static List<User> list = new ArrayList<User>() {{
            add(User.builder()
                    .age(10)
                    .name("aa")
                    .build());
            add(User.builder()
                    .age(15)
                    .name("bb")
                    .build());
            add(User.builder()
                    .age(20)
                    .name("cc")
                    .build());
        }};

    @Test
    void listFilterContext() {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("list", list);
        ExpressionParser parser = new SpelExpressionParser();
        val filterList = (List<User>) parser.parseExpression("#list.?[#this.age > 10]").getValue(context);
        log.info(filterList.toString());
    }

    @Test
    void listFilterContext1() {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("list", list);

        ExpressionParser parser = new SpelExpressionParser();
        val filterList = (List<User>) parser.parseExpression("#list.?[#this.age > 10 and {\"aa\",\"bb\", \"cc\"}.contains(#this.name)]").getValue(context);
        log.info(filterList.toString());
    }

    @Test
    void testFunctions1() throws NoSuchMethodException {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("list", list);
        context.registerFunction("isEmpty", SpelFunctions.class.getDeclaredMethod("isEmpty", String.class));

        ExpressionParser parser = new SpelExpressionParser();
        val filterList = parser.parseExpression("#isEmpty(\"bbb\")").getValue(context);
        log.info(filterList.toString());
    }

    @Test
    void testFunctions2() throws NoSuchMethodException {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("list", list);
        context.registerFunction("isChild", SpelFunctions.class.getDeclaredMethod("isChild", User.class));

        ExpressionParser parser = new SpelExpressionParser();
        val filterList = (List<User>) parser.parseExpression("#list.?[#isChild(#this)]").getValue(context);
        log.info(filterList.toString());
    }

}
