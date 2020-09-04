package com.wpw.demo;

import com.wpw.demo.exception.RestException;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class FutureTaskAstract<T> {

    private static ExecutorService executor  = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2 + 1);

    public abstract T customeHandle(T t );


    public List<T> parallelHandle(List<T> needHandleList) throws InterruptedException, ExecutionException {
        if(CollectionUtils.isEmpty(needHandleList)){
            throw new RestException(" list data is empty");
        }
        Integer needHandleSize = needHandleList.size();
        CountDownLatch latch = new CountDownLatch(needHandleSize);
        CompletionService<T> cs = new ExecutorCompletionService<>(executor);// 异步向电商S1询价
        for(int i = 0 ; i <needHandleSize ; i ++){
            int finalI = i;
            cs.submit(()-> innerHandle(latch,needHandleList.get(finalI)));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<T> list = new ArrayList<>();
        for(int i = 0  ; i < needHandleSize ; i ++  ){
             list.add(cs.take().get());
        }
        return list;
    }

    private  T innerHandle(CountDownLatch latch, T t){
        latch.countDown();
       return customeHandle(t);
    }

}
