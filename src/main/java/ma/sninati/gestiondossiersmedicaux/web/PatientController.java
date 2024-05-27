package ma.sninati.gestiondossiersmedicaux.web;

import lombok.AllArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Patient;
import ma.sninati.gestiondossiersmedicaux.repositories.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping(path = "/patients")
    public String patients(Model model){
        List<Patient> patients=patientRepository.findAll();
        model.addAttribute("listePatients",patients);
        return "patients";
    }
}
