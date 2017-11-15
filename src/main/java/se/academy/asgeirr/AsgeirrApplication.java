package se.academy.asgeirr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AsgeirrApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsgeirrApplication.class, args);
	}
}
