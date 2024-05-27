package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class DossierMedical {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDate DateDeCreation;
    @OneToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "id_docteur")
    private Dentiste medecinTraitant;
    @OneToMany(mappedBy = "dossierMedical",cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Consultation> consultations = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "id_situation_financiere")
    private SituationFinanciere situationFinanciere;
    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;
}

