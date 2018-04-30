package it.uniroma3.CivitasProcuratio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import it.uniroma3.CivitasProcuratio.property.StorageProperties;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@EnableConfigurationProperties(StorageProperties.class)
public class CivitasProcuratioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CivitasProcuratioApplication.class, args);
	}
}
