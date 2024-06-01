package ma.sninati.gestiondossiersmedicaux;

import ma.sninati.gestiondossiersmedicaux.entities.*;
import ma.sninati.gestiondossiersmedicaux.repositories.PatientRepository;
import ma.sninati.gestiondossiersmedicaux.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ma.sninati.gestiondossiersmedicaux.repositories.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class GestionDossiersMedicauxApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDossiersMedicauxApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository, UtilisateurRepository utilisateurRepository) {
        return args -> {
            Adresse adresse1 = new Adresse("123 Main St", "Paris", "France", "75001");
            Patient patient1 = new Patient("Doe", "John", adresse1, "123456789", "john.doe@example.com", "AB123456", LocalDate.of(1985, 5, 15), Mutuelle.CNOPS, GroupeSanguin.O_NEGATIF, "Engineer");

            Adresse adresse2 = new Adresse("456 Elm St", "Lyon", "France", "69002");
            Patient patient2 = new Patient("Smith", "Jane", adresse2, "987654321", "jane.smith@example.com", "CD789012", LocalDate.of(1990, 8, 25), Mutuelle.CMIM, GroupeSanguin.A_NEGATIF, "Doctor");

            patientRepository.save(patient1);
            patientRepository.save(patient2);

        };
    }
}
