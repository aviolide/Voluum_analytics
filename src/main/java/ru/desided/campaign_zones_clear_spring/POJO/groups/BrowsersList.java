package ru.desided.campaign_zones_clear_spring.POJO.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrowsersList {
    private List<CommonObject> rows;

    public List<CommonObject> getRows() {
        return rows;
    }

    public void setRows(List<CommonObject> rows) {
        this.rows = rows;
    }
}
