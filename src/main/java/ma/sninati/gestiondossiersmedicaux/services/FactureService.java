package ma.sninati.gestiondossiersmedicaux.services;

import ma.sninati.gestiondossiersmedicaux.entities.Consultation;
import ma.sninati.gestiondossiersmedicaux.entities.DossierMedicale;
import ma.sninati.gestiondossiersmedicaux.entities.Facture;
import ma.sninati.gestiondossiersmedicaux.repositories.ConsultationRepository;
import ma.sninati.gestiondossiersmedicaux.repositories.DossierMedicaleRepository;
import ma.sninati.gestiondossiersmedicaux.repositories.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private DossierMedicaleRepository dossierMedicaleRepository;

    public Facture generateFactureForConsultation(Long consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid consultation Id:" + consultationId));
        double totalAmount = consultation.calculateTotalAmount();

        Facture facture = new Facture();
        facture.setMontantTotal(totalAmount);
        facture.setMontantPaye(0.0);
        facture.setMontantRestant(totalAmount);
        facture.setConsultation(consultation);

        return factureRepository.save(facture);
    }

    public Facture generateFactureForDossierMedicale(Long dossierMedicaleId) {
        DossierMedicale dossierMedicale = dossierMedicaleRepository.findById(dossierMedicaleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dossier Id:" + dossierMedicaleId));
        double totalAmount = dossierMedicale.calculateTotalAmount();

        Facture facture = new Facture();
        facture.setMontantTotal(totalAmount);
        facture.setMontantPaye(0.0);
        facture.setMontantRestant(totalAmount);
        facture.setSituationFinanciere(dossierMedicale.getSituationFinanciere());

        return factureRepository.save(facture);
    }
}
