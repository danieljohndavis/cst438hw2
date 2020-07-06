package cst438hw2.service;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.anyString;

import cst438hw2.domain.*;
import org.springframework.mock.web.MockHttpServletResponse;

@SpringBootTest
public class CityServiceTest {

	@MockBean
	private WeatherService weatherService;
	
	@Autowired
	private CityService cityService;
	
	@MockBean
	private CityRepository cityRepository;
	
	@MockBean
	private CountryRepository countryRepository;

	
	@Test
	public void contextLoads() {
	}


	@Test
	public void testCityFound() throws Exception {

		Country country = new Country("TST","TEST");
		City city = new City(1, "TestCity","TST","Test District",1000);

		List<City> cities = new ArrayList<>();
		cities.add(city);


		given(weatherService.getTempAndTime("TestCity")).willReturn(new TempAndTime(300, 100000,1000));
		given(cityRepository.findByName("TestCity")).willReturn(cities);
		given(countryRepository.findByCode("TST")).willReturn(country);


 		CityInfo returned = cityService.getCityInfo("TestCity");
 		CityInfo expected = new CityInfo(1, "TestCity","TST","TEST","Test District",1000,80.33,"04:03 AM");

		assertEquals(expected,returned);

	}
	
	@Test 
	public void  testCityNotFound() {
		Country country = new Country("TST","TEST");
		City city = new City(1, "TestCity","TST","Test District",1000);

		List<City> cities = new ArrayList<>();
		cities.add(city);


		given(weatherService.getTempAndTime("TestCity")).willReturn(new TempAndTime(300, 100000,1000));
		given(cityRepository.findByName("TestCity")).willReturn(cities);
		given(countryRepository.findByCode("TST")).willReturn(country);


		CityInfo returned = cityService.getCityInfo("Random Name of City");
		CityInfo expected = null;

		assertEquals(expected,returned);
	}
	
	@Test 
	public void  testCityMultiple() {
		Country country = new Country("TST","TEST");
		Country country1 = new Country("TST1","TEST1");
		Country country2 = new Country("TST2","TEST2");

		City city = new City(1, "TestCity","TST","Test District",1000);
		City city1 = new City(11, "TestCity","TST1","Test District1",10001);
		City city2 = new City(12, "TestCity","TST2","Test District2",100022);

		List<City> cities = new ArrayList<>();
		cities.add(city);
		cities.add(city1);
		cities.add(city2);


		given(weatherService.getTempAndTime("TestCity")).willReturn(new TempAndTime(300, 100000,1000));

		given(cityRepository.findByName("TestCity")).willReturn(cities);

		given(countryRepository.findByCode("TST")).willReturn(country);
		given(countryRepository.findByCode("TST1")).willReturn(country1);
		given(countryRepository.findByCode("TST2")).willReturn(country2);


		CityInfo returned = cityService.getCityInfo("TestCity");
		CityInfo expected = new CityInfo(1, "TestCity","TST","TEST","Test District",1000,80.33,"04:03 AM");

		assertEquals(expected,returned);
	}

}
