package ma.sninati.gestiondossiersmedicaux.repositories;

import ma.sninati.gestiondossiersmedicaux.entities.AntecedentMedicale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AntecedentMedicaleRepository extends JpaRepository<AntecedentMedicale, Long> {



}
