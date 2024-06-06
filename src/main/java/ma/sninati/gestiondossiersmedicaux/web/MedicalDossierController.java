package ma.sninati.gestiondossiersmedicaux.web;

import lombok.extern.flogger.Flogger;
import ma.sninati.gestiondossiersmedicaux.entities.*;
import ma.sninati.gestiondossiersmedicaux.repositories.*;
import ma.sninati.gestiondossiersmedicaux.services.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dentiste/dossierMedicale")
public class MedicalDossierController {

    @Autowired
    private DossierMedicaleRepository dossierMedicaleRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private InterventionMedecinRepository interventionMedecinRepository;

    @Autowired
    private ActeRepository acteRepository;

    @Autowired
    private FactureService factureService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping
    public String listDossiers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Dentiste dentiste = (Dentiste) utilisateurRepository.findByEmail(authentication.getName());
        List<DossierMedicale> dossiers = dossierMedicaleRepository.findAll().stream()
                .filter(d -> d.getMedecinTraitant() != null && d.getMedecinTraitant().getId().equals(dentiste.getId()))
                .collect(Collectors.toList());
        model.addAttribute("dossiers", dossiers);
        return "dentiste/list-dossierMedicale";
    }

    @GetMapping("/add")
    public String addDossierForm(Model model) {
        model.addAttribute("dossierMedicale", new DossierMedicale());
        model.addAttribute("patients", patientRepository.findAll());
        return "dentiste/add-dossierMedicale";
    }

    @PostMapping
    public String saveDossier(@ModelAttribute("dossierMedicale") DossierMedicale dossierMedicale) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Dentiste dentiste = (Dentiste) utilisateurRepository.findByEmail(authentication.getName());
        dossierMedicale.setMedecinTraitant(dentiste);
        dossierMedicale.setDateCreation(LocalDate.now());
        dossierMedicaleRepository.save(dossierMedicale);
        return "redirect:/dentiste/dossierMedicale";
    }

    @GetMapping("/edit/{id}")
    public String editDossierForm(@PathVariable Long id, Model model) {
        DossierMedicale dossierMedicale = dossierMedicaleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dossier Id:" + id));
        model.addAttribute("dossierMedicale", dossierMedicale);
        model.addAttribute("patients", patientRepository.findAll());
        return "dentiste/edit-dossierMedicale";
    }

    @PostMapping("/{id}")
    public String updateDossier(@PathVariable Long id, @ModelAttribute("dossierMedicale") DossierMedicale dossierMedicale) {
        DossierMedicale existingDossier = dossierMedicaleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dossier Id:" + id));
        existingDossier.setDateCreation(LocalDate.now());
        existingDossier.setPatient(dossierMedicale.getPatient());
        existingDossier.setMedecinTraitant(dossierMedicale.getMedecinTraitant() != null ? dossierMedicale.getMedecinTraitant() : existingDossier.getMedecinTraitant());
        existingDossier.setNumeroDossier(dossierMedicale.getNumeroDossier());
        existingDossier.setStatutPaiement(dossierMedicale.getStatutPaiement());

        if (dossierMedicale.getConsultations() != null) {
            existingDossier.getConsultations().clear();
            existingDossier.getConsultations().addAll(dossierMedicale.getConsultations());
        }

        dossierMedicaleRepository.save(existingDossier);
        return "redirect:/dentiste/dossierMedicale";
    }

    @GetMapping("/delete/{id}")
    public String deleteDossier(@PathVariable Long id) {
        DossierMedicale dossierMedicale = dossierMedicaleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dossier Id:" + id));
        dossierMedicaleRepository.delete(dossierMedicale);
        return "redirect:/dentiste/dossierMedicale";
    }

    @GetMapping("/consultations")
    public String listConsultations(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Dentiste dentiste = (Dentiste) utilisateurRepository.findByEmail(authentication.getName());
        List<Consultation> consultations = consultationRepository.findAll().stream()
                .filter(c -> c.getDossierMedicale().getMedecinTraitant().getId().equals(dentiste.getId()))
                .collect(Collectors.toList());
        model.addAttribute("consultations", consultations);
        return "dentiste/list-consultations";
    }

    @GetMapping("/consultations/add")
    public String addConsultationForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Dentiste dentiste = (Dentiste) utilisateurRepository.findByEmail(authentication.getName());
        List<DossierMedicale> filteredDossiers = dossierMedicaleRepository.findAll().stream()
                .filter(d -> d.getMedecinTraitant().getId().equals(dentiste.getId()))
                .collect(Collectors.toList());
        model.addAttribute("consultation", new Consultation());
        model.addAttribute("dossiers", filteredDossiers);
        return "dentiste/add-consultation";
    }

    @PostMapping("/consultations")
    public String saveConsultation(@ModelAttribute("consultation") Consultation consultation) {
        consultationRepository.save(consultation);
        return "redirect:/dentiste/dossierMedicale/consultations";
    }

    @GetMapping("/consultations/edit/{id}")
    public String editConsultationForm(@PathVariable Long id, Model model) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid consultation Id:" + id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Dentiste dentiste = (Dentiste) utilisateurRepository.findByEmail(authentication.getName());
        List<DossierMedicale> filteredDossiers = dossierMedicaleRepository.findAll().stream()
                .filter(d -> d.getMedecinTraitant().getId().equals(dentiste.getId()))
                .collect(Collectors.toList());
        model.addAttribute("consultation", consultation);
        model.addAttribute("dossiers", filteredDossiers);
        return "dentiste/edit-consultation";
    }

    @PostMapping("/consultations/{id}")
    public String updateConsultation(@PathVariable Long id, @ModelAttribute("consultation") Consultation consultation) {
        Consultation existingConsultation = consultationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid consultation Id:" + id));
        existingConsultation.setDateConsultation(consultation.getDateConsultation());
        existingConsultation.setDossierMedicale(consultation.getDossierMedicale());
        existingConsultation.setTypeConsultation(consultation.getTypeConsultation());
        consultationRepository.save(existingConsultation);
        return "redirect:/dentiste/dossierMedicale/consultations";
    }

    @GetMapping("/consultations/delete/{id}")
    public String deleteConsultation(@PathVariable Long id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid consultation Id:" + id));
        consultationRepository.delete(consultation);
        return "redirect:/dentiste/dossierMedicale/consultations";
    }

    @GetMapping("/interventions")
    public String listInterventions(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Dentiste dentiste = (Dentiste) utilisateurRepository.findByEmail(authentication.getName());
        List<InterventionMedecin> interventions = interventionMedecinRepository.findAll().stream()
                .filter(i -> i.getConsultation().getDossierMedicale().getMedecinTraitant().getId().equals(dentiste.getId()))
                .collect(Collectors.toList());
        model.addAttribute("interventions", interventions);
        return "dentiste/list-interventions";
    }

    @GetMapping("/interventions/add")
    public String addInterventionForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Dentiste dentiste = (Dentiste) utilisateurRepository.findByEmail(authentication.getName());
        List<Consultation> filteredConsultations = consultationRepository.findAll().stream()
                .filter(c -> c.getDossierMedicale().getMedecinTraitant().getId().equals(dentiste.getId()))
                .collect(Collectors.toList());
        model.addAttribute("intervention", new InterventionMedecin());
        model.addAttribute("consultations", filteredConsultations);
        model.addAttribute("actes", acteRepository.findAll());
        return "dentiste/add-intervention";
    }

    @PostMapping("/interventions")
    public String saveIntervention(@ModelAttribute("intervention") InterventionMedecin intervention) {
        interventionMedecinRepository.save(intervention);
        Facture facture = factureService.generateFactureForConsultation(intervention.getConsultation().getIdConsultation());
        Long factureId = facture.getIdFacture();
        return "redirect:/dentiste/dossierMedicale/interventions";
    }


    @GetMapping("/interventions/edit/{id}")
    public String editInterventionForm(@PathVariable Long id, Model model) {
        InterventionMedecin intervention = interventionMedecinRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid intervention Id:" + id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Dentiste dentiste = (Dentiste) utilisateurRepository.findByEmail(authentication.getName());
        List<Consultation> filteredConsultations = consultationRepository.findAll().stream()
                .filter(c -> c.getDossierMedicale().getMedecinTraitant().getId().equals(dentiste.getId()))
                .collect(Collectors.toList());
        model.addAttribute("intervention", intervention);
        model.addAttribute("consultations", filteredConsultations);
        model.addAttribute("actes", acteRepository.findAll());
        return "dentiste/edit-intervention";
    }

    @PostMapping("/interventions/{id}")
    public String updateIntervention(@PathVariable Long id, @ModelAttribute("intervention") InterventionMedecin intervention) {
        InterventionMedecin existingIntervention = interventionMedecinRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid intervention Id:" + id));
        existingIntervention.setNoteMedecin(intervention.getNoteMedecin());
        existingIntervention.setPrixPatient(intervention.getPrixPatient());
        existingIntervention.setDent(intervention.getDent());
        existingIntervention.setActe(intervention.getActe());
        existingIntervention.setConsultation(intervention.getConsultation());
        interventionMedecinRepository.save(existingIntervention);
        return "redirect:/dentiste/dossierMedicale/interventions";
    }

    @GetMapping("/interventions/delete/{id}")
    public String deleteIntervention(@PathVariable Long id) {
        InterventionMedecin intervention = interventionMedecinRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid intervention Id:" + id));
        interventionMedecinRepository.delete(intervention);
        return "redirect:/dentiste/dossierMedicale/interventions";
    }

    @GetMapping("/facture/download/{consultationId}/{factureId}")
    public ResponseEntity<InputStreamResource> downloadFacture(@PathVariable Long consultationId, @PathVariable Long factureId) throws IOException {
        ByteArrayInputStream bis = factureService.generatePdfForFacture(factureId, consultationId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=facture.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/view/{id}")
    public String viewDossier(@PathVariable Long id, Model model) {
        DossierMedicale dossierMedicale = dossierMedicaleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dossier Id:" + id));
        List<Consultation> consultations = consultationRepository.findByDossierMedicale(dossierMedicale);

        List<InterventionMedecin> interventions = new ArrayList<>();
        for (Consultation consultation : consultations) {
            List<InterventionMedecin> consultationInterventions = interventionMedecinRepository.findByConsultation(consultation);
            interventions.addAll(consultationInterventions);
        }

        model.addAttribute("dossier", dossierMedicale);
        model.addAttribute("consultations", consultations);
        model.addAttribute("interventions", interventions);

        return "dentiste/view-dossierMedicale";
    }

    @GetMapping("/consultations/add/{dossierId}")
    public String addConsultationForm(@PathVariable Long dossierId, Model model) {
        DossierMedicale dossierMedicale = dossierMedicaleRepository.findById(dossierId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dossier Id:" + dossierId));
        model.addAttribute("consultation", new Consultation());
        model.addAttribute("dossier", dossierMedicale);
        return "dentiste/add-consultation";
    }

    @GetMapping("/interventions/add/{dossierId}")
    public String addInterventionForm(@PathVariable Long dossierId, Model model) {
        DossierMedicale dossierMedicale = dossierMedicaleRepository.findById(dossierId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dossier Id:" + dossierId));
        List<Consultation> consultations = consultationRepository.findByDossierMedicale(dossierMedicale);
        model.addAttribute("intervention", new InterventionMedecin());
        model.addAttribute("consultations", consultations);
        model.addAttribute("actes", acteRepository.findAll());
        return "dentiste/add-intervention";
    }


}
