package com.wpw.demo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class FutureTaskDemoOne extends FutureTaskAstract<Map<String, String>> {

    @Override
    public Map<String, String> customeHandle(Map<String, String> stringStringMap) {
        String param = stringStringMap.get("param");
        stringStringMap.put("result", param + "1");
        try {
            Random random = new Random();
            Integer costTime = random.nextInt() * (3000 - 2000 + 1) + 2000;
            Thread.sleep(costTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return stringStringMap;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        StopWatch totalStopWatch = new StopWatch();
        totalStopWatch.start("总耗时");
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> mapOne = new HashMap<>();
        mapOne.put("param", "a");
        Map<String, String> mapTwo = new HashMap<>();
        mapTwo.put("param", "b");
        Map<String, String> mapThree = new HashMap<>();
        mapThree.put("param", "c");
        list.add(mapOne);
        list.add(mapTwo);
        list.add(mapThree);
        FutureTaskDemoOne futureTaskDemoOne = new FutureTaskDemoOne();
        List<Map<String, String>> result = futureTaskDemoOne.parallelHandle(list);
        totalStopWatch.stop();
        System.out.println("result:" + JSONObject.toJSONString(result));
        System.out.println("耗时：" + totalStopWatch.getLastTaskTimeMillis());
    }
}
