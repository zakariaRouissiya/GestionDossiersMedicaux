package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 3, discriminatorType = DiscriminatorType.STRING)
public class Utilisateur extends Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomUtilisateur;
    private String motDePasse;
    @Enumerated(EnumType.STRING)
    private Role role;
    public Utilisateur(String nomUtilisateur, String motDePasse) {
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
    }
}
