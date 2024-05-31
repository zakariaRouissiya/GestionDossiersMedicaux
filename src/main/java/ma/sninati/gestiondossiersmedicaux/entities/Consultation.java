package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private TypeConsultation typeConsultation;
    @ManyToOne
    @JoinColumn(name = "id_dossier")
    private DossierMedical dossierMedical;
    @OneToMany(mappedBy = "consultation")
    private Collection<InterventionDocteur> interventionDocteurs = new ArrayList<>();
    @OneToMany(mappedBy = "consultation")
    private Collection<Facture> factures = new ArrayList<>();
}
