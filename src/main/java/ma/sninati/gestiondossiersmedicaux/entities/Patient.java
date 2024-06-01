package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.GroupeSanguin;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.Mutuelle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Patient extends Personne{

    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    private Mutuelle mutuelle;

    @Enumerated(EnumType.STRING)
    private GroupeSanguin groupeSanguin;


    @ManyToMany( mappedBy = "patientsAvecCeAntecedentMedicale", fetch = FetchType.LAZY)
    private List<AntecedentMedicale> AntecedentsMedicaux = new ArrayList<>();


    @OneToOne(mappedBy = "patient",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private DossierMedicale dossierMedicale;


    private String profession;

}
