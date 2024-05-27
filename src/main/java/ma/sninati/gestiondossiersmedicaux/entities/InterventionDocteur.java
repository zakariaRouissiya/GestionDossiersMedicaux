package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterventionDocteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numDent;
    private Double prixPatient;
    private String noteMedcin;
    @ManyToOne
    @JoinColumn(name = "id_acte")
    private Acte acte;
    @ManyToOne
    @JoinColumn(name = "id_consultation")
    private Consultation consultation;
}

