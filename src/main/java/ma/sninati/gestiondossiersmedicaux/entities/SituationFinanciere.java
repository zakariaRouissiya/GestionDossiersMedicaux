package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SituationFinanciere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Caisse caisse;

    @OneToOne(optional = true)
    @JoinColumn(name = "id_dossier_medical")
    private DossierMedical dossierMedical;

    private LocalDate dateDeCreation;
    private Double montantGlobalRestant;
    private Double montantGlobalPaye;

    @OneToMany(mappedBy = "situationFinanciere", cascade = CascadeType.ALL)
    private List<Facture> factures = new ArrayList<>();
}