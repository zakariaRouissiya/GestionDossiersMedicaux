package ma.sninati.gestiondossiersmedicaux.repositories;

import ma.sninati.gestiondossiersmedicaux.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

}

