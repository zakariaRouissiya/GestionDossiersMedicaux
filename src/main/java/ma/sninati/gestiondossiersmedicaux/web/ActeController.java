package ma.sninati.gestiondossiersmedicaux.web;

import ma.sninati.gestiondossiersmedicaux.entities.Acte;
import ma.sninati.gestiondossiersmedicaux.repositories.ActeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dentiste/actes")
public class ActeController {

    @Autowired
    private ActeRepository acteRepository;

    @GetMapping
    public String listActes(Model model) {
        List<Acte> actes = acteRepository.findAll();
        model.addAttribute("actes", actes);
        return "dentiste/list-actes";
    }

    @GetMapping("/add")
    public String addActeForm(Model model) {
        model.addAttribute("acte", new Acte());
        return "dentiste/add-acte";
    }

    @PostMapping
    public String saveActe(@ModelAttribute("acte") Acte acte) {
        acteRepository.save(acte);
        return "redirect:/dentiste/actes";
    }

    @GetMapping("/edit/{id}")
    public String editActeForm(@PathVariable Long id, Model model) {
        Acte acte = acteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid acte Id:" + id));
        model.addAttribute("acte", acte);
        return "dentiste/edit-acte";
    }

    @PostMapping("/{id}")
    public String updateActe(@PathVariable Long id, @ModelAttribute("acte") Acte acte) {
        Acte existingActe = acteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid acte Id:" + id));
        existingActe.setPrixDeBase(acte.getPrixDeBase());
        existingActe.setCategorie(acte.getCategorie());
        existingActe.setLibelle(acte.getLibelle());
        acteRepository.save(existingActe);
        return "redirect:/dentiste/actes";
    }

    @GetMapping("/delete/{id}")
    public String deleteActe(@PathVariable Long id) {
        Acte acte = acteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid acte Id:" + id));
        acteRepository.delete(acte);
        return "redirect:/dentiste/actes";
    }
}
