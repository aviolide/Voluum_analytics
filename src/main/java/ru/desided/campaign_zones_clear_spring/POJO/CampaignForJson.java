package ru.desided.campaign_zones_clear_spring.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.desided.campaign_zones_clear_spring.POJO.groups.Offer;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignForJson {

    private String campaignId;
    private List<Offer> offersList;

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public List<Offer> getOffersList() {
        return offersList;
    }

    public void setOffersList(List<Offer> offersList) {
        this.offersList = offersList;
    }
}
