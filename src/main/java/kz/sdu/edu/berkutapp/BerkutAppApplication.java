package kz.sdu.edu.berkutapp;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BerkutAppApplication{

	public static void main(String[] args) {
		SpringApplication.run(BerkutAppApplication.class, args);
	}

}
