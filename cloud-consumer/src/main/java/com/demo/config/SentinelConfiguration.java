package com.demo.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreaker;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.EventObserverRegistry;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.util.TimeUtil;
import com.demo.properties.SentinelProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class SentinelConfiguration {
    private static final String RESOURCE_NAME = "consumer/consume";

    // 流量控制
    public static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        //规则作用对象
        rule.setResource(RESOURCE_NAME);
        //流量控制规则
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS.
        rule.setCount(1000);
        //限流对象
        rule.setLimitApp("default");
        //限流策略及对象节点
//        rule.setStrategy(RuleConstant.STRATEGY_DIRECT);
//        rule.setRefResource("...");
        //流控方式
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP_RATE_LIMITER);
        // 最大排队时间
        rule.setMaxQueueingTimeMs(5000);
        //预热时间
        rule.setWarmUpPeriodSec(10);
        rules.add(rule);

        FlowRuleManager.loadRules(rules);
    }

    // 熔断控制
    public static void initSlowRequestRatioRules() {
        DegradeRule rule = new DegradeRule();
        rule.setGrade(CircuitBreakerStrategy.SLOW_REQUEST_RATIO.getType());
        rule.setResource(RESOURCE_NAME);
        rule.setLimitApp("default");

        rule.setSlowRatioThreshold(0.1);
        rule.setCount(1); // 划分为慢响应的最小响应时间ms

        rule.setMinRequestAmount(100);
        rule.setStatIntervalMs(1000);

        rule.setTimeWindow(5); //熔断时间 秒
        DegradeRuleManager.loadRules(Collections.singletonList(rule));
    }

    public static void initErrorCountRules() {
        DegradeRule rule = new DegradeRule();
        rule.setGrade(CircuitBreakerStrategy.ERROR_COUNT.getType());
        rule.setMinRequestAmount(100);
        rule.setStatIntervalMs(1000);
        rule.setCount(-1);
        rule.setResource(RESOURCE_NAME);
        rule.setLimitApp("default");
        DegradeRuleManager.loadRules(Collections.singletonList(rule));
    }

    public static void initErrorRatioRules() {
        DegradeRule rule = new DegradeRule();
        rule.setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType());
        rule.setMinRequestAmount(100);
        rule.setStatIntervalMs(1000);
        rule.setResource(RESOURCE_NAME);
        rule.setLimitApp("default");
        DegradeRuleManager.loadRules(Collections.singletonList(rule));
    }

    // 熔断事件监听器
    public static void registerStateChangeObserver() {
        EventObserverRegistry.getInstance().addStateChangeObserver("logging",
                (prevState, newState, rule, snapshotValue) -> {
                    if (newState == CircuitBreaker.State.OPEN) {
                        System.out.printf("%s -> OPEN at %d, snapshotValue=%.2f%n", prevState.name(),
                                TimeUtil.currentTimeMillis(), snapshotValue);
                    } else {
                        System.out.printf("%s -> %s at %d%n", prevState.name(), newState.name(),
                                TimeUtil.currentTimeMillis());
                    }
                });
    }

    // 系统控制
    public static void initSystemRules() {
        SystemRule rule = new SystemRule();
        // max load is 3, only unix/linux available
//        rule.setHighestSystemLoad(3.0);
        // max cpu usage is 60%
        rule.setHighestCpuUsage(0.95);
        // max avg rt of all request is 10 ms
        rule.setAvgRt(3000);
        // max total qps is 20
        rule.setQps(1000);
        // max parallel working thread is 10
        rule.setMaxThread(10);
        rule.setResource(RESOURCE_NAME);
        rule.setLimitApp("default");
        SystemRuleManager.loadRules(Collections.singletonList(rule));
    }

    // 访问控制
    private static void initWhiteRules() {
        AuthorityRule rule = new AuthorityRule();
        rule.setResource(RESOURCE_NAME);
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        rule.setLimitApp("A");
        rule.setLimitApp("default");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
    }

    private static void initBlackRules() {
        AuthorityRule rule = new AuthorityRule();
        rule.setResource(RESOURCE_NAME);
        rule.setLimitApp("default");
        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule.setLimitApp("B");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
