package me.hardlearner.roomrentalmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hardlearner.roomrentalmanager.domain.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class EventControllerTest {
    private static final Logger log = LoggerFactory.getLogger(EventControllerTest.class);

    @Autowired
    TestRestTemplate template;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("lessorname", "황태원")
                .param("roomno", "401")
                .param("startdatetime", "2019-02-28T09:00")
                .param("enddatetime", "2019-02-28T11:00"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("lessorname", "김화원")
                .param("roomno", "402")
                .param("startdatetime", "2019-02-28T13:00")
                .param("enddatetime", "2019-02-28T14:00"))
                .andExpect(status().isCreated());
    }

    @Test
    public void getEvents() throws Exception {
        mockMvc.perform(get("/api/events"))
                            .andExpect(jsonPath("$").exists())
                            .andExpect(status().isOk());
    }

    @Test
    public void getEventsOfDay() throws Exception {
        mockMvc.perform(get("/api/events/days/190228"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andDo(print());
    }

    @Test
    public void createEvent() throws Exception {
        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("lessorname", "황태원")
                .param("roomno", "402")
                .param("startdatetime", "2019-03-09T09:01")
                .param("enddatetime", "2019-03-09T11:01"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("lessorName").value("황태원"))
                .andExpect(jsonPath("location.roomNo").value("402"))
                .andExpect(jsonPath("location.pianoCategory").value("GRAND"))
                .andExpect(jsonPath("location.pianoCount").value("2"))
                .andExpect(jsonPath("startDateTime").value("2019-03-09T09:01:00"))
                .andExpect(jsonPath("endDateTime").value("2019-03-09T11:01:00"))
                .andDo(print());
    }

    @Test
    public void deleteEvent() throws Exception {
        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(delete("/api/events/1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateEvent() throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("lessorname", "황태원")
                .param("roomno", "402")
                .param("startdatetime", "2019-03-09T09:01")
                .param("enddatetime", "2019-03-09T11:01"))
                .andExpect(status().isCreated());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Event event = objectMapper.readValue(contentAsString, Event.class);

        mockMvc.perform(put("/api/events/" + event.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("lessorname", "황태원")
                .param("roomno", "403")
                .param("startdatetime", "2019-03-09T11:01")
                .param("enddatetime", "2019-03-09T13:01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("lessorName").value("황태원"))
                .andExpect(jsonPath("location.roomNo").value("403"))
                .andExpect(jsonPath("startDateTime").value("2019-03-09T11:01:00"))
                .andExpect(jsonPath("endDateTime").value("2019-03-09T13:01:00"));
    }
}