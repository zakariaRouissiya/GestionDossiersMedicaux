package ma.sninati.gestiondossiersmedicaux.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.sninati.gestiondossiersmedicaux.entities.Role;
import ma.sninati.gestiondossiersmedicaux.entities.Utilisateur;
import ma.sninati.gestiondossiersmedicaux.repositories.RoleRepository;
import ma.sninati.gestiondossiersmedicaux.repositories.UtilisateurRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
@Transactional
public class AccountServiceImp implements AccountService{
    private UtilisateurRepository utilisateurRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Utilisateur createUser(String username, String email, String password, String confirmPassword) {
        Utilisateur usr = utilisateurRepository.findByUsername(username);
        if (usr!=null) throw new RuntimeException("Utilisateur existe deja");
        if (!password.equals(confirmPassword)) throw new RuntimeException("Mot De Passe Incorrecte");
        Utilisateur user = new Utilisateur();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        return utilisateurRepository.save(user);
    }

    @Override
    public Role createRole(String role) {
        Role role1=roleRepository.findById(role).orElse(null);
         role1 = Role.builder()
                .role(role)
                .build();
        return roleRepository.save(role1);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        Utilisateur usr = utilisateurRepository.findByUsername(username);
        Role role1=roleRepository.findById(role).orElse(null);
        usr.getRoles().add(role1);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        Utilisateur usr = utilisateurRepository.findByUsername(username);
        Role role1=roleRepository.findById(role).orElse(null);
        usr.getRoles().remove(role1);
    }

    @Override
    public Utilisateur loadUserByUsername(String username) {
        return utilisateurRepository.findByUsername(username);
    }
}
