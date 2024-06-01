package ma.sninati.gestiondossiersmedicaux.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.CategorieAntecedentsMedicaux;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class AntecedentMedicale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAntecedent;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "patient_antecedent",
            joinColumns = @JoinColumn(name = "id_antecedent"),
            inverseJoinColumns = @JoinColumn(name = "id_patient")
    )
    private List<Patient> patientsAvecCeAntecedentMedicale = new ArrayList<>();

    private String libelle;

    @Enumerated(EnumType.STRING)
    private CategorieAntecedentsMedicaux categorie;

}
