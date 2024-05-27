package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("DEN")
public class Dentiste extends Utilisateur {
    private String specialite;
    @CreationTimestamp
    private LocalDate dateRecrutement;
    @OneToMany(mappedBy = "medecinTraitant")
    private Collection<DossierMedical> dossierTraites = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Disponibilite disponibilite;
    @Enumerated(EnumType.STRING)
    private Assurance assurance;
    @Enumerated(EnumType.STRING)
    private StatusActuel statusActuel;
    private Double salaireDeBase;

    public Dentiste(String nomUtilisateur, String motDePasse) {
        super(nomUtilisateur, motDePasse);
    }
    public Dentiste(String nomUtilisateur, String motDePasse, String specialite, LocalDate dateRecrutement, Collection<DossierMedical> dossierTraites, Disponibilite disponibilite, Assurance assurance, StatusActuel statusActuel, Double salaireDeBase) {
        super(nomUtilisateur, motDePasse);
        this.specialite = specialite;
        this.dateRecrutement = dateRecrutement;
        this.dossierTraites = dossierTraites;
        this.disponibilite = disponibilite;
        this.assurance = assurance;
        this.statusActuel = statusActuel;
        this.salaireDeBase = salaireDeBase;
    }

}


