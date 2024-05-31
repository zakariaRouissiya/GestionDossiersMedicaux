package ma.sninati.gestiondossiersmedicaux.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
        return new InMemoryUserDetailsManager(
                User.withUsername("zakaria").password(passwordEncoder.encode("1234")).roles("DENTISTE").build(),
                User.withUsername("bouhaddou").password(passwordEncoder.encode("1234")).roles("SECRETAIRE").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("ADMINISTRATEUR").build()
        );
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/patients", true)
                        .permitAll()
                )
                .authorizeHttpRequests(ar -> ar
                        .requestMatchers("/webjars/**","/vendor/**","/css/**","/img/**","/js/**","/scss/**").permitAll()
                        .requestMatchers("/delete/**", "/editPatient/**").hasRole("ADMINISTRATEUR")
                        .requestMatchers("/patients/**").hasAnyRole("ADMINISTRATEUR", "SECRETAIRE", "DENTISTE")
                        .requestMatchers("/formPatients/**").hasAnyRole("ADMINISTRATEUR", "SECRETAIRE")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ar->ar.accessDeniedPage("/accessDenied"))
                .build();
    }
}
