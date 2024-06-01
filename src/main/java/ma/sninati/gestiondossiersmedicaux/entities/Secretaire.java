package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.Assurance;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.StatusEmploye;

import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@DiscriminatorValue("Secretaire")
public class Secretaire extends Utilisateur{

    private Double salaireDeBase;


    private LocalDate dateRetourConge;

    @Enumerated(EnumType.STRING)
    private Assurance assurance;

    @Enumerated(EnumType.STRING)
    private StatusEmploye statusActuel;

    private Double prime;

}
