package mon.food.mon.config;

//import mon.food.mon.service.UsuarioService;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /*@Autowired
    private UsuarioService usuarioService; */
    

    /*@Bean se usa dentro de clases @Configuration */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            //Rutas públicas esté registrado o no
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/","/recetas","/recetas/**").permitAll()
                .requestMatchers("/registro","/login").permitAll()
                .requestMatchers("/css/**","/js/**","/images/**").permitAll()
                .anyRequest().authenticated()
            )
            
            .formLogin(form -> form
                .loginPage("/login")
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
