package ma.sninati.gestiondossiersmedicaux;

import ma.sninati.gestiondossiersmedicaux.entities.*;
import ma.sninati.gestiondossiersmedicaux.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ma.sninati.gestiondossiersmedicaux.repositories.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class GestionDossiersMedicauxApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDossiersMedicauxApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
