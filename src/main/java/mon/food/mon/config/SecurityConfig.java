package mon.food.mon.config;

import mon.food.mon.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@Bean se usa dentro de clases @Configuration */
  
    @Bean
    public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(passwordEncoder);
    provider.setUserDetailsService(usuarioService);
    return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            //Rutas públicas esté registrado o no
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/","/inicio","/recetas","/recetas/**").permitAll()
                .requestMatchers("/registro","/login", "/error").permitAll()
                .requestMatchers("/css/**","/js/**","/images/**").permitAll()
                .anyRequest().authenticated()
            )
            
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/perfil", true) //Si el login es correcto nos redirige al perfil
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/inicio") //Tras cerrar sesión nos llleva al inicio de la página
                .permitAll()
            );
            return http.build();
    }

}
