package mon.food.mon.service;

import mon.food.mon.model.Usuario;
import mon.food.mon.repository.UsuarioRepository;
import mon.food.mon.model.Receta;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UsuarioService implements UserDetailsService {
    private final RecetaService recetaService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(RecetaService recetaService, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.recetaService = recetaService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Buscamos al usuario mediante el email para ver si existe o no
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        return usuarioRepository.findByEmail(email)
        .orElseThrow(()-> new UsernameNotFoundException("El usuario: "+ email + " no ha sido encontrado"));
    }

    //Registro de usuario
    public void registrar(Usuario usuario){
        /*Uso passwordEncoder para encriptar la contraseña*/
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol("USER");
        usuarioRepository.save(usuario);
    }

    public boolean emailExists(String email){
        return usuarioRepository.findByEmail(email).isPresent();
    }

    //Método para que un usuario guarde una receta
    public void guardarReceta(Usuario usuario, Receta receta){
        if(!usuario.getRecetasGuardadas().contains(receta)){
            usuario.getRecetasGuardadas().add(receta);
            usuarioRepository.save(usuario);
        }
    }

    //Quitar receta de guardados
    public void quitarRecetaGuardada(Usuario usuario, Receta receta){
        usuario.getRecetasGuardadas().remove(receta);
        usuarioRepository.save(usuario);
    }

    public boolean tieneRecetaGuardada(Usuario usuario, Receta receta){
        return usuario.getRecetasGuardadas().contains(receta);
    }

    public Usuario buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }

    public List<Receta> obtenerRecetasPorUsuario(Usuario usuario){
        return recetaService.buscarPorUsuario(usuario);
    }

    public void actualizarPerfil(Usuario usuario, Usuario datosEditados){
        usuario.setNombre(datosEditados.getNombre());
        usuario.setBiografia(datosEditados.getBiografia());
        usuario.setImagenPerfil(datosEditados.getImagenPerfil());
        usuarioRepository.save(usuario);
    }
}
