package com.mouse.spel_demo;

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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest
class SpelDemoApplicationTests {

    @Test
    void hello() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'");
        String message = (String) exp.getValue();
        log.info(message);
    }

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
                    .name("FR007_1Y*2Y")
                    .build());
            add(User.builder()
                    .age(30)
                    .name("cc")
                    .build());
        }};

    @Test
    void listFilter() {

        ExpressionParser parser = new SpelExpressionParser();
        List<User> filterList = (List<User>) parser.parseExpression("#{1 == 1}").getValue(list);
        log.info(filterList.toString());
    }


    @Test
    void listFilterContext() throws InterruptedException, ExecutionException {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("list", list);

        ExpressionParser parser = new SpelExpressionParser();
        val filterList = (List<User>) parser.parseExpression("#list.?[#this.age ge 10]").getValue(context);
        log.info(filterList.toString());
    }

    @Test
    void listFilterContext1() {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("list", list);

        ExpressionParser parser = new SpelExpressionParser();
        val filterList = (List<User>) parser.parseExpression("#list.?[#this.age > 10L and {\"aa\",\"bb\", \"FR007_1Y*2Y\", 'cc'}.contains(#this.name)]").getValue(context);
        log.info(filterList.toString());

        for (val user : list) {
            val p = parser.parseExpression("name matches \"a.*\"").getValue(user);
            log.info(p.toString());
        }

        val ret = FilenameUtils.wildcardMatch("baac","*aa*");
        log.info("ret={}",ret);
    }

}
