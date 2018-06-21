package ru.desided.campaign_zones_clear_spring.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.desided.campaign_zones_clear_spring.POJO.*;
import ru.desided.campaign_zones_clear_spring.POJO.groups.Offer;
import ru.desided.campaign_zones_clear_spring.logic.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class LoginController {

    private static CampaignsList campaignsListMain;
    private CampaignsList campaignsListToday;
    private CampaignsList campaignsListTemp;
    private CampaignsList campaignsListActive;
    private CampaignsList campaignsListOld;
    private static AuthProp authProp;

    public static CampaignsList getCampaignsListMain() {
        return campaignsListMain;
    }

    public static AuthProp getAuthProp() {
        return authProp;
    }

    @RequestMapping(value=("/"), method = RequestMethod.GET)
    public ModelAndView index() throws IOException {

        return new ModelAndView("login", "command", new Login());
    }

    @RequestMapping(value=("/allcampaigns"), method = RequestMethod.POST)
    public ModelAndView start(@Valid @ModelAttribute("allcampaigns") Login login, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()){
            return new ModelAndView("index", "command", new Login());
        }
        System.out.println(login.getFormula());
        System.out.println(login.getApiId());
        Auth auth = new Auth();
        authProp = auth.connect(login);
        authProp.setLogin(login);
        MainPageCampaignsList mainPageCampaignsList = new MainPageCampaignsList();
        CampaignsList campaignsList = new CampaignsList();
        campaignsList.setRows(mainPageCampaignsList.allCampaigns(authProp));

        MainPageCampaignsList mainPageCampaignsListToday = new MainPageCampaignsList();
        CampaignsList campaignsListActive = new CampaignsList();
        campaignsListActive.setRows(mainPageCampaignsListToday.startActiveCampaignsList(authProp));

        List<Campaign> finalCampaignsList = new ArrayList<>();
        finalCampaignsList.addAll(campaignsListActive.getRows());
        finalCampaignsList.addAll(campaignsList.getRows());

        campaignsListMain = new CampaignsList();
        campaignsListMain.setRows(finalCampaignsList);
        this.campaignsListOld = new CampaignsList();
        this.campaignsListOld.setRows(campaignsList.getRows());
        this.campaignsListToday = new CampaignsList();
        this.campaignsListToday.setRows(mainPageCampaignsListToday.startTodayCampaignsList(authProp));
        this.campaignsListActive = new CampaignsList();
        this.campaignsListActive.setRows(campaignsListActive.getRows());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("campaignList", campaignsListMain);
        modelAndView.setViewName("main_page");

        return modelAndView;
    }

    @RequestMapping(value=("/campaign_result/{id}"), method = RequestMethod.GET)
    public ModelAndView campaign_result(@PathVariable("id") int id) throws IOException {

        System.out.println(id);
        List<Campaign> campaigns = campaignsListMain.getRows();
        System.out.println(campaigns.get(id));

        Visits visits = new Visits();
        visits.filterCampaign(authProp, campaigns.get(id), authProp.getDATE());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("campaign_result");
        modelAndView.addObject("campaign", campaigns.get(id));
        return modelAndView;
    }

    /**checking by active list**/
    @RequestMapping(value=("/check_field"), method = RequestMethod.POST)
    public ModelAndView checkField(@ModelAttribute("allchkplace") String string) throws IOException {

        CampareCampaigns campareCampaigns = new CampareCampaigns();
        List<Campaign> campareList = campareCampaigns.getDataFromList(string);
        campaignsListTemp = new CampaignsList();
        campaignsListTemp.setRows(campareList);

        campareCampaigns.campareCampaigns(campaignsListTemp, authProp);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("campaignList", campaignsListTemp.getRows());
        modelAndView.setViewName("check_compare");
        return modelAndView;
    }

    @RequestMapping(value=("/check_today"), method = RequestMethod.GET)
    public ModelAndView checkToday(@ModelAttribute("allchkplace") String string) throws IOException {

        System.out.println(string);

        Visits visits = new Visits();
        campaignsListToday = visits.getDataAll(campaignsListToday, authProp, authProp.getLogin());
        List<Campaign> campaignList = campaignsListToday.getRows();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("campaignList", campaignList);
        modelAndView.addObject("formula", authProp.getLogin().getFormula());
        modelAndView.setViewName("allcheck");
        return modelAndView;
    }

    @RequestMapping(value=("/set_time"), method = RequestMethod.GET)
    public void set_time(@RequestParam("month") String month,
                         @RequestParam("days") String days) throws IOException {

        System.out.println(month);
        System.out.println(days);
        if (!month.equals("")){
            authProp.getLogin().setMonth(Integer.parseInt(month));
        }
        if (!days.equals("")) {
            authProp.getLogin().setDays(Integer.parseInt(days));
        }
    }

    @RequestMapping(value=("/clear_time"), method = RequestMethod.GET)
    public void clear_time() {

        authProp.getLogin().setMonth(null);
        authProp.getLogin().setDays(null);
    }
    @RequestMapping(value=("/check_by_time"), method = RequestMethod.GET)
    public ModelAndView checkByTime(@ModelAttribute("allchkplace") String string) throws IOException {

        System.out.println(string);

        Visits visits = new Visits();
        campaignsListMain = visits.getDataByMonth(campaignsListMain, authProp, authProp.getLogin());
        List<Campaign> campaignList = campaignsListMain.getRows();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("campaignList", campaignList);
        modelAndView.addObject("formula", authProp.getLogin().getFormula());
        modelAndView.setViewName("allcheck");
        return modelAndView;
    }
    @RequestMapping(value=("/check_all"), method = RequestMethod.GET)
    public ModelAndView checkAll(@ModelAttribute("allchkplace") String string) throws IOException {

        System.out.println(string);

        Visits visits = new Visits();
        campaignsListMain = visits.getDataAll(campaignsListMain, authProp, authProp.getLogin());
        List<Campaign> campaignList = campaignsListMain.getRows();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("campaignList", campaignList);
        modelAndView.addObject("formula", authProp.getLogin().getFormula());
        modelAndView.setViewName("allcheck");
        return modelAndView;
    }

    @RequestMapping(value=("/check_active"), method = RequestMethod.GET)
    public ModelAndView checkActive() throws IOException {
        Visits visits = new Visits();
        campaignsListActive = visits.getDataAll(campaignsListActive, authProp, authProp.getLogin());
        List<Campaign> campaignList = campaignsListActive.getRows();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("campaignList", campaignList);
        modelAndView.addObject("formula", authProp.getLogin().getFormula());
        modelAndView.setViewName("allcheck");
        return modelAndView;
    }

    @RequestMapping(value=("/add_formula"), method = RequestMethod.POST, consumes = "text/plain")
    public void add_formula(@RequestBody String string) throws UnsupportedEncodingException {
        System.out.println(string);
        authProp.getLogin().setFormula(string);
    }

    @RequestMapping(value=("/clear_formula"), method = RequestMethod.GET)
    public void clear_formula() throws IOException {
        authProp.getLogin().setFormula(null);
    }



}
