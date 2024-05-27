package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patient extends Personne {
    @Temporal(TemporalType.DATE)
    private LocalDate dateNaissance;
    @Enumerated(EnumType.STRING)
    private Mutuelle mutuelle;
    @Enumerated(EnumType.STRING)
    private GroupeSanguin groupeSanguin;
    @ManyToMany(mappedBy = "patientsARisque")
    private Collection<Risque> risques = new ArrayList<>();
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private DossierMedical dossierMedical;
    private String profession;

    public Patient(String nom, String prenom, Adresse adresse, String tel, String email, String cin, LocalDate dateNaissance, Mutuelle mutuelle, GroupeSanguin groupeSanguin, String profession) {
        super(nom, prenom, adresse, tel, email, cin);
        this.dateNaissance = dateNaissance;
        this.mutuelle = mutuelle;
        this.groupeSanguin = groupeSanguin;
        this.profession = profession;
    }
}