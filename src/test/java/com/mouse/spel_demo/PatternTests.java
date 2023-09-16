package com.mouse.spel_demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
class PatternTests {

    @Test
    void testCriteria() {
        String s = "productType=='B' and tenor in ('0D', '3M') and portfolio in ('A', 'B') and threshold <= 10.00 and formula = SUM({'LGB'}, {tenor}) - SUM({'CB', 'DB'}, {tenor})";
        String[] list = s.split("and");
        List<String> conditionList = new ArrayList<>();
        List<String> tenorList = new ArrayList<>();

        for (String condition : list) {
            if (condition.contains("tenor") && !condition.contains("formula")) {
                Pattern p = Pattern.compile("'(.*?)'");
                Matcher m = p.matcher(condition);
                while (m.find()) {
                    tenorList.add(m.group());
                }
                //pass tenor
                continue;
            }

            if (condition.contains("formula")) {
                //process formula
                //parse first argument productSubType
//                SUM(condition + "subType").tenorMap[]
                continue;

            }

            if (condition.contains("threshold")) {
                //threshold
                String thresholdCriteria = condition;
                System.out.println("threshold="+thresholdCriteria);
                Pattern p = Pattern.compile("([\\d.]+)");
                Matcher m = p.matcher(condition);
                Double thresholdNum = 0.d;
                while (m.find()) {
                    thresholdNum = Double.valueOf( m.group());
                }
                System.out.println("thresholdNum="+thresholdNum);
                continue;
            }
            conditionList.add(condition);
        }

        String condition = String.join("and", conditionList);
        //convert condition
        String convertedCondition = condition;//Utils.convert TODO

        String tenorStr = ".![tenorMap].![" +
                String.join(",", tenorList.stream().map(tenor -> "#this["+ tenor +"]").collect(Collectors.toList()))
                + "]";
        //parse formula
        //transform SUM({'LGB'}, {tenor}) - SUM({'CB', 'DB'}, {tenor}) to
        // #SUM(#list[condition + {subType}.contains(productSubType)].![tenorMap].![#this[3M],#this[9M]])
        for (String conditionTmp : list) {
            if (conditionTmp.contains("formula")) {
                String formula = conditionTmp.replaceAll("formula\\s*=+\\s*", "");
                Pattern p = Pattern.compile("SUM\\((.*?)\\)");
                Matcher m = p.matcher(formula);
                while (m.find()) {
                    String formulaToken = m.group(); //SUM({'LGB'}, {tenor})
                    String arguments = m.group(1);//{'LGB'}, {tenor}
                    String productSubTypeScope = arguments.split(",")[0];

                    formula = formula.replace(formulaToken, "#SUM(#list[" +
                            convertedCondition
                             + " and " +  productSubTypeScope + ".contains(productSubType)]"
                            + tenorStr
                            + ")"
                     );
                }
                System.out.println(formula);
            }
        }
        System.out.println(condition);


    }
    //threshold
//    @Test
//    String testThresholdCriteria(String a) {
//        Pattern p = Pattern.compile("(threshold\\s*[{<=>}]+\\s*[\\d.]+)");
//        Matcher m  =p.matcher(a);
//        String thresholdCriteria = null;
//        while (m.find()) {
//            System.out.println(m.groupCount());
//            System.out.println(m.group());
//            System.out.println(m);
//            thresholdCriteria = m.group();
//
//        }
//
//        return thresholdCriteria;
//    }

    @Test
    public void s() {
//        Pattern p = Pattern.compile("([\\d.]+)");
//        Matcher m  = p.matcher("threshold <= 10.00");
//        double threshold = 0.d;
//        while (m.find()) {
//            System.out.println(m.group());
//            threshold =Double.valueOf( m.group());
//
//        }
//
//        return threshold;
    }


}
