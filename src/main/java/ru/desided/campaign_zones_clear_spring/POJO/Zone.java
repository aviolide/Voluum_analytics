package ru.desided.campaign_zones_clear_spring.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zone implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String customVariable1;
    private String visits;
    private BigDecimal profit;

    public String getCustomVariable1() {
        return customVariable1;
    }

    public void setCustomVariable1(String customVariable1) {
        this.customVariable1 = customVariable1;
    }

    public String getVisits() {
        return visits;
    }

    public void setVisits(String visits) {
        this.visits = visits;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return Objects.equals(customVariable1, zone.customVariable1) ||
                Objects.equals(visits, zone.visits) ||
                Objects.equals(profit, zone.profit);
    }

    @Override
    public int hashCode() {

        return Objects.hash(customVariable1, visits, profit);
    }
}
