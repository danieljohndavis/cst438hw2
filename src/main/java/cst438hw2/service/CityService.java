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

        if (tempCity.size() == 0) return null;

        else {
            Country tempCountry = countryRepository.findByCode(tempCity.get(0).getCountryCode());
            TempAndTime tempTempAndTime = weatherService.getTempAndTime(cityName);
            double tempTemp = (double) Math.round(((tempTempAndTime.temp - 273.15) * 9.0 / 5.0 + 32.0) * 100) / 100;
            String time = fixTimeString(tempTempAndTime);
            return new CityInfo(tempCity.get(0), tempCountry.getName(), tempTemp, time);
        }
    }

    public String fixTimeString(TempAndTime t) {

        Instant instant = Instant.ofEpochSecond(t.time);
        ZoneOffset offset = ZoneOffset.ofTotalSeconds(t.timezone);

        OffsetDateTime offsetDate = instant.atOffset(offset);

        String timeFormat = "hh:mm a";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(timeFormat);

        return offsetDate.format(dtf);
    }
}
