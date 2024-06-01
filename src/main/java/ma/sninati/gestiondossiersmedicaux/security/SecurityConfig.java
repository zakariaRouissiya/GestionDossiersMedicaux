package ma.sninati.gestiondossiersmedicaux.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .usernameParameter("email")
                        .permitAll()
                )
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/webjars/**", "/vendor/**", "/css/**", "/img/**", "/js/**", "/scss/**").permitAll()
                        .requestMatchers("/admin/**","/editPatient/**").hasRole("ADMIN")
                        .requestMatchers("/patients/**").hasAnyRole("ADMIN", "SECRETAIRE", "DENTISTE")
                        .requestMatchers("/formPatients/**").hasAnyRole("ADMIN", "SECRETAIRE")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/accessDenied"))
                .build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
