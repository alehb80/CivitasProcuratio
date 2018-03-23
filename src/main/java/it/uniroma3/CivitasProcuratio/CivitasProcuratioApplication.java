package it.uniroma3.CivitasProcuratio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class CivitasProcuratioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CivitasProcuratioApplication.class, args);
	}
}
