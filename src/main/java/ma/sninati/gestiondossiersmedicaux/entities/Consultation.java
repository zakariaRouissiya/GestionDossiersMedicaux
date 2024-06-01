package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.TypeConsultation;

import java.time.LocalDate;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsultation;

    @OneToMany(mappedBy = "consultation", fetch = FetchType.LAZY)
    private List<InterventionMedecin> interventions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "id_dossiermedicale")
    private DossierMedicale dossierMedicale;


    private LocalDate dateConsultation;

    @Enumerated(EnumType.STRING)
    private TypeConsultation typeConsultation;

    @OneToMany(mappedBy = "consultation", fetch = FetchType.LAZY)
    private List<Facture> factures;

}
