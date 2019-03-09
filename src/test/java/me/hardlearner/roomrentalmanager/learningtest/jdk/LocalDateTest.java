package me.hardlearner.roomrentalmanager.learningtest.jdk;

import org.junit.Test;

import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateTest {
    @Test
    public void createLocalDate() {
        String inputDay = "180228";
        java.time.LocalDate localDate = java.time.LocalDate.parse(inputDay, DateTimeFormatter.ofPattern("yyMMdd"));
        assertThat(localDate.toString()).isEqualTo("2018-02-28");
    }
}
