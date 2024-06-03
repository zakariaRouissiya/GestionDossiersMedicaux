package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "utilisateur_type", discriminatorType = DiscriminatorType.STRING)
public class Utilisateur extends Personne {

    private String motDePasse;
    private String nomUtilisateur;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Utilisateur(Long id, String nom, String prenom, String adresse, String telephone, String email, String cin, String motDePasse, String nomUtilisateur, Role role) {
        super(id, nom, prenom, adresse, telephone, email, cin);
        this.motDePasse = motDePasse;
        this.nomUtilisateur = nomUtilisateur;
        this.role = role;
    }
}
