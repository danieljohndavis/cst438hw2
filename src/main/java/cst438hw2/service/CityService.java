package cst438hw2.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438hw2.domain.*;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private WeatherService weatherService;

    public CityInfo getCityInfo(String cityName) {
        List<City> tempCity = cityRepository.findByName(cityName);

        // If no cities are returned, return null.
        if (tempCity.size() == 0) return null;
        else {
            // Get Country information by code for first result.
            Country tempCountry = countryRepository.findByCode(tempCity.get(0).getCountryCode());
            // Get temperature and time information from weather service.
            TempAndTime tempTempAndTime = weatherService.getTempAndTime(cityName);
            // Use new helpers in TempAndTime class to format output from the service.
            return new CityInfo(tempCity.get(0), tempCountry.getName(), tempTempAndTime.getFahrenheit(), tempTempAndTime.getLocalTime());
        }
    }
}
