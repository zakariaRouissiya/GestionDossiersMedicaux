package ma.sninati.gestiondossiersmedicaux.entities.Enums;

public enum StatutPaiement {

    EN_ATTENTE,
    IMPAYÉ,
    PAYÉ ;
    String description;

    StatutPaiement() {

    }

    StatutPaiement(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
