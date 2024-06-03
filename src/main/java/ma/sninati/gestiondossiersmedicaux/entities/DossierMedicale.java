package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.StatutPaiement;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DossierMedicale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "dossierMedicale", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations;

    @CreationTimestamp
    private LocalDate dateCreation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @OneToOne(mappedBy = "dossierMedicale", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private SituationFinanciere situationFinanciere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medecin")
    private Dentiste medecinTraitant;

    @Column(unique = true, nullable = false)
    private String numeroDossier;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;

    @PrePersist
    private void generateNumeroDossier() {
        this.numeroDossier = UUID.randomUUID().toString();
    }

    public double calculateTotalAmount() {
        return consultations.stream().mapToDouble(Consultation::calculateTotalAmount).sum();
    }
}
