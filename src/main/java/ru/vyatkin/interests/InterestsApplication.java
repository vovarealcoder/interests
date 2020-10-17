package ru.vyatkin.interests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories("ru.vyatkin.interests.db.repository")
@EnableTransactionManagement
public class InterestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterestsApplication.class, args);
	}

}
