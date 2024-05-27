package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montantRestant;
    @ManyToOne
    @JoinColumn(name = "id_situation_financiere")
    private SituationFinanciere situationFinanciere;
    private Double montantPaye;
    private LocalDate dateFacturation;
    private Double montantTotal;
    @ManyToOne
    @JoinColumn(name = "id_consultation")
    private Consultation consultation;
    @Enumerated(EnumType.STRING)
    private TypePaiement typePaiement;
}

