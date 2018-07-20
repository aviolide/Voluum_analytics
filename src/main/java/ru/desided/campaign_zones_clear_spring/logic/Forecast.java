package ru.desided.campaign_zones_clear_spring.logic;

import org.springframework.beans.BeanUtils;
import ru.desided.campaign_zones_clear_spring.POJO.Campaign;
import ru.desided.campaign_zones_clear_spring.POJO.groups.Offer;
import sun.awt.image.BufferedImageGraphicsConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Forecast {

    public Campaign calc(Campaign campaign, BigDecimal costMulti, BigDecimal visitsMulti) {

        List<Offer> offerList = new ArrayList<>();

        for (Offer offer : campaign.getOffersList()) {

            BigDecimal visits = offer.getVisits();
            BigDecimal clicks = offer.getClicks();
            BigDecimal conversions = offer.getConversions();
            BigDecimal revenue = offer.getRevenue();
            BigDecimal cost = offer.getCost();
            BigDecimal profit = offer.getProfit();
            BigDecimal cpv = offer.getCpv();
            BigDecimal ctr = offer.getCtr();
            BigDecimal cr = offer.getCr();
            BigDecimal cv = offer.getCv();
            BigDecimal roi = offer.getRoi();
            BigDecimal epv = offer.getEpv();
            BigDecimal epc = offer.getEpc();
            BigDecimal costDay = offer.getCostDay();
            BigDecimal profitDay = offer.getProfitDay();
            BigDecimal visitsDay = offer.getVisitsDay();
            BigDecimal profitHour = offer.getProfitHour();

            BigDecimal newVisits = visits.multiply(visitsMulti).setScale(0, RoundingMode.HALF_UP);
            BigDecimal newClicks = clicks.multiply(visitsMulti).setScale(0, RoundingMode.HALF_UP);
            BigDecimal newConversions = conversions.multiply(visitsMulti).setScale(0, RoundingMode.HALF_UP);
            BigDecimal newRevenue = revenue.multiply(visitsMulti).setScale(1, RoundingMode.HALF_UP);
            BigDecimal newCost = cost.multiply(costMulti).setScale(1, RoundingMode.HALF_UP);
            BigDecimal newProfit = newRevenue.subtract(newCost).setScale(1, RoundingMode.HALF_UP);
            BigDecimal newCpv = newCost.divide(newVisits, 4, RoundingMode.HALF_UP);
            BigDecimal newCtr = new BigDecimal(100).multiply(newClicks).divide(newVisits, 2, RoundingMode.HALF_UP);

            BigDecimal newCr = newConversions.divide(newClicks, 4, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
            BigDecimal newCv = newConversions.divide(newVisits, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            BigDecimal newRoi = (newRevenue.subtract(newCost)).multiply(new BigDecimal(100)).divide(newCost, 2, RoundingMode.HALF_UP);
            BigDecimal newEpv = newRevenue.divide(newVisits, 4, RoundingMode.HALF_UP);
            BigDecimal newEpc = newRevenue.divide(newClicks, 2, RoundingMode.HALF_UP);
            BigDecimal newCostDay = costDay.multiply(costMulti).setScale(1, RoundingMode.HALF_UP);
            BigDecimal newProfitDay = profitDay.multiply(visitsMulti).setScale(1, RoundingMode.HALF_UP);
            BigDecimal newVisitsDay = visitsDay.multiply(visitsMulti).setScale(0, RoundingMode.HALF_UP);
            BigDecimal newProfitHour = profitHour.multiply(visitsMulti).setScale(2, RoundingMode.HALF_UP);

            offer.setVisits(newVisits);
            offer.setClicks(newClicks);
            offer.setConversions(newConversions);
            offer.setRevenue(newRevenue);
            offer.setCost(newCost);
            offer.setProfit(newProfit);
            offer.setCpv(newCpv);
            offer.setCtr(newCtr);
            offer.setCr(newCr);
            offer.setCv(newCv);
            offer.setRoi(newRoi);
            offer.setEpv(newEpv);
            offer.setEpc(newEpc);
            offer.setCostDay(newCostDay);
            offer.setProfitDay(newProfitDay);
            offer.setVisitsDay(newVisitsDay);
            offer.setProfitHour(newProfitHour);

            offerList.add(offer);
        }
        campaign.setOffersList(offerList);
        return campaign;
    }

}
