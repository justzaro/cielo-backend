package com.example.cielobackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class CieloBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(CieloBackendApplication.class, args);
	}
}
