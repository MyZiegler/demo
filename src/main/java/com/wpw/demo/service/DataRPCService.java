package com.wpw.demo.service;

import com.wpw.demo.pojo.LinePriceBase;
import com.wpw.demo.pojo.LinePriceParam;

public interface DataRPCService {

    LinePriceBase calculateLinePrice(LinePriceParam linePriceParam);

}


