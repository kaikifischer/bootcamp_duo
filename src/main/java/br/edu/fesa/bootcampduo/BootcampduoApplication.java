package br.edu.fesa.bootcampduo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.edu.fesa.bootcampduo", "br.edu.fesa.bootcamp.service"})
//@SpringBootApplication
public class BootcampduoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootcampduoApplication.class, args);
	}

}
