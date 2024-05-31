package ma.sninati.gestiondossiersmedicaux.services;

import ma.sninati.gestiondossiersmedicaux.entities.Role;
import ma.sninati.gestiondossiersmedicaux.entities.Utilisateur;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    Utilisateur createUser(String username, String email, String password, String confirmPassword);
    Role createRole(String role);
    void addRoleToUser(String username,String role);
    void removeRoleFromUser(String username,String role);
    Utilisateur loadUserByUsername(String username);
}
