package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("SEC")
public class Secretaire extends Utilisateur {
    private Double salaireDeBase;
    private LocalDate dateRetourConge;
    private Assurance assurance;
    private StatusActuel statusActuel;
    private Double prime;
    public Secretaire(String nomUtilisateur, String motDePasse, Double salaireDeBase, LocalDate dateRetourConge, Assurance assurance, StatusActuel statusActuel, Double prime) {
        super(nomUtilisateur, motDePasse);
        this.salaireDeBase = salaireDeBase;
        this.dateRetourConge = dateRetourConge;
        this.assurance = assurance;
        this.statusActuel = statusActuel;
        this.prime = prime;
    }
}

