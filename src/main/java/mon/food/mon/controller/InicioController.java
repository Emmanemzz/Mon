package mon.food.mon.controller;

import mon.food.mon.service.RecetaService;
import mon.food.mon.service.UsuarioService;
import mon.food.mon.model.Receta;
import mon.food.mon.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InicioController {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String inicio(@AuthenticationPrincipal Usuario usuarioActual, Model model) {
        List<Receta> destacadas = recetaService.listarDestacadas();
        model.addAttribute("destacadas", destacadas);

        if (usuarioActual != null) {
            Usuario usuarioFresh = usuarioService.buscarPorEmail(usuarioActual.getUsername());
            model.addAttribute("recetasGuardadas", usuarioFresh.getRecetasGuardadas());
        } else {
            model.addAttribute("recetasGuardadas", new ArrayList<>());
        }

        return "index";
    }

    @GetMapping("/inicio")
    public String inicioPorNombre(@AuthenticationPrincipal Usuario usuarioActual, Model model) {
        List<Receta> destacadas = recetaService.listarDestacadas();
        model.addAttribute("destacadas", destacadas);

        if (usuarioActual != null) {
            Usuario usuarioFresh = usuarioService.buscarPorEmail(usuarioActual.getUsername());
            model.addAttribute("recetasGuardadas", usuarioFresh.getRecetasGuardadas());
        } else {
            model.addAttribute("recetasGuardadas", new ArrayList<>());
        }

        return "index";
    }
}