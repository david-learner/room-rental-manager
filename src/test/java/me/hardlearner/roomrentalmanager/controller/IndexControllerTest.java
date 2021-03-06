package me.hardlearner.roomrentalmanager.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= RANDOM_PORT)
public class IndexControllerTest {
    @Autowired
    TestRestTemplate template;

    @Test
    public void index() {
        ResponseEntity<String> response = template.getForEntity("/", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}