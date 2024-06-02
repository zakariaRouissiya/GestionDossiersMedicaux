package ma.sninati.gestiondossiersmedicaux.services;


import ma.sninati.gestiondossiersmedicaux.entities.CustomUserDetails;
import ma.sninati.gestiondossiersmedicaux.entities.Utilisateur;
import ma.sninati.gestiondossiersmedicaux.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(utilisateur);
    }


    public UserDetails saveUtilisateur(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        return (UserDetails) utilisateurRepository.save(utilisateur);
    }
}

