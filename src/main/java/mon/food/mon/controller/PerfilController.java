package mon.food.mon.controller;

import mon.food.mon.model.Receta;
import mon.food.mon.model.Usuario;
import mon.food.mon.service.UsuarioService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping("/perfil")
public class PerfilController {
    @Autowired
    private UsuarioService usuarioService;

    //El usuario ve su propio perfil
@GetMapping
public String verPerfil(@AuthenticationPrincipal Usuario usuarioActual, Model model) {
    Usuario usuario = usuarioService.buscarPorEmail(usuarioActual.getEmail());
    List<Receta> todasLasRecetas = usuarioService.obtenerRecetasPorUsuario(usuario);
    List<Receta> todasLasGuardadas = new ArrayList<>(usuario.getRecetasGuardadas());

    model.addAttribute("usuario", usuario);
    model.addAttribute("usuarioActual", usuario);
    model.addAttribute("recetas", todasLasRecetas.size() > 6 ? todasLasRecetas.subList(0, 6) : todasLasRecetas);
    model.addAttribute("totalRecetas", todasLasRecetas.size());
    model.addAttribute("recetasGuardadas", todasLasGuardadas.size() > 6 ? todasLasGuardadas.subList(0, 6) : todasLasGuardadas);
    model.addAttribute("totalGuardadas", todasLasGuardadas.size());
    return "perfil/perfil";
}

    //Formulario de edición de perfil
    @GetMapping("/editar")
    public String editarPerfil(@AuthenticationPrincipal Usuario usuarioActual, Model model ) {
        Usuario usuario = usuarioService.buscarPorEmail(usuarioActual.getEmail());
        model.addAttribute("usuario", usuario);
        return "perfil/editar";
    }

    //Guardar cambios en el perfil
    @PostMapping("/editar")
    public String guardarEdicion(@AuthenticationPrincipal Usuario usuarioActual,
                                    @ModelAttribute Usuario datosEditados) {
        Usuario usuario = usuarioService.buscarPorEmail(usuarioActual.getEmail());
        usuarioService.actualizarPerfil(usuario, datosEditados);
        return "redirect:/perfil";
    }
    
    //Listado recetas propias del usuario 
    @GetMapping("/mis-recetas")
    public String misRecetas(@AuthenticationPrincipal Usuario usuarioActual, Model model) {
        Usuario usuario = usuarioService.buscarPorEmail(usuarioActual.getEmail());
        model.addAttribute("recetas", usuarioService.obtenerRecetasPorUsuario(usuario));
        return "perfil/mis-recetas";
    }
    
    //Listado recetas guardadas de otros usuarios
    @GetMapping("/recetas-guardadas")
    public String recetasGuardadas(@AuthenticationPrincipal Usuario usuarioActual, Model model) {
        Usuario usuario = usuarioService.buscarPorEmail(usuarioActual.getEmail());
        model.addAttribute("recetasGuardadas", usuario.getRecetasGuardadas());
        return "perfil/recetas-guardadas";
    }
    
    
}
