package ma.sninati.gestiondossiersmedicaux.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.security.provisioning.UserDetailsManager;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(String username, String password, String[] roles) {
        UserDetails user = User.withUsername(username)
                .password(passwordEncoder.encode(password))
                .roles(roles)
                .build();
        userDetailsManager.createUser(user);
    }

    public List<UserDetails> listUsers() {
        List<UserDetails> users = new ArrayList<>();

        /*for (UserDetails loadedUser : userDetailsManager.loadAllUsers()) {
            users.add(loadedUser);
        }*/

        return users;
    }


    public void updateUser(String username, String password, String[] roles) {
        UserDetails user = User.withUsername(username)
                .password(passwordEncoder.encode(password))
                .roles(roles)
                .build();
        userDetailsManager.updateUser(user);
    }

    public void deleteUser(String username) {
        userDetailsManager.deleteUser(username);
    }

    public UserDetails loadUserByUsername(String username) {
        return userDetailsManager.loadUserByUsername(username);
    }
}
