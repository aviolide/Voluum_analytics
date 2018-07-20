package ru.desided.campaign_zones_clear_spring.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignsList {

    private List<Campaign> rows;

    public List<Campaign> getRows() {
        return rows;
    }

    public void setRows(List<Campaign> rows) {
        this.rows = rows;
    }
}
