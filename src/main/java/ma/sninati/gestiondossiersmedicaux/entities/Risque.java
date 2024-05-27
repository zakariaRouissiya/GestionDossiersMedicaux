package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Risque {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String categorie;

    @ManyToMany
    private Collection<Patient> patientsARisque = new ArrayList<>();
}
