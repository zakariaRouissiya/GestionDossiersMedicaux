package ma.sninati.gestiondossiersmedicaux.entities.Enums;


public enum CategorieAntecedentsMedicaux {
    MALADIE_CHRONIQUE,
    CONTRE_INDICATION,
    AUTRE,
    MALADIE_HEREDITAIRE,
    ALLERGIE,
    ;

    private Risque risqueAssocie;
    private String description;

    CategorieAntecedentsMedicaux(){ }
    CategorieAntecedentsMedicaux(Risque risqueAssocie, String description) {
        this.risqueAssocie = risqueAssocie;
        this.description = description;
    }

    public Risque getRisqueAssocie() {
        return risqueAssocie;
    }

    public void setRisqueAssocie(Risque risqueAssocie) {
        this.risqueAssocie = risqueAssocie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
