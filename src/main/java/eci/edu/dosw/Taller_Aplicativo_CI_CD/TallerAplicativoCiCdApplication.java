package eci.edu.dosw.Taller_Aplicativo_CI_CD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "eci.edu.dosw")
@EnableMongoRepositories(basePackages = "eci.edu.dosw.repository")
public class TallerAplicativoCiCdApplication {

	public static void main(String[] args) {
		SpringApplication.run(TallerAplicativoCiCdApplication.class, args);
	}

}
