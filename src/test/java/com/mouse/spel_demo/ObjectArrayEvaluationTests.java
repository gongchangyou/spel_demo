package com.mouse.spel_demo;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

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

}
