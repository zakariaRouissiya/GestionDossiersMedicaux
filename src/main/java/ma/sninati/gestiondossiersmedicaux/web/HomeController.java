package ma.sninati.gestiondossiersmedicaux.web;

import ma.sninati.gestiondossiersmedicaux.entities.CustomUserDetails;
import ma.sninati.gestiondossiersmedicaux.entities.Enums.Role;
import ma.sninati.gestiondossiersmedicaux.entities.Utilisateur;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur user = ((CustomUserDetails) authentication.getPrincipal()).getUtilisateur();
        if(user.getRole() == Role.ADMIN){
            return "redirect:/admin";
        } else if (user.getRole() == Role.DENTISTE) {
            return "redirect:/dentiste";
        } else if (user.getRole() == Role.SECRETAIRE) {
            return "redirect:/secretaire";
        }else {
            return "redirect:/login";
        }

    }


    @GetMapping("/dentiste")
    public String dentisteHome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur user = ((CustomUserDetails) authentication.getPrincipal()).getUtilisateur();
        if(user.getRole() == Role.DENTISTE)
            return "Dentiste/home";

        return "redirect:/home";
    }


    @GetMapping("/secretaire")
    public String secretaireHome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur user = ((CustomUserDetails) authentication.getPrincipal()).getUtilisateur();
        if(user.getRole() == Role.SECRETAIRE)
            return "Secretaire/home";
        return "redirect:/home";
    }


    @GetMapping("/admin")
    public String adminHome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur user = ((CustomUserDetails) authentication.getPrincipal()).getUtilisateur();
        if(user.getRole() == Role.ADMIN)
            return "admin/home";
        return "redirect:/home";
    }



}
