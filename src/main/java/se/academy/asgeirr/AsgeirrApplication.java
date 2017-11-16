package se.academy.asgeirr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AsgeirrApplication {
    private static List<Highscore> highScore;

    public static void main(String[] args) {
        SpringApplication.run(AsgeirrApplication.class, args);
    }
}
