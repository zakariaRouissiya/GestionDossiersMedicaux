package ma.sninati.gestiondossiersmedicaux.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Acte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private Double prixDeBase;
    @Enumerated(EnumType.STRING)
    private CategorieActe categorie;
    @OneToMany(mappedBy = "acte")
    private Collection<InterventionDocteur> interventionDocteurs = new ArrayList<>();
}
