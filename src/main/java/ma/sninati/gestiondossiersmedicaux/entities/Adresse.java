package ma.sninati.gestiondossiersmedicaux.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Adresse {
    private String rue;
    private String ville;
    private String pays;
    private String codePostal;
}
