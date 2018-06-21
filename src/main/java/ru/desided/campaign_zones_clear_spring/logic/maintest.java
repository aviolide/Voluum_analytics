package ru.desided.campaign_zones_clear_spring.logic;

import ru.desided.campaign_zones_clear_spring.POJO.AuthProp;
import ru.desided.campaign_zones_clear_spring.POJO.Campaign;
import ru.desided.campaign_zones_clear_spring.POJO.Login;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class maintest {
    public static void main(String[] args) throws IOException {
        Auth auth = new Auth();
        Login login = new Login();
        AuthProp authProp = new AuthProp();
        login.setApiKey("");
        login.setApiId("");
        authProp = auth.connect(login);
        Campaign campaign = new Campaign();
        campaign.setCampaignId("");
        campaign.setCheckOfferBrowser(true);
        campaign.setCheckOfferOS(false);
        campaign.setCheckOfferOsBrowser(false);

        AnalyzeCampaigns analyzeCampaigns = new AnalyzeCampaigns(authProp, campaign);
        analyzeCampaigns.startAnalyzeID();
//        start();

//        calc(new BigDecimal(2), new BigDecimal(1.9));
    }




    public static void start(){
        //cost day; rev day; profit day; visit day; profit hour
        BigDecimal revenue = new BigDecimal("1592");
        BigDecimal cost = new BigDecimal("737");
        BigDecimal profit = new BigDecimal("858");
        BigDecimal visits = new BigDecimal("438448");

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));

        String stringDateNow = simpleDateFormat.format(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -29);
        Date dateBefore = calendar.getTime();
        String stringDateBefore = simpleDateFormat.format(calendar.getTime());
        String DATE = dateBefore + "&to=" + stringDateNow;

        long diff=  date.getTime() - dateBefore.getTime();
        long hours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        BigDecimal revenueDay = revenue.divide(new BigDecimal(days), 2, RoundingMode.HALF_UP);
        BigDecimal costDay = cost.divide(new BigDecimal(days), 2, RoundingMode.HALF_UP);
        BigDecimal profitDay = profit.divide(new BigDecimal(days), 2, RoundingMode.HALF_UP);
        BigDecimal visitsDay = visits.divide(new BigDecimal(days), 2, RoundingMode.HALF_UP);
        BigDecimal profitHour = profit.divide(new BigDecimal(24), 2, RoundingMode.HALF_UP);

        System.out.println(revenueDay);
        System.out.println(costDay);
        System.out.println(profitDay);
        System.out.println(visitsDay);
        System.out.println(profitHour);
    }

}
