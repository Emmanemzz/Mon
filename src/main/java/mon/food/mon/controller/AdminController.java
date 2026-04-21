package mon.food.mon.controller;

import mon.food.mon.service.RecetaService;
import mon.food.mon.model.Receta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RecetaService recetaService;

    // Marcar receta como destacada
    @PostMapping("/recetas/{id}/destacar")
    public String destacarReceta(@PathVariable Long id){
        Optional<Receta> recetaOpt = recetaService.listarPorId(id);
        if(recetaOpt.isPresent()){
            Receta receta = recetaOpt.get();
            receta.setDestacada(true);
            recetaService.guardarConAutor(receta, receta.getAutor());
        }
        return "redirect:/recetas/" + id;
    }

    // Desmarcar receta como destacada
    @PostMapping("/recetas/{id}/quitar-destacada")
    public String quitarDestacada(@PathVariable Long id){
        Optional<Receta> recetaOpt = recetaService.listarPorId(id);
        if(recetaOpt.isPresent()){
            Receta receta = recetaOpt.get();
            receta.setDestacada(false);
            recetaService.guardarConAutor(receta, receta.getAutor());
        }
        return "redirect:/recetas/" + id;
    }
}
