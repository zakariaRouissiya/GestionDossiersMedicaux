package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@DiscriminatorValue("ADM")
public class Administrateur extends Utilisateur {
    public Administrateur() {
    }

    public Administrateur(String nomUtilisateur, String motDePasse) {
        super(nomUtilisateur, motDePasse);
        this.setRole(Role.ADMINISTRATEUR);
    }
}


