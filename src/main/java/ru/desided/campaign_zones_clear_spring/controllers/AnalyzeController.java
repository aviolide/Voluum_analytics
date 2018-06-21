package ru.desided.campaign_zones_clear_spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.desided.campaign_zones_clear_spring.POJO.AuthProp;
import ru.desided.campaign_zones_clear_spring.POJO.Campaign;
import ru.desided.campaign_zones_clear_spring.POJO.CampaignsList;
import ru.desided.campaign_zones_clear_spring.POJO.groups.Offer;
import ru.desided.campaign_zones_clear_spring.logic.AnalyzeCampaigns;
import ru.desided.campaign_zones_clear_spring.logic.Forecast;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/analyze")
public class AnalyzeController {

    private AuthProp authProp;
    private CampaignsList campaignsListMain = new CampaignsList();
    private Boolean offerOs;
    private Boolean offerBrowser;
    private Boolean offerOsBrowser;

    @RequestMapping(value = ("/analyzator"), method = RequestMethod.GET)
    public ModelAndView analyzator(){

        this.campaignsListMain = LoginController.getCampaignsListMain();
        this.authProp = LoginController.getAuthProp();


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("campaignList", campaignsListMain);
        modelAndView.setViewName("analyze/analyzator");
        return modelAndView;
    }
    @RequestMapping(value = ("/analyzator/set_group"), method = RequestMethod.GET)
    public void set_group(@RequestParam(value = "offeros") Boolean offerOs,
                          @RequestParam(value = "offerbrowser") Boolean offerBrowser,
                          @RequestParam(value = "offerosbrowser") Boolean offerOsBrowser) {
        System.out.println(offerOs);
        System.out.println(offerBrowser);
        System.out.println(offerOsBrowser);
        this.offerOs = offerOs;
        this.offerBrowser = offerBrowser;
        this.offerOsBrowser = offerOsBrowser;
    }

    @RequestMapping(value = ("/analyzator/{id}"), method = RequestMethod.GET)
    public ModelAndView analyze_id(@PathVariable("id") int id) throws IOException {
        List<Campaign> campaigns = campaignsListMain.getRows();

        Campaign campaign = campaigns.get(id);
        campaign.setCheckOfferOS(offerOs);
        campaign.setCheckOfferBrowser(offerBrowser);
        campaign.setCheckOfferOsBrowser(offerOsBrowser);
        AnalyzeCampaigns analyzeCampaigns = new AnalyzeCampaigns(authProp, campaign);
        analyzeCampaigns.startAnalyzeID();

        String json = new ObjectMapper().writeValueAsString(campaign);
        String json2 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json);

        System.out.println(json2);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("json", json2);
        modelAndView.setViewName("analyze/table");
        return modelAndView;
    }

    @RequestMapping(value = ("analyzator/table"), method = RequestMethod.GET)
    public ModelAndView table(){
        return new ModelAndView("analyze/table");
    }


    @RequestMapping(value = "/analyzator/calculate", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Campaign campaign(@RequestParam("forecastcost") String forecastcost,
                      @RequestParam("forecastvisits") String forecastvisits,
                      @RequestParam("campaign_id") int id){
        System.out.println(forecastcost);
        System.out.println(forecastvisits);
        System.out.println(id);

        Campaign campaign = SerializationUtils.clone(campaignsListMain.getRows().get(id));
        Forecast forecast = new Forecast();
        forecast.calc(campaign, new BigDecimal(forecastcost), new BigDecimal(forecastvisits));

//        List<Offer> offers = new ArrayList<>();
//        Offer offer = new Offer();
//        campaign.setCampaignId("123123123123");
//        offer.setProfitHour(new BigDecimal(123123));
//        offer.setProfit(new BigDecimal(123123));
//        offer.setVisitsDay(new BigDecimal(123123));
//        offer.setCostDay(new BigDecimal(123123));
//        offer.setCost(new BigDecimal(123123));
//        offer.setOfferName("objee");
//        offers.add(offer);
//        campaign.setOffersList(offers);
        return campaign;
    }
}
