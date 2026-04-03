package mon.food.mon.service;

import mon.food.mon.model.Usuario;
import mon.food.mon.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Buscamos al usuario mediante el email para ver si existe o no
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        return usuarioRepository.findByEmail(email)
        .orElseThrow(()-> new UsernameNotFoundException("El usuario: "+ email + " no ha sido encontrado"));
    }

    public void registrar(Usuario usuario){
        /*Uso passwordEncoder para encriptar la contraseña*/
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol("USER");
        usuarioRepository.save(usuario);
    }

    public boolean emailExists(String email){
        return usuarioRepository.findByEmail(email).isPresent();
    }
}
