package ma.sninati.gestiondossiersmedicaux.web;

import ma.sninati.gestiondossiersmedicaux.entities.*;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.Role;
import ma.sninati.gestiondossiersmedicaux.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String adminHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Utilisateur user = customUserDetails.getUtilisateur();
        model.addAttribute("user", user);

        List<Utilisateur> users = utilisateurRepository.findAll();
        model.addAttribute("users", users);

        return "admin/home";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<Utilisateur> users = utilisateurRepository.findAll();
        model.addAttribute("users", users);
        return "admin/list-users";
    }

    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new Utilisateur());
        return "admin/add-user";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") Utilisateur user) {
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        switch (user.getRole()) {
            case ADMIN:
                utilisateurRepository.save(new Admin(user));
                user.setRole(Role.ADMIN);
                break;
            case DENTISTE:
                utilisateurRepository.save(new Dentiste(user));
                user.setRole(Role.DENTISTE);
                break;
            case SECRETAIRE:
                utilisateurRepository.save(new Secretaire(user));
                user.setRole(Role.SECRETAIRE);
                break;
            default:
                utilisateurRepository.save(user);
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Utilisateur user = utilisateurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") Utilisateur user, Model model) {
        Utilisateur existingUser = utilisateurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        existingUser.setNom(user.getNom());
        existingUser.setPrenom(user.getPrenom());
        existingUser.setAdresse(user.getAdresse());
        existingUser.setTelephone(user.getTelephone());
        existingUser.setEmail(user.getEmail());
        existingUser.setCin(user.getCin());
        existingUser.setNomUtilisateur(user.getNomUtilisateur());
        existingUser.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        existingUser.setRole(user.getRole());

        utilisateurRepository.save(existingUser);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        Utilisateur user = utilisateurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        utilisateurRepository.delete(user);
        return "redirect:/admin/users";
    }

    // CRUD operations for Dentiste
    @GetMapping("/dentistes")
    public String listDentistes(Model model) {
        List<Dentiste> dentistes = utilisateurRepository.findAll()
                .stream()
                .filter(u -> u instanceof Dentiste)
                .map(u -> (Dentiste) u)
                .toList();
        model.addAttribute("dentistes", dentistes);
        return "admin/list-dentistes";
    }

    @GetMapping("/dentistes/add")
    public String addDentisteForm(Model model) {
        model.addAttribute("dentiste", new Dentiste());
        return "admin/add-dentiste";
    }

    @PostMapping("/dentistes")
    public String saveDentiste(@ModelAttribute("dentiste") Dentiste dentiste) {
        dentiste.setMotDePasse(passwordEncoder.encode(dentiste.getMotDePasse()));
        dentiste.setRole(Role.DENTISTE);

        utilisateurRepository.save(dentiste);

        return "redirect:/admin/dentistes";
    }

    @GetMapping("/dentistes/edit/{id}")
    public String editDentisteForm(@PathVariable Long id, Model model) {
        Dentiste dentiste = (Dentiste) utilisateurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dentiste Id:" + id));
        model.addAttribute("dentiste", dentiste);
        return "admin/edit-dentiste";
    }

    @PostMapping("/dentistes/{id}")
    public String updateDentiste(@PathVariable Long id, @ModelAttribute("dentiste") Dentiste dentiste, Model model) {
        Dentiste existingDentiste = (Dentiste) utilisateurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dentiste Id:" + id));
        existingDentiste.setNom(dentiste.getNom());
        existingDentiste.setPrenom(dentiste.getPrenom());
        existingDentiste.setAdresse(dentiste.getAdresse());
        existingDentiste.setTelephone(dentiste.getTelephone());
        existingDentiste.setEmail(dentiste.getEmail());
        existingDentiste.setCin(dentiste.getCin());
        existingDentiste.setNomUtilisateur(dentiste.getNomUtilisateur());
        existingDentiste.setMotDePasse(passwordEncoder.encode(dentiste.getMotDePasse()));
        existingDentiste.setRole(dentiste.getRole());
        existingDentiste.setDateRetourConge(dentiste.getDateRetourConge());
        existingDentiste.setSalaireDeBase(dentiste.getSalaireDeBase());
        existingDentiste.setSpecialite(dentiste.getSpecialite());
        existingDentiste.setDisponibilite(dentiste.getDisponibilite());
        existingDentiste.setAssurance(dentiste.getAssurance());
        existingDentiste.setStatusActuel(dentiste.getStatusActuel());

        utilisateurRepository.save(existingDentiste);
        return "redirect:/admin/dentistes";
    }

    @GetMapping("/dentistes/delete/{id}")
    public String deleteDentiste(@PathVariable Long id) {
        Dentiste dentiste = (Dentiste) utilisateurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dentiste Id:" + id));
        utilisateurRepository.delete(dentiste);
        return "redirect:/admin/dentistes";
    }

    // CRUD operations for Secretaire
    @GetMapping("/secretaires")
    public String listSecretaires(Model model) {
        List<Secretaire> secretaires = utilisateurRepository.findAll()
                .stream()
                .filter(u -> u instanceof Secretaire)
                .map(u -> (Secretaire) u)
                .toList();
        model.addAttribute("secretaires", secretaires);
        return "admin/list-secretaires";
    }

    @GetMapping("/secretaires/add")
    public String addSecretaireForm(Model model) {
        model.addAttribute("secretaire", new Secretaire());
        return "admin/add-secretaire";
    }

    @PostMapping("/secretaires")
    public String saveSecretaire(@ModelAttribute("secretaire") Secretaire secretaire) {
        secretaire.setMotDePasse(passwordEncoder.encode(secretaire.getMotDePasse()));
        secretaire.setRole(Role.SECRETAIRE);

        utilisateurRepository.save(secretaire);

        return "redirect:/admin/secretaires";
    }

    @GetMapping("/secretaires/edit/{id}")
    public String editSecretaireForm(@PathVariable Long id, Model model) {
        Secretaire secretaire = (Secretaire) utilisateurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid secretaire Id:" + id));
        model.addAttribute("secretaire", secretaire);
        return "admin/edit-secretaire";
    }

    @PostMapping("/secretaires/{id}")
    public String updateSecretaire(@PathVariable Long id, @ModelAttribute("secretaire") Secretaire secretaire, Model model) {
        Secretaire existingSecretaire = (Secretaire) utilisateurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid secretaire Id:" + id));
        existingSecretaire.setNom(secretaire.getNom());
        existingSecretaire.setPrenom(secretaire.getPrenom());
        existingSecretaire.setAdresse(secretaire.getAdresse());
        existingSecretaire.setTelephone(secretaire.getTelephone());
        existingSecretaire.setEmail(secretaire.getEmail());
        existingSecretaire.setCin(secretaire.getCin());
        existingSecretaire.setNomUtilisateur(secretaire.getNomUtilisateur());
        existingSecretaire.setMotDePasse(passwordEncoder.encode(secretaire.getMotDePasse()));
        existingSecretaire.setRole(secretaire.getRole());
        existingSecretaire.setDateRetourConge(secretaire.getDateRetourConge());
        existingSecretaire.setSalaireDeBase(secretaire.getSalaireDeBase());
        existingSecretaire.setAssurance(secretaire.getAssurance());
        existingSecretaire.setStatusActuel(secretaire.getStatusActuel());
        existingSecretaire.setPrime(secretaire.getPrime());

        utilisateurRepository.save(existingSecretaire);
        return "redirect:/admin/secretaires";
    }

    @GetMapping("/secretaires/delete/{id}")
    public String deleteSecretaire(@PathVariable Long id) {
        Secretaire secretaire = (Secretaire) utilisateurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid secretaire Id:" + id));
        utilisateurRepository.delete(secretaire);
        return "redirect:/admin/secretaires";
    }
}
