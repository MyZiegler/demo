package com.wpw.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.wpw.demo.exception.RestException;
import com.wpw.demo.pojo.*;
import com.wpw.demo.service.DataRPCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @auther wupeiwen
 * @description
 * @date 2021/3/5 0005
 */
@Slf4j
@Service
public class DataRPCServiceImpl implements DataRPCService {

    @Autowired
    private RestTemplate restTemplate;

    private static ExecutorService QUERY_TASK_EXECUTOR = Executors.newWorkStealingPool(8);

    public static final String quoteServiceURL = "http://192.168.1.10:18080/lecar-datac-service";

    @Override
    public LinePriceBase calculateLinePrice(LinePriceParam linePriceParam) {
        String lineQuoteURL = quoteServiceURL + "/api/v1/lingdan/getPrice2";

        //并行计算获取发货地和收货地信息
        StopWatch caculatePriceWatch = new StopWatch();
        caculatePriceWatch.start("计算价格总耗时");
        StopWatch startDistanceWatch = new StopWatch();
        CompletableFuture<LineDistanceBase> caculateStartDistanceFuture = CompletableFuture.supplyAsync(() -> {
            log.info("计算出发地站点信息开始");
            startDistanceWatch.start("计算出发地距离信息");
            LineDistanceParam param = new LineDistanceParam();
            param.setAddress(linePriceParam.getStartAddress());
            param.setCityCode(linePriceParam.getStartAreaID());
            param.setCityName(linePriceParam.getStartCityName());
            linePriceParam.setRequestStartDistanceParamContent(JSONObject.toJSONString(param));
            LineDistanceBase lineDistanceBase = distanceCommon(param);
            linePriceParam.setResponseStartDistanceParamContent(JSONObject.toJSONString(lineDistanceBase));
            startDistanceWatch.stop();
            log.info("计算出发地站点信息结束  耗时：{}", startDistanceWatch.getLastTaskTimeMillis());
            return lineDistanceBase;
        }, QUERY_TASK_EXECUTOR);

        StopWatch endDistanceWatch = new StopWatch();

        CompletableFuture<LineDistanceBase> caculateEndDistanceFuture = CompletableFuture.supplyAsync(() -> {
            log.info("计算目的地站点信息开始");
            endDistanceWatch.start("计算目的地距离信息");
            LineDistanceParam param = new LineDistanceParam();
            param.setAddress(linePriceParam.getEndAddress());
            param.setCityCode(linePriceParam.getEndAreaID());
            param.setCityName(linePriceParam.getEndCityName());
            linePriceParam.setRequestEndDistanceParamContent(JSONObject.toJSONString(param));
            LineDistanceBase lineDistanceBase = distanceCommon(param);
            linePriceParam.setResponseEndDDistanceParamContent(JSONObject.toJSONString(lineDistanceBase));
            endDistanceWatch.stop();
            log.info("计算目的地站点信息结束  耗时：{}", endDistanceWatch.getLastTaskTimeMillis());
            return lineDistanceBase;
        }, QUERY_TASK_EXECUTOR);

        CompletableFuture<LinePriceBase> queryResult = caculateStartDistanceFuture.thenCombine(caculateEndDistanceFuture, (startDistanceResult, endDistanceResult) -> {
            if (!Objects.isNull(startDistanceResult) && !Objects.isNull(startDistanceResult.getData()) &&
                    !Objects.isNull(endDistanceResult) && !Objects.isNull(endDistanceResult.getData())) {
                LineDistanceData startDisanceData = startDistanceResult.getData();
                LineDistanceData endDisanceData = endDistanceResult.getData();
                linePriceParam.setStartFallGoodsinfo(startDisanceData.getFallGoodsPlaceInfoEntity());
                linePriceParam.setEndFallGoodsinfo(endDisanceData.getFallGoodsPlaceInfoEntity());
                linePriceParam.setStartDistance(startDisanceData.getDistance());
                linePriceParam.setEndDistance(endDisanceData.getDistance());
                linePriceParam.setRequestPriceParamContent(JSONObject.toJSONString(linePriceParam));
            }
            return linePriceCommon(lineQuoteURL, linePriceParam);
        });
        LinePriceBase result = queryResult.join();
        linePriceParam.setResponsePriceParamContent(JSONObject.toJSONString(result));
        caculatePriceWatch.stop();
        log.info("计算总价格耗时：{}", caculatePriceWatch.getLastTaskTimeMillis());
        return result;

    }

    public LineDistanceBase distanceCommon(LineDistanceParam lineDistanceParam) {
        String getDistanceUrl = quoteServiceURL + "/api/v1/lingdan/getDistance";
        ResponseEntity<JSONObject> stringResponseEntity;
        log.info("请求地区距离请求参数 :{}", JSONObject.toJSONString(lineDistanceParam));
        try {
            stringResponseEntity = restTemplate.postForEntity(getDistanceUrl, lineDistanceParam, JSONObject.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
        log.info("请求地区距离信息返回数据 result:{}", JSONObject.toJSONString(stringResponseEntity));
        if (stringResponseEntity.getBody() == null) {
            log.info("ERROR_DISTANCE 查询站点返回数据报错 param:{}", JSONObject.toJSONString(lineDistanceParam));
            return null;
        }
        LineDistanceBase lineDistanceBase = toBean(stringResponseEntity.getBody(), LineDistanceBase.class);
        if (Objects.isNull(lineDistanceBase.getData()) || !lineDistanceBase.getSuccess()) {
            log.info("ERROR_DISTANCE 查询站点查询不到对应站点 param:{}", JSONObject.toJSONString(lineDistanceParam));
        }
        return lineDistanceBase;
    }

    public static <T> T toBean(JSONObject object, Class<T> objClass) {
        try {
            return JSON.toJavaObject(object, objClass);
        } catch (Exception var3) {
            return null;
        }
    }

    public LinePriceBase linePriceCommon(String url, LinePriceParam linePriceParam) {

        ResponseEntity<JSONObject> stringResponseEntity;
        log.info("请求干线信息请求参数 :{}", JSONObject.toJSONString(linePriceParam));
        try {
            stringResponseEntity = restTemplate.postForEntity(url, linePriceParam, JSONObject.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RestException("干线接口查询失败");
        }
        log.info("请求干线信息返回数据 result:{}", JSONObject.toJSONString(stringResponseEntity));
        if (stringResponseEntity.getBody() == null) {
            throw new RestException("干线接口查询失败");
        }
        return toBean(stringResponseEntity.getBody(), LinePriceBase.class);
    }


}
