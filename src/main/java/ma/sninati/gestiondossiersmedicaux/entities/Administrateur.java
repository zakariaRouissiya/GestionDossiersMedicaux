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

    public Administrateur(String username, String password) {
        super(username, password);
    }
}


