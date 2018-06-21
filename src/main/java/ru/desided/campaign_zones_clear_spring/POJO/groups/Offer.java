package ru.desided.campaign_zones_clear_spring.POJO.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.desided.campaign_zones_clear_spring.logic.CustomsDeserializers.CustomDecDeserializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Offer implements Serializable {
    private String group1;
    private String group2;
    private String group3;
    private String offerId;
    private String offerName;
    private String os;
    private String browser;
    @JsonDeserialize(using = CustomDecDeserializer.class)
    private BigDecimal visits;
    @JsonDeserialize(using = CustomDecDeserializer.class)
    private BigDecimal clicks;
    private BigDecimal conversions;
    private BigDecimal revenue;
    private BigDecimal roi;
    private BigDecimal cost;
    private BigDecimal profit;
    private BigDecimal revenueDay;
    private BigDecimal costDay;
    private BigDecimal profitDay;
    private BigDecimal visitsDay;
    private BigDecimal profitHour;
    private BigDecimal cpv;
    private BigDecimal ctr;
    private BigDecimal cr;
    private BigDecimal cv;
    private BigDecimal epv;
    private BigDecimal epc;

    public BigDecimal getConversions() {
        return conversions;
    }

    public void setConversions(BigDecimal conversions) {
        this.conversions = conversions;
    }

    public String getGroup1() {
        return group1;
    }

    public void setGroup1(String group1) {
        this.group1 = group1;
    }

    public String getGroup2() {
        return group2;
    }

    public void setGroup2(String group2) {
        this.group2 = group2;
    }

    public String getGroup3() {
        return group3;
    }

    public void setGroup3(String group3) {
        this.group3 = group3;
    }

    public BigDecimal getCpv() {
        return cpv;
    }

    public void setCpv(BigDecimal cpv) {
        this.cpv = cpv;
    }

    public BigDecimal getCtr() {
        return ctr;
    }

    public void setCtr(BigDecimal ctr) {
        this.ctr = ctr;
    }

    public BigDecimal getCr() {
        return cr;
    }

    public void setCr(BigDecimal cr) {
        this.cr = cr;
    }

    public BigDecimal getCv() {
        return cv;
    }

    public void setCv(BigDecimal cv) {
        this.cv = cv;
    }

    public BigDecimal getEpv() {
        return epv;
    }

    public void setEpv(BigDecimal epv) {
        this.epv = epv;
    }

    public BigDecimal getEpc() {
        return epc;
    }

    public void setEpc(BigDecimal epc) {
        this.epc = epc;
    }

    public BigDecimal getRevenueDay() {
        return revenueDay;
    }

    public void setRevenueDay(BigDecimal revenueDay) {
        this.revenueDay = revenueDay;
    }

    public BigDecimal getCostDay() {
        return costDay;
    }

    public void setCostDay(BigDecimal costDay) {
        this.costDay = costDay;
    }

    public BigDecimal getProfitDay() {
        return profitDay;
    }

    public void setProfitDay(BigDecimal profitDay) {
        this.profitDay = profitDay;
    }

    public BigDecimal getVisitsDay() {
        return visitsDay;
    }

    public void setVisitsDay(BigDecimal visitsDay) {
        this.visitsDay = visitsDay;
    }

    public BigDecimal getProfitHour() {
        return profitHour;
    }

    public void setProfitHour(BigDecimal profitHour) {
        this.profitHour = profitHour;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public BigDecimal getVisits() {
        return visits;
    }

    public void setVisits(BigDecimal visits) {
        this.visits = visits;
    }

    public BigDecimal getClicks() {
        return clicks;
    }

    public void setClicks(BigDecimal clicks) {
        this.clicks = clicks;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getRoi() {
        return roi;
    }

    public void setRoi(BigDecimal roi) {
        this.roi = roi.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit.setScale(2, RoundingMode.HALF_UP);
    }
}
