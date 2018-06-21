package ru.desided.campaign_zones_clear_spring.logic;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import ru.desided.campaign_zones_clear_spring.POJO.AuthProp;
import ru.desided.campaign_zones_clear_spring.POJO.Campaign;
import ru.desided.campaign_zones_clear_spring.POJO.CampaignsList;
import ru.desided.campaign_zones_clear_spring.POJO.Zone;

import java.io.IOException;
import java.util.*;

public class CampareCampaigns {

    public CampaignsList campareCampaigns(CampaignsList campaignsListMain, AuthProp authProp) throws IOException {

        for (Campaign campaignMain : campaignsListMain.getRows()){

            Visits visits = new Visits();
            Campaign campaignTemp = SerializationUtils.clone(campaignMain);
            visits.filterCampaign(authProp, campaignMain, authProp.getDATE());
            List<Zone> tempList = new ArrayList<>();
            tempList.addAll(campaignMain.getZonesList());
            if (campaignTemp.getZonesList() != null) {
                campaignMain.getZonesList().removeAll(campaignTemp.getZonesList());
                campaignMain.setZonesListNew(campaignMain.getZonesList());
                campaignMain.setZonesList(tempList);
            } else if (campaignTemp.getZonesList() == null){
                campaignMain.setZonesListNew(tempList);

            }
        }

        return campaignsListMain;
    }

    public List<Campaign> getDataFromList(String string){
        Campaign campaign = new Campaign();
        List<Campaign> campaignList = new ArrayList<>();
        List<String> stringList = Arrays.asList(string.split("\\r\\n"));
        List<Zone> zoneList = new ArrayList<>();

        for (String stringtext : stringList){

            if (stringtext.length() > 10){
                campaign = new Campaign();
                zoneList = new ArrayList<>();

                campaign.setCampaignId(stringtext);
                campaignList.add(campaign);
            } else {
                Zone zone = new Zone();
                zone.setCustomVariable1(stringtext);
                zoneList.add(zone);
                campaign.setZonesList(zoneList);
            }
        }
        return campaignList;
    }

}
