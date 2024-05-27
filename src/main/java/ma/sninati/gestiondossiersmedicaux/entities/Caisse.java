package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Caisse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "caisse", cascade = CascadeType.ALL)
    private List<SituationFinanciere> situationsFinancieres = new ArrayList<>();

    private Double recetteDuJour;
    private Double recetteDuMois;
    private Double recetteDeLAnnee;
}