package cst438hw2.controller;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cst438hw2.domain.*;
import cst438hw2.service.CityService;

@Controller
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/cities/{city}")
    public String getWeather(@PathVariable("city") String cityName, Model model) {

        // Retrieve information from City Service
        CityInfo info = cityService.getCityInfo(cityName);

        // If no information was returned, send to error template
        if (info == null) {
            return "error";
        } else {
            // Add CityInfo to model and send to display template
            model.addAttribute(info);
            return "city_info_list";
        }
    }

}