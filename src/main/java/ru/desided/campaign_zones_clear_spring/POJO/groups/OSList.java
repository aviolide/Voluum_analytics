package ru.desided.campaign_zones_clear_spring.POJO.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OSList {
    private List<CommonObject> rows;

    public List<CommonObject> getRows() {
        return rows;
    }

    public void setRows(List<CommonObject> rows) {
        this.rows = rows;
    }
}
