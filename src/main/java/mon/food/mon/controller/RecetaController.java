package mon.food.mon.controller;

import mon.food.mon.model.Receta;
import mon.food.mon.model.Usuario;

import mon.food.mon.service.RecetaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;





@Controller
@RequestMapping("/recetas")
public class RecetaController {
    @Autowired
    private RecetaService recetaService;

    //Esto es público, listamos todas las recetas disponibles
    @GetMapping
    public String listarRecetas(Model model) {
        List<Receta> recetas = recetaService.listarTodas();
        model.addAttribute("recetas", recetas);
        return "recetas/lista";
    }
    /* @GetMapping("/recetas")
    public String listarRecetas(Model model) {
        List<Receta> recetas = recetaService.listarTodas();
        model.addAttribute("recetas", recetas);
        return "recetas";
    }*/
    
    //Esto es público, ver detalle de una receta
    @GetMapping("/{id}")
    public String verReceta(@PathVariable Long id, Model model) {
        Optional<Receta> receta = recetaService.listarPorId(id);
        if (receta.isEmpty()) {
            return "redirect:/recetas";
        }
        model.addAttribute("receta", receta.get());
        return "recetas/detalle";
    }

    //Esto es privado, mostramos el formulario para crear receta
    @GetMapping("/nueva")
    public String nuevaReceta(Model model) {
        model.addAttribute("receta", new Receta());
        return "recetas/formulario";
    }
    
    //Esto es privado, guardar la nueva receta
    @PostMapping("/nueva")
    public String guardarReceta(@ModelAttribute Receta receta,
        @AuthenticationPrincipal Usuario autor) {
            recetaService.guardarConAutor(receta, autor);
        return "redirect:/recetas";
    }
    
    //Sólo lo puede hacer el autor, un formulario para editar una receta propia
    @GetMapping("/{id}/editar")
    public String editarReceta(@PathVariable Long id,
        @AuthenticationPrincipal Usuario usuarioActual,
        Model model) {
            
            Optional<Receta> receta = recetaService.listarPorId(id);
            if (receta.isEmpty() || !receta.get().getAutor().getId().equals(usuarioActual.getId())) {
                return "redirect:/recetas";
            }
            model.addAttribute("receta", receta.get());
            return "recetas/formulario";
    }

    //Sólo lo puede hacer el autor, guardar la edición de receta
    @PostMapping("/{id}/editar")
    public String guardarRecetaEditada(@PathVariable Long id,
        @ModelAttribute Receta recetaEditada,
        @AuthenticationPrincipal Usuario usuarioActual) {
            
            Optional<Receta> receta = recetaService.listarPorId(id);
            if (receta.isEmpty() || !receta.get().getAutor().getId().equals(usuarioActual.getId())) {
                return "redirect:/recetas";  
            }
            recetaEditada.setId(id);
            recetaService.guardarConAutor(recetaEditada, usuarioActual);
            return "redirect:/recetas/" + id;
    }

    //Sólo puede hacerlo el autor, eliminar receta
    @PostMapping("/{id}/eliminar")
    public String eliminarReceta(@PathVariable Long id,
        @AuthenticationPrincipal Usuario usuarioActual) {
            
            Optional<Receta> receta = recetaService.listarPorId(id);
            if (receta.isPresent() && receta.get().getAutor().getId().equals(usuarioActual.getId())) {
                recetaService.eliminar(id); 
            }
            return "redirect:/recetas";
    }
    
    //Público, búsqueda con filtros
    @GetMapping("/buscar")
    public String search(@RequestParam(required = false) String ingrediente,
                        @RequestParam(required = false) String pais,
                        @RequestParam(required = false) String tipoDieta,
                        @RequestParam(required = false) String alergia,
                        Model model) {
        List<Receta> resultadosBusqueda;
        if (ingrediente != null && !ingrediente.isBlank()) {
            resultadosBusqueda = recetaService.buscarPorIngredientes(ingrediente);
        }else if (pais != null && !pais.isBlank()) {
            resultadosBusqueda = recetaService.buscarPorPais(pais);
        }else if (tipoDieta != null && !tipoDieta.isBlank()) {
            resultadosBusqueda = recetaService.buscarPorTipoDieta(tipoDieta);
        }else if (alergia != null && !alergia.isBlank()) {
            resultadosBusqueda = recetaService.buscarPorAlergias(alergia);
        }else{
            resultadosBusqueda = recetaService.listarTodas();
        }
        model.addAttribute("recetas", resultadosBusqueda);
        model.addAttribute("ingrediente", ingrediente);
        model.addAttribute("pais", pais);
        model.addAttribute("tipoDieta", tipoDieta);
        model.addAttribute("alergia", alergia);

        return "recetas/buscar";
    }

    
}
