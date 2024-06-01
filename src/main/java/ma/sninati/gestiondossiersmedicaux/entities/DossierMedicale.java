package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.StatutPaiement;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class DossierMedicale {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany( mappedBy = "dossierMedicale", fetch = FetchType.LAZY)
    private List<Consultation> consultations;

    @CreationTimestamp
    private LocalDate dateCreation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn( name= "id_patient")
    private Patient patient;

    @OneToOne(mappedBy = "dossierMedicale",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private SituationFinanciere situationFinanciere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name= "id_medecin")
    private Dentiste medecinTraitant;


    private String numeroDossier;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;

}
