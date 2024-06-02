package ma.sninati.gestiondossiersmedicaux.web;

import ma.sninati.gestiondossiersmedicaux.entities.*;
import ma.sninati.gestiondossiersmedicaux.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private PatientRepository patientRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping
    public String listDossiers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Dentiste dentiste = (Dentiste) utilisateurRepository.findByEmail(authentication.getName());
        List<DossierMedicale> dossiers = dossierMedicaleRepository.findAll().stream()
                .filter(d -> d.getMedecinTraitant().getId().equals(dentiste.getId()))
                .toList();
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
        existingDossier.setConsultations(dossierMedicale.getConsultations());
        existingDossier.setDateCreation(dossierMedicale.getDateCreation());
        existingDossier.setPatient(dossierMedicale.getPatient());
        existingDossier.setMedecinTraitant(dossierMedicale.getMedecinTraitant());
        existingDossier.setNumeroDossier(dossierMedicale.getNumeroDossier());
        existingDossier.setStatutPaiement(dossierMedicale.getStatutPaiement());
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

    // CRUD for Consultation
    @GetMapping("/consultations")
    public String listConsultations(Model model) {
        List<Consultation> consultations = consultationRepository.findAll();
        model.addAttribute("consultations", consultations);
        return "dentiste/list-consultations";
    }

    @GetMapping("/consultations/add")
    public String addConsultationForm(Model model) {
        model.addAttribute("consultation", new Consultation());
        model.addAttribute("dossiers", dossierMedicaleRepository.findAll());
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
        model.addAttribute("consultation", consultation);
        model.addAttribute("dossiers", dossierMedicaleRepository.findAll());
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

    // CRUD for InterventionMedecin
    @GetMapping("/interventions")
    public String listInterventions(Model model) {
        List<InterventionMedecin> interventions = interventionMedecinRepository.findAll();
        model.addAttribute("interventions", interventions);
        return "dentiste/list-interventions";
    }

    @GetMapping("/interventions/add")
    public String addInterventionForm(Model model) {
        model.addAttribute("intervention", new InterventionMedecin());
        model.addAttribute("consultations", consultationRepository.findAll());
        model.addAttribute("actes", acteRepository.findAll());
        return "dentiste/add-intervention";
    }

    @PostMapping("/interventions")
    public String saveIntervention(@ModelAttribute("intervention") InterventionMedecin intervention) {
        interventionMedecinRepository.save(intervention);
        return "redirect:/dentiste/dossierMedicale/interventions";
    }

    @GetMapping("/interventions/edit/{id}")
    public String editInterventionForm(@PathVariable Long id, Model model) {
        InterventionMedecin intervention = interventionMedecinRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid intervention Id:" + id));
        model.addAttribute("intervention", intervention);
        model.addAttribute("consultations", consultationRepository.findAll());
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
}
