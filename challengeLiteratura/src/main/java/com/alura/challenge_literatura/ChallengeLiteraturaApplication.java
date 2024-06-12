package com.alura.challenge_literatura;

import com.alura.challenge_literatura.principal.Principal;
import com.alura.challenge_literatura.repository.AutorRepository;
import com.alura.challenge_literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class ChallengeLiteraturaApplication implements CommandLineRunner {

//Inyeccion de dependencias
	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private LibroRepository libroRepository;



	public static void main(String[] args) {

		// Set Hibernate logging level to OFF
		Logger.getLogger("org.hibernate").setLevel(Level.OFF);

		SpringApplication.run(ChallengeLiteraturaApplication.class, args);
	}

	// Inyecciòn del autorRepository
	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraElMenu();
	}
}
