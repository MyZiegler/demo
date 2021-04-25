package com.wpw.demo.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @auther wupeiwen
 * @description
 * @date 2021/3/5 0005
 */

@Data
public class LinePriceData {
    /**
     * 计算后的总费用 （含税不含利润）
     */
    private BigDecimal price = BigDecimal.ZERO;
    /**
     *  上游费用（含税、含利润）
     */
    private BigDecimal priceProfit = BigDecimal.ZERO;
    /**
     * 不含税总计(裸价)
     */
    private BigDecimal priceU = BigDecimal.ZERO;
    /**
     *  折扣后金额总计  （含税含利润）
     */
    private BigDecimal priceProfitDis = BigDecimal.ZERO;
    /**
     *  开始城市名称
     */
    private String startCity;
    /**
     *  到达城市名称
     */
    private String endCity;
    /**
     *  开始城市编码
     */
    private Integer startCityId;
    /**
     * 到达城市编码
     */
    private Integer endCityId;
    /**
     * 时效   单位：天
     */
    private String timeEfficiency;

    private Boolean linePriceSxbSmallPrice;
    /**
     * 送货费（只含税）
     */
    private BigDecimal deliveryShortPrice = BigDecimal.ZERO;
    /**
     * 提货费（只含税）
     */
    private BigDecimal sendShortPrice = BigDecimal.ZERO;

    /**
     * 送货费用（含税+利润）
     */
    private BigDecimal deliveryShortPriceTaxProfit = BigDecimal.ZERO;

    /**
     * 提货费用（含税+利润）
     */
    private BigDecimal sendShortPriceTaxProfit = BigDecimal.ZERO;
    /**
     * 费用信息
     */
    private LinePriceInfo dicLinePriceInfo;

    /*************************************************/
    /**
     * 吨保价 （单位元） 含税不含利润
     */
    private BigDecimal ntonPrice;
    /**
     *  方报价（单位元） 含税不含利润
     */
    private BigDecimal ncubePrice;
    /**
     * 千克报价（含税）不含利润
     */
    private BigDecimal nkgPrice;

