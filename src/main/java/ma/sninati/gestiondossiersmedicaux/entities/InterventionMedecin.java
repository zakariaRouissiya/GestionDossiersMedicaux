package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InterventionMedecin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIntervention;

    private String noteMedecin;
    private Double prixPatient;
    private Long dent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_acte")
    private Acte acte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consultation")
    private Consultation consultation;
}
