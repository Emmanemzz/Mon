package mon.food.mon.controller;

import mon.food.mon.model.Usuario;
import mon.food.mon.service.UsuarioService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/perfil")
public class PerfilController {
    @Autowired
    private UsuarioService usuarioService;

    //El usuario ve su propio perfil
    @GetMapping
    public String verPerfil(@AuthenticationPrincipal Usuario usuarioActual, Model model) {
        Usuario usuario = usuarioService.buscarPorEmail(usuarioActual.getEmail());
        model.addAttribute("usuario", usuario);
        model.addAttribute("recetas", usuarioService.obtenerRecetasPorUsuario(usuario));
        model.addAttribute("recetasGuardadas", usuario.getRecetasGuardadas());
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
    
    
    
}
