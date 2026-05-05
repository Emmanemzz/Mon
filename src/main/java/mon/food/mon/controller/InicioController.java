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
    public String inicio(@AuthenticationPrincipal Usuario usuarioPrincipal, Model model) {
        List<Receta> destacadas = recetaService.listarDestacadas();
        model.addAttribute("destacadas", destacadas);

        if (usuarioPrincipal != null) {
            Usuario usuarioActual = usuarioService.buscarPorEmail(usuarioPrincipal.getUsername());
            List<Receta> todasGuardadas = new ArrayList<>(usuarioActual.getRecetasGuardadas());
            List<Receta> guardadasLimitadas = todasGuardadas.size() > 2 ? todasGuardadas.subList(0, 2) : todasGuardadas;
            model.addAttribute("recetasGuardadas", guardadasLimitadas);
            model.addAttribute("totalGuardadas", todasGuardadas.size());
        } else {
            model.addAttribute("recetasGuardadas", new ArrayList<>());
            model.addAttribute("totalGuardadas", 0);
        }

        return "index";
    }

    @GetMapping("/inicio")
    public String inicioPorNombre(@AuthenticationPrincipal Usuario usuarioPrincipal, Model model) {
        List<Receta> destacadas = recetaService.listarDestacadas();
        model.addAttribute("destacadas", destacadas);

        if (usuarioPrincipal != null) {
            Usuario usuarioActual = usuarioService.buscarPorEmail(usuarioPrincipal.getUsername());
            List<Receta> todasGuardadas = new ArrayList<>(usuarioActual.getRecetasGuardadas());
            List<Receta> guardadasLimitadas = todasGuardadas.size() > 2 ? todasGuardadas.subList(0, 2) : todasGuardadas;
            model.addAttribute("recetasGuardadas", guardadasLimitadas);
            model.addAttribute("totalGuardadas", todasGuardadas.size());
        } else {
            model.addAttribute("recetasGuardadas", new ArrayList<>());
            model.addAttribute("totalGuardadas", 0);
        }

        return "index";
    }
}