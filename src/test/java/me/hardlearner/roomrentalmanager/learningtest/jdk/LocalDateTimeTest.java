package me.hardlearner.roomrentalmanager.learningtest.jdk;

import org.apache.tomcat.jni.Local;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class    LocalDateTimeTest {
    @Test
    public void makeLocalDateTimeWithoutSeconds() {
        String startdatetime = "2019-03-09T09:10";
        LocalDateTime newStartDateTime = LocalDateTime.parse(startdatetime
                , DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        assertThat(newStartDateTime.toString()).isEqualTo("2019-03-09T09:10");
    }

    @Test
    public void isAfter() {
        LocalDateTime prev = LocalDateTime.of(2019, 3, 8, 10, 00);
        LocalDateTime next = LocalDateTime.of(2019, 3, 8, 11, 00);

        assertThat(prev.isBefore(next)).isTrue();
        assertThat(next.isAfter(prev)).isTrue();
    }

    @Test
    public void withYear() {
        LocalDateTime year2019 = LocalDateTime.of(2019, 02, 28, 0, 0, 0);

        System.out.println(year2019.withYear(2018).toString());
    }
}
