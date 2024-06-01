package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Caisse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCaisse;

    @OneToMany(mappedBy = "caisse", fetch = FetchType.LAZY)
    private List<SituationFinanciere> situationFinancieres;

    private Double recetteDuJours;
    private Double recetteDuMois;
    private Double recetteDeLAnnee;


}
