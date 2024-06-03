package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.Assurance;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.Disponibilite;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.Specialite;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.StatusEmploye;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@DiscriminatorValue("Dentiste")
public class Dentiste extends  Utilisateur{


    private LocalDate dateRetourConge;

    private Double salaireDeBase;

    @Enumerated(EnumType.STRING)
    private Specialite specialite;

    @Convert(converter = DisponibiliteConverter.class)
    private Map<DayOfWeek, Disponibilite> disponibilite;

    @Enumerated(EnumType.STRING)
    private Assurance assurance;

    @Enumerated(EnumType.STRING)
    private StatusEmploye statusActuel;

    @OneToMany( mappedBy = "medecinTraitant", fetch = FetchType.LAZY)
    private List<DossierMedicale> dossierTraites;

    public Dentiste(Utilisateur utilisateur) {
        super(utilisateur.getId(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getAdresse(),
                utilisateur.getTelephone(), utilisateur.getEmail(), utilisateur.getCin(),
                utilisateur.getMotDePasse(), utilisateur.getNomUtilisateur(), utilisateur.getRole());
    }

}
