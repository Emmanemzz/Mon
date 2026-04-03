package mon.food.mon.controller;

import mon.food.mon.model.Receta;
import mon.food.mon.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@Controller
public class RecetaController {
    @Autowired
    private RecetaService recetaService;


   @GetMapping("/")
    public String inicio() {
        return "inicio";
    } 
    
    @GetMapping("/recetas")
    public String listarRecetas(Model model) {
        List<Receta> recetas = recetaService.listarTodas();
        model.addAttribute("recetas", recetas);
        return "recetas";
    }
    
}
