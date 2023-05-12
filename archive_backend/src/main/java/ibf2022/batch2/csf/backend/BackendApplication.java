package ibf2022.batch2.csf.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;

@SpringBootApplication
public class BackendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
