package ru.desided.campaign_zones_clear_spring.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZonesList {
    private List<Zone> rows;

    public List<Zone> getRows() {
        return rows;
    }

    public void setRows(List<Zone> rows) {
        this.rows = rows;
    }
}
