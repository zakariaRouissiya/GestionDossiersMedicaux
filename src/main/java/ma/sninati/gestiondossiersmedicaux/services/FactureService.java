package ma.sninati.gestiondossiersmedicaux.services;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import ma.sninati.gestiondossiersmedicaux.entities.Consultation;
import ma.sninati.gestiondossiersmedicaux.entities.Facture;
import ma.sninati.gestiondossiersmedicaux.repositories.ConsultationRepository;
import ma.sninati.gestiondossiersmedicaux.repositories.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    public Facture generateFactureForConsultation(Long consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid consultation Id:" + consultationId));
        double montantTotal = consultation.calculateTotalAmount();

        Facture facture = new Facture();
        facture.setConsultation(consultation);
        facture.setMontantTotal(montantTotal);
        facture.setMontantRestant(montantTotal);
        facture.setMontantPaye(0.0);

        return factureRepository.save(facture);
    }



    public ByteArrayInputStream generatePdfForFacture(Long factureId, Long consultationId) throws IOException {
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid facture Id:" + factureId));

        if (!facture.getConsultation().getIdConsultation().equals(consultationId)) {
            throw new IllegalArgumentException("Facture does not belong to the specified consultation Id:" + consultationId);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDocument);

        Color headerColor = new DeviceRgb(0, 102, 204);
        Style headerStyle = new Style().setFontSize(20).setBold().setFontColor(headerColor).setTextAlignment(TextAlignment.CENTER);
        Style normalStyle = new Style().setTextAlignment(TextAlignment.LEFT);

        document.add(new Paragraph("RECEPISSE DE REGLEMENT - FACTURE CONSULTATION SNINATI").addStyle(headerStyle));

        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{3, 7})).useAllAvailableWidth().setMarginTop(10);

        infoTable.addHeaderCell(new Cell().add(new Paragraph("Facture ID:")).addStyle(normalStyle));
        infoTable.addHeaderCell(new Cell().add(new Paragraph(facture.getIdFacture().toString())).addStyle(normalStyle));

        infoTable.addHeaderCell(new Cell().add(new Paragraph("Date:")).addStyle(normalStyle));
        infoTable.addHeaderCell(new Cell().add(new Paragraph(facture.getDateFacturation().toString())).addStyle(normalStyle));

        infoTable.addHeaderCell(new Cell().add(new Paragraph("Montant Total:")).addStyle(normalStyle));
        infoTable.addHeaderCell(new Cell().add(new Paragraph(facture.getMontantTotal().toString() + " MAD")).addStyle(normalStyle));

        infoTable.addHeaderCell(new Cell().add(new Paragraph("Montant Payé:")).addStyle(normalStyle));
        infoTable.addHeaderCell(new Cell().add(new Paragraph(facture.getMontantPaye().toString() + " MAD")).addStyle(normalStyle));

        infoTable.addHeaderCell(new Cell().add(new Paragraph("Montant Restant:")).addStyle(normalStyle));
        infoTable.addHeaderCell(new Cell().add(new Paragraph(facture.getMontantRestant().toString() + " MAD")).addStyle(normalStyle));

        infoTable.addHeaderCell(new Cell().add(new Paragraph("Consultation ID:")).addStyle(normalStyle));
        infoTable.addHeaderCell(new Cell().add(new Paragraph(facture.getConsultation().getIdConsultation().toString())).addStyle(normalStyle));

        infoTable.addHeaderCell(new Cell().add(new Paragraph("Patient:")).addStyle(normalStyle));
        infoTable.addHeaderCell(new Cell().add(new Paragraph(facture.getConsultation().getDossierMedicale().getPatient().getNom() + " " + facture.getConsultation().getDossierMedicale().getPatient().getPrenom())).addStyle(normalStyle));

        infoTable.addHeaderCell(new Cell().add(new Paragraph("Dossier Médical:")).addStyle(normalStyle));
        infoTable.addHeaderCell(new Cell().add(new Paragraph(facture.getConsultation().getDossierMedicale().getNumeroDossier())).addStyle(normalStyle));

        infoTable.addHeaderCell(new Cell().add(new Paragraph("Docteur:")).addStyle(normalStyle));
        infoTable.addHeaderCell(new Cell().add(new Paragraph(facture.getConsultation().getDossierMedicale().getMedecinTraitant().getNom() + " " + facture.getConsultation().getDossierMedicale().getMedecinTraitant().getPrenom())).addStyle(normalStyle));

        document.add(infoTable);

        document.add(new Paragraph("Merci pour votre confiance!").addStyle(normalStyle).setTextAlignment(TextAlignment.CENTER).setMarginTop(50));

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }


}
