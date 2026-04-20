package mon.food.mon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import mon.food.mon.model.Usuario;

@Controller
public class AuthController {
    @Autowired
    private mon.food.mon.service.UsuarioService usuarioService;

    @GetMapping("/login")
    public String showLogin() {
        return "auth/login";
    }

    @GetMapping("/registro")
    public String redirecciónRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registroUsuario(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioService.emailExists(usuario.getEmail())) {
            model.addAttribute("Ups!", "Ya existe una cuenta con ese email.");
            return "auth/registro";
        }
        usuarioService.registrar(usuario);
        return "redirect:/login?registroExitoso";
    }

    @GetMapping("/error/404")
    public String error404() {
        return "error/404";
    }

    @GetMapping("/error/403")
    public String error403() {
        return "error/403";
    }

}
