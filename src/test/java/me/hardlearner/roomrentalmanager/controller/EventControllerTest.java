package me.hardlearner.roomrentalmanager.controller;

import me.hardlearner.roomrentalmanager.domain.Event;
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

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class EventControllerTest {
    private static final Logger log =  LoggerFactory.getLogger(EventControllerTest.class);

    @Autowired
    TestRestTemplate template;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getEvents() throws Exception {
        ResponseEntity<Event> response = template.getForEntity("/api/events", Event.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        mockMvc.perform(get("/api/events"))
                    .andExpect(status().isOk());
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
}