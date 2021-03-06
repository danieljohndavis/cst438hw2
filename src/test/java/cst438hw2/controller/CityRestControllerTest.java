package cst438hw2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import cst438hw2.domain.*;
import cst438hw2.service.CityService;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(CityRestController.class)
public class CityRestControllerTest {

    @MockBean
    private CityService cityService;

    @Autowired
    private MockMvc mvc;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<CityInfo> json;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void getCityInfo() throws Exception {
        // Basic city info for use in service.
        CityInfo cityInfo = new CityInfo(1, "TestCity", "TST", "TEST", "Test District", 1000, 80.33, "04:03 AM");

        // Given for the city info using above.
        given(cityService.getCityInfo("TestCity")).willReturn(cityInfo);

        // Get response from mvc model.
        MockHttpServletResponse response = mvc.perform(get("/api/cities/TestCity")).andReturn().getResponse();

        // Check that status code is as expected.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // Convert JSON to object. Had to ass default constructor for this to work.
        CityInfo returned = json.parseObject(response.getContentAsString());

        // Final test.
        assertEquals(cityInfo, returned);
    }

}
