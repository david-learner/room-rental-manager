package me.hardlearner.roomrentalmanager;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class RoomRentalManagerApplication {

    @PostConstruct
    public void setTimeZone() {
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(RoomRentalManagerApplication.class, args);
    }

}
