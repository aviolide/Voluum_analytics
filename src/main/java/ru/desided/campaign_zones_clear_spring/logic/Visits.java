package ru.desided.campaign_zones_clear_spring.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import ru.desided.campaign_zones_clear_spring.POJO.*;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Visits {

    public Campaign filterCampaign(AuthProp authProp, Campaign campaign, String DATE) throws IOException {

        if (authProp.getLogin().getMonth() != null || authProp.getLogin().getDays() != null){
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
            String dateNow = simpleDateFormat.format(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (authProp.getLogin().getMonth() != null) {
                calendar.add(Calendar.MONTH, -authProp.getLogin().getMonth());
            } else if(authProp.getLogin().getMonth() != null && authProp.getLogin().getDays() != null){
                calendar.add(Calendar.DAY_OF_MONTH, -authProp.getLogin().getDays());
                calendar.add(Calendar.MONTH, -authProp.getLogin().getMonth());
            } else {
                calendar.add(Calendar.DAY_OF_MONTH, -authProp.getLogin().getDays());
            }
            String dateBefore = simpleDateFormat.format(calendar.getTime());
            DATE = dateBefore + "&to=" + dateNow;
            System.out.println(DATE);
        }

        Login login = authProp.getLogin();
        StringBuffer url = new StringBuffer("https://panel-api.voluum.com/report?from=" + DATE + "&tz=America%2FNew_York&sort=visits&direction=desc&columns=customVariable1&columns=impressions&columns=visits&columns=clicks&columns=conversions&columns=revenue&columns=cost&columns=profit&columns=cpv&columns=ictr&columns=ctr&columns=cr&columns=cv&columns=roi&columns=epv&columns=epc&columns=ap&columns=errors&columns=rpm&columns=ecpm&columns=ecpc&columns=ecpa&groupBy=custom-variable-1&offset=0&limit=10000&include=ACTIVE&filter1=campaign&filter1Value=");
        url.append(campaign.getCampaignId());
        HttpGet get = new HttpGet(url.toString());
        String out = makeRequest(authProp.getHttpClient(), get);
        ObjectNode node = new ObjectMapper().readValue(out, Campaign.class).getTotals();

        campaign.setConversions(node.get("conversions").asText());
        campaign.setCtr(new BigDecimal(node.get("ctr").asText()).setScale(2, RoundingMode.HALF_UP).toString());
        campaign.setRevenue(node.get("revenue").asText());

        BigDecimal averageConversion;
        try {
            averageConversion = new BigDecimal(campaign.getRevenue()).divide(new BigDecimal(campaign.getConversions()), 2, BigDecimal.ROUND_HALF_UP);
        } catch (ArithmeticException e){
            averageConversion = new BigDecimal(0);
        }
        campaign.setAverageConversion(averageConversion.toString());

        List<StringBuffer> formulsForRequest = new ArrayList<>();
        System.out.println(login.getFormula());
        List<String> formulsList = Arrays.asList(login.getFormula().split(";"));
        for(String str : formulsList){

            System.out.println(str);
            StringBuffer stringBuffer = new StringBuffer();
            List<String> innerFormula = Arrays.asList(str.split("&"));
            int i = 2;

            for(String innerStr : innerFormula){
                stringBuffer.append(formulaBuilder(innerStr, i, campaign) + "&");
                i++;
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            formulsForRequest.add(stringBuffer);
        }


        Set<Zone> finalZonesList = new HashSet<>();
        for (StringBuffer formula : formulsForRequest){

            System.out.println(formula);
            url = new StringBuffer("https://panel-api.voluum.com/report?from=" + DATE+ "&tz=America%2FNew_York&sort=visits&direction=desc&columns=customVariable1&columns=impressions&columns=visits&columns=clicks&columns=conversions&columns=revenue&columns=cost&columns=profit&columns=cpv&columns=ictr&columns=ctr&columns=cr&columns=cv&columns=roi&columns=epv&columns=epc&columns=ap&columns=errors&columns=rpm&columns=ecpm&columns=ecpc&columns=ecpa&groupBy=custom-variable-1&offset=0&limit=10000&include=ACTIVE&filter1=campaign&filter1Value=");
            url.append(campaign.getCampaignId() + "&");
            url.append(formula);
            get = new HttpGet(url.toString());
            out = makeRequest(authProp.getHttpClient(), get);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper = new ObjectMapper();
            finalZonesList.addAll(objectMapper.readValue(out, ZonesList.class).getRows());

        }

        List<Zone> zoneList = finalZonesList.stream()
                .distinct()
                .collect(Collectors.toList());
        Collections.sort(zoneList, Comparator.comparing(v -> v.getProfit()));

        BigDecimal summProfit = new BigDecimal(0);
        for(Zone zone : zoneList){
            if (zone.getCustomVariable1().equals("")){
                zoneList.remove("");
                continue;
            }
            System.out.println(zone.getCustomVariable1() + "   " + zone.getProfit());
            summProfit = summProfit.add(zone.getProfit());
        }
        System.out.println("all " + summProfit + " size " + zoneList.size());
        campaign.setZonesList(zoneList);
        campaign.setSummProfit(summProfit.toString());
        return campaign;
    }

    public CampaignsList getAllDataToday(CampaignsList campaignsListInput, AuthProp authProp, Login login) throws IOException {

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

        CampaignsList campaignsListNew = new CampaignsList();
        List<Campaign> campaignList = new ArrayList<>();
        for (Campaign campaign : campaignsListInput.getRows()){
            campaignList.add(filterCampaign(authProp, campaign, DATE));
        }
        campaignsListNew.setRows(campaignList);
        return campaignsListNew;
    }

    public CampaignsList getDataActive(CampaignsList campaignsListInput, AuthProp authProp, Login login) throws IOException {

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

        CampaignsList campaignsListNew = new CampaignsList();
        List<Campaign> campaignList = new ArrayList<>();
        for (Campaign campaign : campaignsListInput.getRows()){
            campaignList.add(filterCampaign(authProp, campaign, DATE));
        }
        campaignsListNew.setRows(campaignList);
        return campaignsListNew;
    }

    public CampaignsList getDataByMonth(CampaignsList campaignsListInput, AuthProp authProp, Login login) throws IOException {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String dateNow = simpleDateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -authProp.getLogin().getMonth());
        String dateBefore = simpleDateFormat.format(calendar.getTime());
        String DATE = dateBefore + "&to=" + dateNow;
        System.out.println(DATE);

        CampaignsList campaignsListNew = new CampaignsList();
        List<Campaign> campaignList = new ArrayList<>();
        for (Campaign campaign : campaignsListInput.getRows()){
            campaignList.add(filterCampaign(authProp, campaign, DATE));
        }
        campaignsListNew.setRows(campaignList);
        return campaignsListNew;
    }

    public CampaignsList getDataAll(CampaignsList campaignsListInput, AuthProp authProp, Login login) throws IOException {
        Date date = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        simpleDate.setTimeZone(TimeZone.getTimeZone("EST"));
//        System.out.println(simpleDate.format(date));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        System.out.println(simpleDate.format(calendar.getTime()));
//        DATE = simpleDate.format(calendar.getTime()) + "&to=" + simpleDate.format(date);
        String DATE = "2017-01-01&to=" + simpleDate.format(date);
        System.out.println(DATE);

        CampaignsList campaignsListNew = new CampaignsList();
        List<Campaign> campaignList = new ArrayList<>();
        for (Campaign campaign : campaignsListInput.getRows()){
            campaignList.add(filterCampaign(authProp, campaign, DATE));
        }
        campaignsListNew.setRows(campaignList);
        return campaignsListNew;
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

    private String formulaBuilder(String string, int i, Campaign campaign){

        String filter = null;
        String filterValue = null;
        String filterRelation = null;

        Pattern pattern = Pattern.compile("[<>]");
        Matcher matcher =pattern.matcher(string);
        if (matcher.find()){
            filterRelation = matcher.group().contains(">") ? "GREATER" : "LESS";
        }

        pattern = Pattern.compile("ctr|roi|visits|cost|conversions|clicks|revenue|profit|cpv|cr|cv|epv|epc|ecpc|ecpm|ecpa");
        matcher = pattern.matcher(string);
        if (matcher.find()){
            filter = matcher.group();
        }
//        "ctr<2&roi<10&visits>100;ctr>2&roi<10&visits>100;cost>2.5&roi<10&visits>100"
        pattern = Pattern.compile("-?[0-9]*\\.?[0-9]+");
        matcher = pattern.matcher(string);
        if (matcher.find()){

            if (filter.equals("ctr") && filterRelation.equals("LESS")) {
                BigDecimal ctr = new BigDecimal(campaign.getCtr()).divide(new BigDecimal(matcher.group()), 2, BigDecimal.ROUND_HALF_UP);
                filterValue = ctr.toString();
            } else if (filter.equals("ctr") && filterRelation.equals("GREATER")){
                BigDecimal ctr = new BigDecimal(campaign.getCtr()).multiply(new BigDecimal(matcher.group())).setScale(2, RoundingMode.HALF_UP);
                filterValue = ctr.toString();
            } else if (filter.equals("cost") && filterRelation.equals("LESS")){
                BigDecimal cost = new BigDecimal(campaign.getAverageConversion()).divide(new BigDecimal(matcher.group()), 2, RoundingMode.HALF_UP);
                filterValue = cost.toString();
            } else if (filter.equals("cost") && filterRelation.equals("GREATER")){
                BigDecimal cost = new BigDecimal(campaign.getAverageConversion()).multiply(new BigDecimal(matcher.group())).setScale(2, RoundingMode.HALF_UP);
                filterValue = cost.toString();
            } else {
                filterValue = matcher.group();
            }


        }

        String out = String.format("filter%d=%s&filter%dValue=%s&filter%dRelation=%s",
                i, filter, i, filterValue, i, filterRelation);
        return out;
    }
}
