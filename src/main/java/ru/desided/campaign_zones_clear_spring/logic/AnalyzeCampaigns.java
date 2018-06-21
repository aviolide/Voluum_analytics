package ru.desided.campaign_zones_clear_spring.logic;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import ru.desided.campaign_zones_clear_spring.POJO.groups.*;
import ru.desided.campaign_zones_clear_spring.logic.CustomsDeserializers.CustomDeserializer;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyzeCampaigns {

    private Login login;
    private String DATE;
    private HttpClient httpClient;
    private Campaign campaign;
    private List<Offer> tempOfferList = new ArrayList<>();

    public AnalyzeCampaigns(AuthProp authProp, Campaign campaign){
        this.login = authProp.getLogin();
//        this.DATE = authProp.getDATE();
        this.httpClient = authProp.getHttpClient();
        this.campaign = campaign;

    }
//
//    public void analyzeAll(){
//
//    }

    public void startAnalyzeID() throws IOException {

        if (campaign.getCheckOfferOS()){
            setDateYear();
            getDateStartCampaign();
            analyzeOfferOs();
        }
        if (campaign.getCheckOfferBrowser()){
            setDateYear();
            getDateStartCampaign();
            analyzeOfferBrowser();
        }
        if (campaign.getCheckOfferOsBrowser()){
            setDataMonth();
            analyzeOfferOsBrowser();
        }
        campaign.setOffersList(tempOfferList);
        new ObjectMapper().writeValue(new File("files/out"), campaign);
    }

    private void analyzeOfferBrowser() throws IOException {
        String out = groupOffer();
        OffersList offersList = new ObjectMapper().readValue(out, OffersList.class);
        List<Offer> arrayOffer = new ArrayList<>();
        campaign.setOffersList(offersList.getRows());

        for (int i = 0; i < offersList.getRows().size(); i++) {
            if (offersList.getRows().get(i).getVisits().compareTo(new BigDecimal(1000)) == 1) {
                out = groupBrowser(i);
                BrowsersList browsersList = new ObjectMapper().readValue(out, BrowsersList.class);

                for (int x = 0; x < browsersList.getRows().size(); x++) {
                    if (browsersList.getRows().get(x).getVisits().compareTo(new BigDecimal(1000)) == 1 &&
                            browsersList.getRows().get(x).getClicks().compareTo(new BigDecimal(50)) == 1) {

                        JsonNode node = new ObjectMapper().valueToTree(browsersList.getRows().get(x));
                        JsonParser parser = new ObjectMapper().treeAsTokens(node);
                        Offer offer = new ObjectMapper().readValue(parser, Offer.class);
                        offer.setOfferName(offersList.getRows().get(i).getOfferName());
                        offer.setBrowser(browsersList.getRows().get(x).getBrowser());
                        offer.setOfferId(offersList.getRows().get(i).getOfferId());
                        offer = calculateData(campaign.getDiffDateDays(), offer);
                        offer.setGroup1("Offer");
                        offer.setGroup2("Browser");
                        arrayOffer.add(offer);
                    }
                }
            }
        }
        List<Offer> finalList = new ArrayList<>(arrayOffer);
        tempOfferList.addAll(finalList);
        new ObjectMapper().writeValue(new File("files/out"), campaign);
    }

    private void analyzeOfferOs() throws IOException {
        String out = groupOffer();
        OffersList offersList = new ObjectMapper().readValue(out, OffersList.class);
        List<Offer> arrayOffer = new ArrayList<>();
        campaign.setOffersList(offersList.getRows());
//
        for (int i = 0; i < offersList.getRows().size(); i++) {
            if (offersList.getRows().get(i).getVisits().compareTo(new BigDecimal(1000)) == 1) {
                out = groupOS(i);
                OSList osList = new ObjectMapper().readValue(out, OSList.class);

                for (int x = 0; x < osList.getRows().size(); x++) {
                    if (osList.getRows().get(x).getVisits().compareTo(new BigDecimal(1000)) == 1 &&
                            osList.getRows().get(x).getClicks().compareTo(new BigDecimal(50)) == 1) {

                        JsonNode node = new ObjectMapper().valueToTree(osList.getRows().get(x));
                        JsonParser parser = new ObjectMapper().treeAsTokens(node);
                        Offer offer = new ObjectMapper().readValue(parser, Offer.class);
                        offer.setOfferName(offersList.getRows().get(i).getOfferName());
                        offer.setOfferId(offersList.getRows().get(i).getOfferId());
                        offer.setOs(osList.getRows().get(x).getOs());
                        offer.setGroup1("Offer");
                        offer.setGroup2("OS");
                        offer = calculateData(campaign.getDiffDateDays(), offer);
                        arrayOffer.add(offer);
                    }
                }
            }
        }
        List<Offer> finalList = new ArrayList<>(arrayOffer);
        tempOfferList.addAll(finalList);
        new ObjectMapper().writeValue(new File("files/out"), campaign);
    }

    private void analyzeOfferOsBrowser() throws IOException {
        String out = groupOffer();
        OffersList offersList = new ObjectMapper().readValue(out, OffersList.class);
        List<Offer> arrayOffer = new ArrayList<>();
//        campaign.setOffersList(offersList.getRows());

        for (int i = 0; i < offersList.getRows().size(); i++){

            if (offersList.getRows().get(i).getVisits().compareTo(new BigDecimal(1000)) == 1) {
                out = groupOS(i);
                OSList osList = new ObjectMapper().readValue(out, OSList.class);

                for (int x = 0; x < osList.getRows().size(); x++) {

                    if (osList.getRows().get(x).getVisits().compareTo(new BigDecimal(1000)) == 1 &&
                            osList.getRows().get(x).getClicks().compareTo(new BigDecimal(50)) == 1) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        SimpleModule module = new SimpleModule();
                        module.addDeserializer(BrowsersList.class, new CustomDeserializer());
                        objectMapper.registerModule(module);

                        out = groupOSandBrowser(i, osList.getRows().get(x).getOs());
                        BrowsersList browsersList = objectMapper.readValue(out, BrowsersList.class);

                        for (int b = 0; b < browsersList.getRows().size(); b++) {

                            JsonNode node = new ObjectMapper().valueToTree(browsersList.getRows().get(b));
                            JsonParser parser = new ObjectMapper().treeAsTokens(node);
                            Offer offer = new ObjectMapper().readValue(parser, Offer.class);
                            offer.setOfferName(offersList.getRows().get(i).getOfferName());
                            offer.setOs(osList.getRows().get(x).getOs());
                            offer.setBrowser(browsersList.getRows().get(b).getBrowser());
                            offer.setOfferId(offersList.getRows().get(i).getOfferId());
                            offer = calculateData(new BigDecimal(29), offer);
                            offer.setGroup1("Offer");
                            offer.setGroup2("OS");
                            offer.setGroup3("Browser");
                            arrayOffer.add(offer);
                        }
                    }
                }
            }
        }
        List<Offer> finalList = new ArrayList<>(arrayOffer);
        tempOfferList.addAll(finalList);
        new ObjectMapper().writeValue(new File("files/out"), campaign);
    }

    private Offer calculateData(BigDecimal days, Offer offer){
        BigDecimal revenueDay = offer.getRevenue().divide(days, 2, RoundingMode.HALF_UP);
        BigDecimal costDay = offer.getCost().divide(days, 2, RoundingMode.HALF_UP);
        BigDecimal profitDay = offer.getProfit().divide(days, 2, RoundingMode.HALF_UP);
        BigDecimal visitsDay = offer.getVisits().divide(days, 0, RoundingMode.HALF_UP);
        BigDecimal profitHour = offer.getProfit().divide(days.multiply(new BigDecimal(24)), 2, RoundingMode.HALF_UP);
        offer.setRevenueDay(revenueDay);
        offer.setCostDay(costDay);
        offer.setProfitDay(profitDay);
        offer.setVisitsDay(visitsDay);
        offer.setProfitHour(profitHour);
        return offer;
    }
    private void getDateStartCampaign() throws IOException {
        String out = groupDay();
        CommonList commonList = new ObjectMapper().readValue(out, CommonList.class);

        List<CommonObject> sortList = commonList.getRows().stream()
                .filter(o -> o.getClicks().compareTo(new BigDecimal("50")) == 1)
                .sorted(Comparator.comparing(CommonObject::getDay))
                .collect(Collectors.toList());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        campaign.setStartDate(simpleDateFormat.format(sortList.get(0).getDay()));
        campaign.setEndDate(simpleDateFormat.format(sortList.get(sortList.size() - 1).getDay()));
        campaign.setDiffDateDays(new BigDecimal(sortList.size()));

    }

    private String groupOffer() throws IOException {
        StringBuffer url = new StringBuffer("https://panel-api.voluum.com/report?from=" + DATE + "&tz=America%2FNew_York&sort=visits&direction=desc&columns=offerName&columns=os&columns=browser&columns=offerWorkspaceName&columns=visits&columns=clicks&columns=conversions&columns=revenue&columns=cost&columns=profit&columns=cpv&columns=ictr&columns=ctr&columns=cr&columns=cv&columns=roi&columns=epv&columns=epc&columns=ap&columns=errors&columns=rpm&columns=ecpm&columns=ecpc&columns=ecpa&groupBy=offer&offset=0&limit=500&include=ACTIVE&conversionTimeMode=VISIT&filter1=campaign&filter1Value=");
        url.append(campaign.getCampaignId());
        HttpGet get = new HttpGet(url.toString());
        return makeRequest(httpClient, get);
    }

    private String groupOS(int i) throws IOException {
        StringBuffer url = new StringBuffer("https://panel-api.voluum.com/report?from=" + DATE + "&tz=America%2FNew_York&sort=visits&direction=desc&columns=offerName&columns=os&columns=browser&columns=offerWorkspaceName&columns=visits&columns=clicks&columns=conversions&columns=revenue&columns=cost&columns=profit&columns=cpv&columns=ictr&columns=ctr&columns=cr&columns=cv&columns=roi&columns=epv&columns=epc&columns=ap&columns=errors&columns=rpm&columns=ecpm&columns=ecpc&columns=ecpa&groupBy=os&limit=500&include=ACTIVE&conversionTimeMode=VISIT&filter1=campaign&filter1Value=" + campaign.getCampaignId() + "&filter2=offer&filter2Value=");
        url.append(campaign.getOffersList().get(i).getOfferId());
        HttpGet get = new HttpGet(url.toString());
        return makeRequest(httpClient, get);
    }

    private String groupBrowser(int i) throws IOException {
        StringBuffer url = new StringBuffer("https://panel-api.voluum.com/report?from=" + DATE + "&tz=America%2FNew_York&sort=visits&direction=desc&columns=offerName&columns=os&columns=browser&columns=offerWorkspaceName&columns=visits&columns=clicks&columns=conversions&columns=revenue&columns=cost&columns=profit&columns=cpv&columns=ictr&columns=ctr&columns=cr&columns=cv&columns=roi&columns=epv&columns=epc&columns=ap&columns=errors&columns=rpm&columns=ecpm&columns=ecpc&columns=ecpa&groupBy=browser&limit=500&include=ACTIVE&conversionTimeMode=VISIT&filter1=campaign&filter1Value=" + campaign.getCampaignId() + "&filter2=offer&filter2Value=");
        url.append(campaign.getOffersList().get(i).getOfferId());
        HttpGet get = new HttpGet(url.toString());
        return makeRequest(httpClient, get);
    }

    private String groupOSandBrowser(int x, String os) throws IOException {

        os = os.equalsIgnoreCase("OS X") ? "OS+X" : os;
        StringBuffer url = new StringBuffer("https://panel-api.voluum.com/report?from=" + DATE + "&tz=America%2FNew_York&sort=visits&direction=desc&columns=offerName&columns=os&columns=browser&columns=offerWorkspaceName&columns=visits&columns=clicks&columns=conversions&columns=revenue&columns=cost&columns=profit&columns=cpv&columns=ictr&columns=ctr&columns=cr&columns=cv&columns=roi&columns=epv&columns=epc&columns=ap&columns=errors&columns=rpm&columns=ecpm&columns=ecpc&columns=ecpa&groupBy=browser&limit=500&include=ACTIVE&conversionTimeMode=VISIT&filter1=campaign&filter1Value=" + campaign.getCampaignId() + "&filter2=os&filter2Value=" + os + "&filter3=offer&filter3Value=");
        url.append(campaign.getOffersList().get(x).getOfferId());
        HttpGet get = new HttpGet(url.toString());
        return makeRequest(httpClient, get);
    }

    private String groupDay() throws IOException {
        StringBuffer url = new StringBuffer("https://panel-api.voluum.com/report?from=" + DATE + "&tz=America%2FNew_York&sort=day&direction=desc&columns=day&columns=visits&columns=clicks&columns=conversions&columns=revenue&columns=cost&columns=profit&columns=cpv&columns=ictr&columns=ctr&columns=cr&columns=cv&columns=roi&columns=epv&columns=epc&columns=ap&columns=errors&columns=rpm&columns=ecpm&columns=ecpc&columns=ecpa&groupBy=day&offset=0&limit=1000&include=ACTIVE&conversionTimeMode=VISIT&filter1=campaign&filter1Value=");
        url.append(campaign.getCampaignId());
        HttpGet get = new HttpGet(url.toString());
        return makeRequest(httpClient, get);
    }

    private String setDataMonth(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String dateNow = simpleDateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -29);
        String dateBefore = simpleDateFormat.format(calendar.getTime());
        return this.DATE = dateBefore + "&to=" + dateNow;
    }

    private String setDateYear(){
        Date date = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        simpleDate.setTimeZone(TimeZone.getTimeZone("EST"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return this.DATE = "2017-01-01&to=" + simpleDate.format(calendar.getTime());
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

    public class CustomSerializer extends JsonSerializer<CommonObject> {

        @Override
        public void serialize(CommonObject os, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (os.getClicks().compareTo(new BigDecimal(0)) == 1) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("os", os.getOs());
                jsonGenerator.writeNumber(os.getClicks());
            }
        }
    }
}
