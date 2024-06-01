package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.TypePaiement;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacture;

    private Double montantRestant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_situationFinanciere")
    private SituationFinanciere situationFinanciere;

    private Double montantPaye;

    @CreationTimestamp
    private LocalDate dateFacturation;

    private Double montantTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "id_consultation")
    private Consultation consultation;

    @Enumerated(EnumType.STRING)
    private TypePaiement typePaiement;

}
