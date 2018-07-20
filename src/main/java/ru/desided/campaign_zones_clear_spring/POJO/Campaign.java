package ru.desided.campaign_zones_clear_spring.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.SerializationUtils;
import ru.desided.campaign_zones_clear_spring.POJO.groups.Offer;
import ru.desided.campaign_zones_clear_spring.POJO.groups.OffersList;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Campaign implements Serializable {

    private Boolean checkOfferOS = false;
    private Boolean checkOfferBrowser = false;
    private Boolean checkOfferOsBrowser = false;
    private String campaignName;
    private String campaignId;
    private String visits;
    private String conversions;
    private String cost;
    private String customVariable1;
    private String profit;
    private String ctr;
    private String revenue;
    private String averageConversion;
    private List<Zone> zonesList;
    private List<Zone> zonesListNew;
    private String summProfit;
    private Boolean active;
    private ObjectNode totals;
    private List<Offer> offersList;
    private String startDate;
    private String endDate;
    private BigDecimal diffDateDays;

    public BigDecimal getDiffDateDays() {
        return diffDateDays;
    }

    public void setDiffDateDays(BigDecimal diffDateDays) {
        this.diffDateDays = diffDateDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getCheckOfferOS() {
        return checkOfferOS;
    }

    public void setCheckOfferOS(Boolean checkOfferOS) {
        this.checkOfferOS = checkOfferOS;
    }

    public Boolean getCheckOfferBrowser() {
        return checkOfferBrowser;
    }

    public void setCheckOfferBrowser(Boolean checkOfferBrowser) {
        this.checkOfferBrowser = checkOfferBrowser;
    }

    public Boolean getCheckOfferOsBrowser() {
        return checkOfferOsBrowser;
    }

    public void setCheckOfferOsBrowser(Boolean checkOfferOsBrowser) {
        this.checkOfferOsBrowser = checkOfferOsBrowser;
    }

    public List<Offer> getOffersList() {
        return offersList;
    }

    public void setOffersList(List<Offer> offersList) {
        this.offersList = offersList;
    }

    public ObjectNode getTotals() {
        return totals;
    }

    public void setTotals(ObjectNode totals) {
        this.totals = totals;
    }

    public List<Zone> getZonesListNew() {
        return zonesListNew;
    }

    public void setZonesListNew(List<Zone> zonesListNew) {
        this.zonesListNew = zonesListNew;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSummProfit() {
        return summProfit;
    }

    public void setSummProfit(String summProfit) {
        this.summProfit = summProfit;
    }

    public List<Zone> getZonesList() {
        return zonesList;
    }

    public void setZonesList(List<Zone> zonesList) {
        this.zonesList = zonesList;
    }

    public String getAverageConversion() {
        return averageConversion;
    }

    public void setAverageConversion(String averageConversion) {
        this.averageConversion = averageConversion;
    }

    public String getCtr() {
        return ctr;
    }

    public void setCtr(String ctr) {
        this.ctr = ctr;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getVisits() {
        return visits;
    }

    public void setVisits(String visits) {
        this.visits = visits;
    }

    public String getConversions() {
        return conversions;
    }

    public void setConversions(String conversions) {
        this.conversions = conversions;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCustomVariable1() {
        return customVariable1;
    }

    public void setCustomVariable1(String customVariable1) {
        this.customVariable1 = customVariable1;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campaign campaign = (Campaign) o;
        return Objects.equals(campaignName, campaign.campaignName) &&
                Objects.equals(campaignId, campaign.campaignId) &&
                Objects.equals(visits, campaign.visits) &&
                Objects.equals(conversions, campaign.conversions) &&
                Objects.equals(cost, campaign.cost) &&
                Objects.equals(customVariable1, campaign.customVariable1) &&
                Objects.equals(profit, campaign.profit) &&
                Objects.equals(ctr, campaign.ctr) &&
                Objects.equals(revenue, campaign.revenue) &&
                Objects.equals(averageConversion, campaign.averageConversion) &&
                Objects.equals(zonesList, campaign.zonesList) &&
                Objects.equals(zonesListNew, campaign.zonesListNew) &&
                Objects.equals(summProfit, campaign.summProfit) &&
                Objects.equals(active, campaign.active);
    }

    @Override
    public int hashCode() {

        return Objects.hash(campaignName, campaignId, visits, conversions, cost, customVariable1, profit, ctr, revenue, averageConversion, zonesList, zonesListNew, summProfit, active);
    }
}
