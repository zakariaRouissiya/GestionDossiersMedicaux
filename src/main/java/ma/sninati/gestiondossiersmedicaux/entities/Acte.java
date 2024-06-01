package ma.sninati.gestiondossiersmedicaux.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.CategorieActe;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Acte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActe;

    @OneToMany(mappedBy = "acte", fetch = FetchType.LAZY)
    private List<InterventionMedecin> interventions;

    private Double prixDeBase;

    @Enumerated(EnumType.STRING)
    private CategorieActe categorie;
    private String libelle;

}
