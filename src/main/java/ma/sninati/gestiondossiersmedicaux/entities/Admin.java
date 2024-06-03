package ma.sninati.gestiondossiersmedicaux.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("Admin")
public class Admin extends Utilisateur{

    String adminKey;

    public Admin(Utilisateur utilisateur) {
        super(utilisateur.getId(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getAdresse(),
                utilisateur.getTelephone(), utilisateur.getEmail(), utilisateur.getCin(),
                utilisateur.getMotDePasse(), utilisateur.getNomUtilisateur(), utilisateur.getRole());
    }


}
