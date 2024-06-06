package ma.sninati.gestiondossiersmedicaux.repositories;

import ma.sninati.gestiondossiersmedicaux.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterventionMedecinRepository extends JpaRepository<InterventionMedecin, Long> {
    List<InterventionMedecin> findByConsultation(Consultation consultation);

}

