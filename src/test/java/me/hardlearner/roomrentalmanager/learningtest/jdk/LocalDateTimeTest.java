package me.hardlearner.roomrentalmanager.learningtest.jdk;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateTimeTest {
    @Test
    public void makeLocalDateTimeWithoutSeconds() {
        String startdatetime = "2019-03-09T09:10";
        LocalDateTime newStartDateTime = LocalDateTime.parse(startdatetime
                , DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        assertThat(newStartDateTime.toString()).isEqualTo("2019-03-09T09:10");
    }
}
