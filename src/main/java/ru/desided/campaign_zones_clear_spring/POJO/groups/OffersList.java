package ru.desided.campaign_zones_clear_spring.POJO.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OffersList {
    private List<Offer> rows;

    public List<Offer> getRows() {
        return rows;
    }

    public void setRows(List<Offer> rows) {
        this.rows = rows;
    }
}
