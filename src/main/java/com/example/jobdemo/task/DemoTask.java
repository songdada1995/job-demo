package com.example.jobdemo.task;

import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 */
@Component("demoTask")
public class DemoTask {

    public void demoNoParams() {
        System.out.println("执行无参方法");
    }

    public void demoParams(String params) {
        try {
            System.out.println("执行有参方法：" + params + "开始");
            Thread.sleep(5 * 1000);
            System.out.println("执行有参方法：" + params + "结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void demoMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(String.format("执行多参方法：字符串类型{%s}，布尔类型{%s}，长整型{%s}，浮点型{%s}，整形{%s}", s, b, l, d, i));
    }

    public void demoWithParamsError(String params) throws InterruptedException {
        try {
            System.out.println("执行有参异常方法：" + params + "开始");
            Thread.sleep(5 * 1000);
            throw new RuntimeException("手动异常！");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
