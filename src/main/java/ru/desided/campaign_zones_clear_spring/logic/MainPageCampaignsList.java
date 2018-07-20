package ru.desided.campaign_zones_clear_spring.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import ru.desided.campaign_zones_clear_spring.POJO.AuthProp;
import ru.desided.campaign_zones_clear_spring.POJO.Campaign;
import ru.desided.campaign_zones_clear_spring.POJO.CampaignsList;
import ru.desided.campaign_zones_clear_spring.POJO.Login;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainPageCampaignsList {

    public List<Campaign> allCampaigns(AuthProp authProp) throws IOException {

        HttpClient httpClient = authProp.getHttpClient();
        HttpGet get = new HttpGet("https://panel-api.voluum.com/report?from=" + authProp.getDATE() + "&tz=America%2FNew_York&sort=visits&direction=desc&columns=campaignName&columns=campaignId&columns=internalBids&columns=bids&columns=impressions&columns=winRate&columns=visits&columns=clicks&columns=conversions&columns=revenue&columns=cost&columns=profit&columns=cpv&columns=ictr&columns=ctr&columns=cr&columns=cv&columns=roi&columns=epv&columns=epc&columns=ap&columns=errors&columns=rpm&columns=ecpm&columns=ecpc&columns=ecpa&groupBy=campaign&offset=0&limit=1000&include=ACTIVE&conversionTimeMode=VISIT&filter1=clicks&filter1Value=1000&filter1Relation=GREATER");
        ObjectMapper objectMapper = new ObjectMapper();
        String request = makeRequest(httpClient, get);
        List<Campaign> campaignsLists = objectMapper.readValue(request, CampaignsList.class).getRows();

        for (Campaign campaign : campaignsLists){
            System.out.println(campaign.getCampaignId());
            System.out.println(campaign.getCampaignName());
            campaign.setActive(false);
            BigDecimal averageConversion;
            try {
                averageConversion = new BigDecimal(campaign.getRevenue()).divide(new BigDecimal(campaign.getConversions()), 2, BigDecimal.ROUND_HALF_UP);
            } catch (ArithmeticException e){
                averageConversion = new BigDecimal(0);
            }

            campaign.setAverageConversion(averageConversion.toString());
            BigDecimal ctr = new BigDecimal(campaign.getCtr()).setScale(2, RoundingMode.HALF_UP);
            campaign.setCtr(ctr.toString());

            System.out.println(averageConversion);
            System.out.println(campaign.getCtr());
//            filterCampaign(httpClient, campaign, login);
            //out in html
        }
        return campaignsLists;

    }

    public List<Campaign> startActiveCampaignsList(AuthProp authProp) throws IOException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:00:00'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String dateNow = simpleDateFormat.format(calendar.getTime());
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 1);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String dateTommorow = simpleDateFormat.format(calendar.getTime());
        String DATE = dateNow + "&to=" + dateTommorow;
        System.out.println(DATE);
        return campaignList(authProp, DATE);
    }

    public List<Campaign> startTodayCampaignsList(AuthProp authProp) throws IOException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String dateNow = simpleDateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String dateTommorow = simpleDateFormat.format(calendar.getTime());
        String DATE = dateNow + "&to=" + dateTommorow;
        System.out.println(DATE);
        return campaignList(authProp, DATE);
    }

    public List<Campaign> campaignList(AuthProp authProp, String DATE) throws IOException {

        HttpClient httpClient = authProp.getHttpClient();
        HttpGet get = new HttpGet("https://panel-api.voluum.com/report?from=" + DATE + "&tz=America%2FNew_York&sort=visits&direction=desc&columns=campaignName&columns=campaignId&columns=totalBudget&columns=visits&columns=clicks&columns=conversions&columns=revenue&columns=cost&columns=profit&columns=cpv&columns=ictr&columns=ctr&columns=cr&columns=cv&columns=roi&columns=epv&columns=epc&columns=ap&columns=errors&columns=rpm&columns=ecpm&columns=ecpc&columns=ecpa&groupBy=campaign&offset=0&limit=100&include=ALL&conversionTimeMode=VISIT&filter1=visits&filter1Value=100&filter1Relation=GREATER");
        ObjectMapper objectMapper = new ObjectMapper();
        String request = makeRequest(httpClient, get);
        List<Campaign> campaignsLists = objectMapper.readValue(request, CampaignsList.class).getRows();

        for (Campaign campaign : campaignsLists){
            System.out.println(campaign.getCampaignId());
            System.out.println(campaign.getCampaignName());
            campaign.setActive(true);
            BigDecimal averageConversion;
            try {
                averageConversion = new BigDecimal(campaign.getRevenue()).divide(new BigDecimal(campaign.getConversions()), 2, BigDecimal.ROUND_HALF_UP);
            } catch (ArithmeticException e){
                averageConversion = new BigDecimal(0);
            }

            campaign.setAverageConversion(averageConversion.toString());
            BigDecimal ctr = new BigDecimal(campaign.getCtr()).setScale(2, RoundingMode.HALF_UP);
            campaign.setCtr(ctr.toString());

            System.out.println(averageConversion);
            System.out.println(campaign.getCtr());
//            filterCampaign(httpClient, campaign, login);
            //out in html
        }
        return campaignsLists;
    }


    private String makeRequest(HttpClient httpClient, HttpRequestBase requestBase) throws IOException {
        ResponseHandler<String> responseHandler = response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                return null;
            }
        };
        String responseBody = httpClient.execute(requestBase, responseHandler);
        if (responseBody.contains("NOT_UNIQUE")){
            throw new IllegalStateException("name exist");
        }
        return responseBody;
    }
}
