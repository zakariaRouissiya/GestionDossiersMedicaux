package ma.sninati.gestiondossiersmedicaux.web;

import lombok.AllArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Dentiste;
import ma.sninati.gestiondossiersmedicaux.entities.DossierMedicale;
import ma.sninati.gestiondossiersmedicaux.entities.Patient;
import ma.sninati.gestiondossiersmedicaux.repositories.DossierMedicaleRepository;
import ma.sninati.gestiondossiersmedicaux.repositories.PatientRepository;
import ma.sninati.gestiondossiersmedicaux.repositories.UtilisateurRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    private DossierMedicaleRepository dossierMedicaleRepository;
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/patients";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping(path = "/patients")
    public String patients(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Patient> pagePatients = patientRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listePatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patients";
    }

    @GetMapping("/delete")
    public String delete(Long id, String keyword, int page) {
        patientRepository.deleteById(id);
        return "redirect:/patients?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/pat")
    @ResponseBody
    public List<Patient> listPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/formPatients")
    public String formPatients(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("dentistes", utilisateurRepository.findAll().stream()
                .filter(u -> u instanceof Dentiste)
                .map(u -> (Dentiste) u).toList());
        return "formPatients";
    }

    @PostMapping("/save")
    public String save(Model model, Patient patient, @RequestParam(name = "dentisteId") Long dentisteId,
                       @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        patient = patientRepository.save(patient);

        // Create and save a new DossierMedicale linked to the new patient and assigned dentist
        DossierMedicale dossierMedicale = new DossierMedicale();
        dossierMedicale.setPatient(patient);
        Dentiste dentiste = (Dentiste) utilisateurRepository.findById(dentisteId).orElse(null);
        dossierMedicale.setMedecinTraitant(dentiste);
        dossierMedicaleRepository.save(dossierMedicale);

        return "redirect:/patients?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/editPatient")
    public String editPatient(Model model, Long id, int page, String keyword) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) throw new RuntimeException("Patient Introuvable");
        model.addAttribute("patient", patient);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("dentistes", utilisateurRepository.findAll().stream()
                .filter(u -> u instanceof Dentiste)
                .map(u -> (Dentiste) u).toList());
        return "editPatient";
    }
}
