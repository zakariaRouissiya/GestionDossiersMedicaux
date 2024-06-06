package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.TypeConsultation;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsultation;

    @OneToMany(mappedBy = "consultation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterventionMedecin> interventions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dossiermedicale")
    private DossierMedicale dossierMedicale;

    private LocalDate dateConsultation;

    @Enumerated(EnumType.STRING)
    private TypeConsultation typeConsultation;

    @OneToMany(mappedBy = "consultation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Facture> factures;

    public double calculateTotalAmount() {
        double totalAmount = 200.0;
        for (InterventionMedecin intervention : interventions) {
            if (intervention.getPrixPatient() != null && intervention.getActe() != null && intervention.getActe().getPrixDeBase() != null) {
                totalAmount += intervention.getPrixPatient();
                totalAmount += intervention.getActe().getPrixDeBase();
            }
        }
        return totalAmount;
    }



}
