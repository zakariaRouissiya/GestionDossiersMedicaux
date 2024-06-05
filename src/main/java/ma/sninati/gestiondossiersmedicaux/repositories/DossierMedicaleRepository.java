package ma.sninati.gestiondossiersmedicaux.repositories;

import ma.sninati.gestiondossiersmedicaux.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DossierMedicaleRepository extends JpaRepository<DossierMedicale, Long> {
    DossierMedicale findByPatientId(Long patientId);
    List<DossierMedicale> findByMedecinTraitantId(Long medecinTraitantId);

}

