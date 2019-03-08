package me.hardlearner.roomrentalmanager.controller;

import me.hardlearner.roomrentalmanager.domain.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class EventControllerTest {
    private static final Logger log =  LoggerFactory.getLogger(EventControllerTest.class);

    @Autowired
    TestRestTemplate template;

    @Test
    public void getEvents() {
        ResponseEntity<Event> response = template.getForEntity("/api/events", Event.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}