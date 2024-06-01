package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class SituationFinanciere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSituationFinanciere;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn( name= "id_dossiermedicale")
    private DossierMedicale dossierMedicale;

    @CreationTimestamp
    private LocalDate dateCreation;

    private Double montantGlobaleRestant;

    private Double montantGlobalePaye;

    @OneToMany(mappedBy = "situationFinanciere", fetch = FetchType.LAZY)
    private List<Facture> factures;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "id_caisse")
    private Caisse caisse;

}
