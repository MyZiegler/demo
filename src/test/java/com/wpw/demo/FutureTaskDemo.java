package com.wpw.demo;


import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FutureTaskDemo {

    private static ExecutorService QUERY_TASK_EXECUTOR = Executors.newWorkStealingPool(8);

    /**
     *
     */
    private static Map<String , Integer> map = new HashMap<>();
    private static final String apple = "APPLE";
    private static final String banana = "BANANA";
    static {
        map.put(apple,10);
        map.put(banana,11);
    }

    /**
     *  计算苹果总价
     * @param num
     * @return
     */
    private static Integer caculateApplePrice (Integer num) throws InterruptedException {
        Thread.sleep(2000);
        return num*1;
    }

    /**
     *  计算香蕉价格
     * @param num
     * @return
     */
    private static Integer caculateBananaPrice (Integer num) throws InterruptedException {
        Thread.sleep(1500);
        return num*2;
    }

    /**
     *  计算总价
     * @param bananaPrice
     * @param applePrice
     * @return
     */
    private static Integer caculateTotalPrice(Integer bananaPrice ,Integer applePrice) throws InterruptedException {
        Thread.sleep(500);
        return bananaPrice+applePrice;
    }

    private static Integer caculatefruitTotalPrice(){
        StopWatch totalStopWatch = new StopWatch();
        totalStopWatch.start("总耗时");
        StopWatch appleStopWatch = new StopWatch();
        CompletableFuture<Integer> caculateApplePriceFuture = CompletableFuture.supplyAsync(() -> {
            Integer applePrice = null;
            appleStopWatch.start("计算苹果价格");
            try {
                applePrice  = caculateApplePrice(map.get(apple));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            appleStopWatch.stop();
            System.out.println("1");
            System.out.println("applePrice:"+applePrice);
            return applePrice;
        }, QUERY_TASK_EXECUTOR);

        StopWatch bananaStopWatch = new StopWatch();


        CompletableFuture<Integer> caculateBananaPriceFuture = CompletableFuture.supplyAsync(() -> {
            Integer bananaPrice = null;
            bananaStopWatch.start("计算香蕉价格");
            try {
                bananaPrice = caculateBananaPrice(map.get(banana));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bananaStopWatch.stop();
            System.out.println("2");
            System.out.println("bananaPrice:"+bananaPrice);
            return bananaPrice;
        }, QUERY_TASK_EXECUTOR);

        CompletableFuture<Integer> queryResult = caculateApplePriceFuture.thenCombine(caculateBananaPriceFuture,(applePriceResult,bananaPriceResult)->{
            System.out.println("applePriceResult"+applePriceResult);
            System.out.println("bananaPriceResult"+bananaPriceResult);
            Integer totalPrice = null;
            try {
                totalPrice =  caculateTotalPrice(applePriceResult,bananaPriceResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return totalPrice;
        });

        queryResult.whenComplete((result, throwable) -> {
            System.out.println("result:"+result);

        });
        Integer totalPrice =  queryResult.join();
        totalStopWatch.stop();
        System.out.println("计算苹果价格耗时："+ appleStopWatch.getLastTaskTimeMillis());
        System.out.println("计算香蕉价格耗时："+ bananaStopWatch.getLastTaskTimeMillis());
        System.out.println("计算总价格耗时："+ totalStopWatch.getLastTaskTimeMillis());
        return totalPrice;


    }

    private static Map<String,Integer> caculateAllTotalPrice(){
        Map<String ,Integer> resultMap = new HashMap<>();
        BigDecimal distanceFen = BigDecimal.ZERO;
        StopWatch threadOneStopWatch = new StopWatch();
        CompletableFuture<Integer> oneFuture = CompletableFuture.supplyAsync(() -> {
            threadOneStopWatch.start("计算第一车车水果总价格");
            Integer price = caculatefruitTotalPrice();
            threadOneStopWatch.stop();
            return price;
        }, QUERY_TASK_EXECUTOR);
        StopWatch threadTwoStopWatch = new StopWatch();
        CompletableFuture<Integer> twoFuture = CompletableFuture.supplyAsync(() -> {
            threadTwoStopWatch.start("计算第二车水果总价格");
            Integer price = caculatefruitTotalPrice();
            threadTwoStopWatch.stop();
            return price;
        }, QUERY_TASK_EXECUTOR);

        CompletableFuture<BigDecimal> queryResult = oneFuture.thenCombine(twoFuture,(onePriceResult,twoPriceResult)->{
            resultMap.put("oneTotalPrice",onePriceResult);
            resultMap.put("twoTotalPrice",twoPriceResult);

            return BigDecimal.ZERO;
        });
        queryResult.whenComplete((result, throwable) -> {
            System.out.println("totalResult:"+result);

        });
        queryResult.join();
        return resultMap;
    }

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("两车计算开始");
        caculateAllTotalPrice();
        stopWatch.stop();
        System.out.println("耗时："+stopWatch.getLastTaskTimeMillis());

    }
}
