package ru.desided.campaign_zones_clear_spring.POJO.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.desided.campaign_zones_clear_spring.logic.CustomsDeserializers.CustomDateDeserializer;
import ru.desided.campaign_zones_clear_spring.logic.CustomsDeserializers.CustomDecDeserializer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonObject {

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
    private BigDecimal cpv;
    private BigDecimal ctr;
    private BigDecimal cr;
    private BigDecimal cv;
    private BigDecimal epv;
    private BigDecimal epc;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date day;

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

    public BigDecimal getConversions() {
        return conversions;
    }

    public void setConversions(BigDecimal conversions) {
        this.conversions = conversions;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
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
