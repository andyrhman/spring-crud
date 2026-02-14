package com.myspring.spring_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SpringCrudApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		if (dotenv.get("SPRING_DATASOURCE_URL") != null) {
			System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
		}
		if (dotenv.get("SPRING_DATASOURCE_USERNAME") != null) {
			System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
		}
		if (dotenv.get("SPRING_DATASOURCE_PASSWORD") != null) {
			System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
		}
		SpringApplication.run(SpringCrudApplication.class, args);
	}

}
