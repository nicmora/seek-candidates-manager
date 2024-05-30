package pe.com.seek.seekcandidatesmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class SeekCandidatesManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeekCandidatesManagerApplication.class, args);
	}

}