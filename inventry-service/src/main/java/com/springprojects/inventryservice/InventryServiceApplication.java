package com.springprojects.inventryservice;

import com.springprojects.inventryservice.model.Inventry;
import com.springprojects.inventryservice.repository.InventryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventryRepository inventryRepository){
		return args -> {
			Inventry inventry = new Inventry();
			inventry.setSkuCode("Iphone-12");
			inventry.setQuantity(1000);

			Inventry inventry1 = new Inventry();
			inventry1.setSkuCode("Iphone-13");
			inventry1.setQuantity(0);

			inventryRepository.save(inventry);
			inventryRepository.save(inventry1);

		};
	}


}
