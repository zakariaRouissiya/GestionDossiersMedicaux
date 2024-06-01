package ma.sninati.gestiondossiersmedicaux.web;

import lombok.AllArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.UserForm;
import ma.sninati.gestiondossiersmedicaux.services.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    private CustomUserDetailsService userDetailsService;

    @GetMapping("/admin")
    public String listUsers(Model model) {
        model.addAttribute("users", userDetailsService.listUsers());
        return "users";
    }

    @GetMapping("/admin/form")
    public String formUser(Model model) {
        model.addAttribute("user", new UserForm());
        return "formUser";
    }

    @PostMapping("/admin/save")
    public String saveUser(UserForm userForm) {
        userDetailsService.createUser(userForm.getUsername(), userForm.getPassword(), userForm.getRoles());
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit")
    public String editUser(@RequestParam String username, Model model) {
        var user = userDetailsService.loadUserByUsername(username);
        var userForm = new UserForm(user.getUsername(), "", user.getAuthorities().stream().map(a -> a.getAuthority()).toArray(String[]::new));
        model.addAttribute("user", userForm);
        return "editUser";
    }

    @PostMapping("/admin/update")
    public String updateUser(UserForm userForm) {
        userDetailsService.updateUser(userForm.getUsername(), userForm.getPassword(), userForm.getRoles());
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam String username) {
        userDetailsService.deleteUser(username);
        return "redirect:/admin";
    }
}