    /**
     *  千克报价（含税含利润）
     */
    private BigDecimal nkgPriceProfit;
    /**
     *  方报价（单位元） 含税含利润
     */
    private BigDecimal ncubePriceProfit;
    /**
     *  吨保价 （含税含利润）
     */
    private BigDecimal ntonPriceProfit;
    /**
     *  启运费 （含税含利润）
     */
    private BigDecimal startPriceProfit;
    /**
     *  启运价（ 只含税不含利润）
     */
    private BigDecimal taxIncludeStartPrice;
    /**
     *  出发地名称
     */
    private String vstartCity;
    /**
     *  目的地名称
     */
    private String varriveCity;
    /**
     *   出发地代码
     */
    private Long nstartCity;
    /**
     *   目的地代码
     */
    private Long narriveCity;
    /**
     * 吨报价（不含税）
     */
    private BigDecimal ntonPriceU;
    /**
     *  方报价（不含税）
     */
    private BigDecimal ncubePriceU;
    /**
     * 千克报价（不含税）
     */
    private BigDecimal nKgPriceU;
    /**
     *  时效 单位待定
     */
    private String vtimeEfficiency;
    /**
     *  计算折扣类型 1-吨折扣 2-方折扣 3-公斤折扣 4-启运价折扣
     */
    private Integer discountType;
    /**
     *  参与计算的折扣
     */
    private BigDecimal discount;
    /**
     *  折扣优惠金额
     */
    private BigDecimal discountAmount;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if(price != null){
            this.price = price;
        }
    }

    public Boolean getLinePriceSxbSmallPrice() {
        return linePriceSxbSmallPrice;
    }

    public void setLinePriceSxbSmallPrice(Boolean linePriceSxbSmallPrice) {
        this.linePriceSxbSmallPrice = linePriceSxbSmallPrice;
    }

    public BigDecimal getDeliveryShortPrice() {
        return deliveryShortPrice;
    }

    public void setDeliveryShortPrice(BigDecimal deliveryShortPrice) {
        if(deliveryShortPrice!= null){
            this.deliveryShortPrice = deliveryShortPrice;
        }
    }

    public BigDecimal getSendShortPrice() {
        return sendShortPrice;
    }

    public void setSendShortPrice(BigDecimal sendShortPrice) {
        if(sendShortPrice!= null){
            this.sendShortPrice = sendShortPrice;
        }
    }

    public LinePriceInfo getDicLinePriceInfo() {
        return dicLinePriceInfo;
    }

    public void setDicLinePriceInfo(LinePriceInfo dicLinePriceInfo) {
        this.dicLinePriceInfo = dicLinePriceInfo;
    }

    public String getTimeEfficiency() {
        return timeEfficiency;
    }

    public void setTimeEfficiency(String timeEfficiency) {
        this.timeEfficiency = timeEfficiency;
    }

    public BigDecimal getNtonPrice() {
        return ntonPrice;
    }

    public void setNtonPrice(BigDecimal ntonPrice) {
        this.ntonPrice = ntonPrice;
    }

    public BigDecimal getNcubePrice() {
        return ncubePrice;
    }

    public void setNcubePrice(BigDecimal ncubePrice) {
        this.ncubePrice = ncubePrice;
    }

    public String getVstartCity() {
        return vstartCity;
    }

    public void setVstartCity(String vstartCity) {
        this.vstartCity = vstartCity;
    }

    public String getVarriveCity() {
        return varriveCity;
    }

    public void setVarriveCity(String varriveCity) {
        this.varriveCity = varriveCity;
    }

    public Long getNstartCity() {
        return nstartCity;
    }

    public void setNstartCity(Long nstartCity) {
        this.nstartCity = nstartCity;
    }

    public Long getNarriveCity() {
        return narriveCity;
    }

    public void setNarriveCity(Long narriveCity) {
        this.narriveCity = narriveCity;
    }

    public BigDecimal getNtonPriceU() {
        return ntonPriceU;
    }

    public void setNtonPriceU(BigDecimal ntonPriceU) {
        this.ntonPriceU = ntonPriceU;
    }

    public BigDecimal getNcubePriceU() {
        return ncubePriceU;
    }

    public void setNcubePriceU(BigDecimal ncubePriceU) {
        this.ncubePriceU = ncubePriceU;
    }

    public String getVtimeEfficiency() {
        return vtimeEfficiency;
    }

    public void setVtimeEfficiency(String vtimeEfficiency) {
        this.vtimeEfficiency = vtimeEfficiency;
    }

    public BigDecimal getNkgPrice() {
        return nkgPrice;
    }

    public void setNkgPrice(BigDecimal nkgPrice) {
        this.nkgPrice = nkgPrice;
    }

    public BigDecimal getnKgPriceU() {
        return nKgPriceU;
    }

    public void setnKgPriceU(BigDecimal nKgPriceU) {
        this.nKgPriceU = nKgPriceU;
    }

    public BigDecimal getPriceProfit() {
        return priceProfit;
    }

    public void setPriceProfit(BigDecimal priceProfit) {
        this.priceProfit = priceProfit;
    }

    public BigDecimal getNkgPriceProfit() {
        return nkgPriceProfit;
    }

    public void setNkgPriceProfit(BigDecimal nkgPriceProfit) {
        this.nkgPriceProfit = nkgPriceProfit;
    }

    public BigDecimal getNcubePriceProfit() {
        return ncubePriceProfit;
    }

    public void setNcubePriceProfit(BigDecimal ncubePriceProfit) {
        this.ncubePriceProfit = ncubePriceProfit;
    }

    public BigDecimal getNtonPriceProfit() {
        return ntonPriceProfit;
    }

    public void setNtonPriceProfit(BigDecimal ntonPriceProfit) {
        this.ntonPriceProfit = ntonPriceProfit;
    }

    public BigDecimal getPriceU() {
        return priceU;
    }

    public void setPriceU(BigDecimal priceU) {
        if(!Objects.isNull(priceU)){
            this.priceU = priceU;
        }
    }

    public BigDecimal getPriceProfitDis() {
        return priceProfitDis;
    }

    public void setPriceProfitDis(BigDecimal priceProfitDis) {
        if(!Objects.isNull(priceProfitDis)){
            this.priceProfitDis = priceProfitDis;
        }
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public Integer getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(Integer startCityId) {
        this.startCityId = startCityId;
    }

    public Integer getEndCityId() {
        return endCityId;
    }

    public void setEndCityId(Integer endCityId) {
        this.endCityId = endCityId;
    }

    public BigDecimal getStartPriceProfit() {
        return startPriceProfit;
    }

    public void setStartPriceProfit(BigDecimal startPriceProfit) {
        this.startPriceProfit = startPriceProfit;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTaxIncludeStartPrice() {
        return taxIncludeStartPrice;
    }

    public void setTaxIncludeStartPrice(BigDecimal taxIncludeStartPrice) {
        this.taxIncludeStartPrice = taxIncludeStartPrice;
    }

    public BigDecimal getDeliveryShortPriceTaxProfit() {
        return deliveryShortPriceTaxProfit;
    }

    public void setDeliveryShortPriceTaxProfit(BigDecimal deliveryShortPriceTaxProfit) {
        this.deliveryShortPriceTaxProfit = deliveryShortPriceTaxProfit;
    }

    public BigDecimal getSendShortPriceTaxProfit() {
        return sendShortPriceTaxProfit;
    }

    public void setSendShortPriceTaxProfit(BigDecimal sendShortPriceTaxProfit) {
        this.sendShortPriceTaxProfit = sendShortPriceTaxProfit;
    }
}

