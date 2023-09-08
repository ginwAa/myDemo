//package com.demo;
//
//import com.alibaba.csp.sentinel.Entry;
//import com.alibaba.csp.sentinel.SphU;
//import com.alibaba.csp.sentinel.context.ContextUtil;
//import com.alibaba.csp.sentinel.slots.block.BlockException;
//import com.alibaba.csp.sentinel.slots.block.RuleConstant;
//import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
//import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
//import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
//import com.alibaba.csp.sentinel.slots.system.SystemRule;
//import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Collections;
//
//@SpringBootTest
//public class SentinelTest {
//    public static final String RESOURCE_NAME = "consumer/consume";
//
//    public static void initSlowRequestRatioRules() {
//        DegradeRule rule = new DegradeRule();
//        rule.setGrade(CircuitBreakerStrategy.SLOW_REQUEST_RATIO.getType());
//        DegradeRuleManager.loadRules(Collections.singletonList(rule));
//    }
//
//    public static void initErrorCountRules() {
//        DegradeRule rule = new DegradeRule();
//        rule.setGrade(CircuitBreakerStrategy.ERROR_COUNT.getType());
//        DegradeRuleManager.loadRules(Collections.singletonList(rule));
//    }
//
//    public static void initErrorRatioRules() {
//        DegradeRule rule = new DegradeRule();
//        rule.setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType());
//        DegradeRuleManager.loadRules(Collections.singletonList(rule));
//    }
//
//    public static void initSystemRules() {
//        SystemRule rule = new SystemRule();
//        // max load is 3
//        rule.setHighestSystemLoad(3.0);
//        // max cpu usage is 60%
//        rule.setHighestCpuUsage(0.6);
//        // max avg rt of all request is 10 ms
//        rule.setAvgRt(10);
//        // max total qps is 20
//        rule.setQps(20);
//        // max parallel working thread is 10
//        rule.setMaxThread(10);
//        SystemRuleManager.loadRules(Collections.singletonList(rule));
//    }
//
//    private static void initWhiteRules() {
//        AuthorityRule rule = new AuthorityRule();
//        rule.setResource(RESOURCE_NAME);
//        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
//        rule.setLimitApp("A");
//        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
//    }
//
//    private static void initBlackRules() {
//        AuthorityRule rule = new AuthorityRule();
//        rule.setResource(RESOURCE_NAME);
//        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
//        rule.setLimitApp("B");
//        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
//    }
//
//    @Test
//    public void testMain() {
////        initBlackRules();
////        initWhiteRules();
////        initSlowRequestRatioRules();
////        initErrorCountRules();
////        initErrorRatioRules();
////        initSystemRules();
//        for (int i = 0; i < 10000; ++i) {
//            testFor(RESOURCE_NAME, "testOrigin");
//        }
//    }
//
//    public void testFor(String resource, String origin) {
//        ContextUtil.enter(resource, origin);
//        Entry entry = null;
//        try {
//            entry = SphU.entry(resource);
//            // TODO ...
//            System.out.printf("Passed for resource %s, origin is %s%n", resource, origin);
//        } catch (BlockException ex) {
//            System.err.printf("Blocked for resource %s, origin is %s%n", resource, origin);
//        } finally {
//            if (entry != null) {
//                entry.exit();
//            }
//            ContextUtil.exit();
//        }
//    }
//
//}
