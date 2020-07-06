package cst438hw2.controller;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cst438hw2.domain.*;
import cst438hw2.service.CityService;

@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;

    @GetMapping("/api/cities/{city}")
    public ResponseEntity<CityInfo> getWeather(@PathVariable("city") String cityName) {
        // Retrieve City Information
        CityInfo info = cityService.getCityInfo(cityName);

        // If no city info is retrieved, send not_found httpstatus
        if (info == null) {
            return new ResponseEntity<CityInfo>(HttpStatus.NOT_FOUND);
        }
        // Send information with appropriate Status Code
        else {
            return new ResponseEntity<CityInfo>(info, HttpStatus.OK);
        }
    }
}
