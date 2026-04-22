package mon.food.mon.controller;

import mon.food.mon.model.Usuario;
import mon.food.mon.service.RecetaService;
import mon.food.mon.service.UsuarioService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.*;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RecetaService recetaService;

    //Búsqueda de usuarios por nombre
    @GetMapping("/buscar")
    public String buscarUsuarios(@RequestParam(required = false) String nombre, Model model) {
        if (nombre != null && !nombre.isBlank()) {
            List<Usuario> resultados = usuarioService.buscarPorNombre(nombre);
            model.addAttribute("resultados", resultados);
            model.addAttribute("nombre", nombre);
        }
        return "usuarios/buscar";
    }

    //Ver perfil de un usuario
    @GetMapping("/{id}")
    public String verPerfilPublico(@PathVariable Long id, 
                                    @AuthenticationPrincipal Usuario usuarioActual,
                                    Model model) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        if (usuario.isEmpty()) {
            return "redirect:/usuarios/buscar";
        }
        model.addAttribute("usuario", usuario.get());
        model.addAttribute("usuarioActual", usuarioActual);
        model.addAttribute("recetas", recetaService.buscarPorUsuario(usuario.get()));
        return "perfil/perfil";
    }
    
    
}
