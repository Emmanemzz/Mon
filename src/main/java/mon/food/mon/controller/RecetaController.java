package mon.food.mon.controller;

import mon.food.mon.model.Receta;
import mon.food.mon.model.Usuario;

import mon.food.mon.service.ComentarioService;
import mon.food.mon.service.RecetaService;
import mon.food.mon.service.UsuarioService;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/recetas")
public class RecetaController {
    private final ComentarioService comentarioService;
    private final RecetaService recetaService;
    private final UsuarioService usuarioService;


    public RecetaController(RecetaService recetaService, ComentarioService comentarioService, UsuarioService usuarioService){
        this.recetaService = recetaService;
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService;
    }

    // Esto es público, listamos todas las recetas disponibles y filtros
    @GetMapping
    public String listarRecetas(@RequestParam(required = false) String q,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) String tipoDieta,
            @RequestParam(required = false) String alergia,
            @RequestParam(required = false) String tipoPlato,
            Model model) {

        List<Receta> resultadosBusqueda;
        // Sin filtros
        if (q != null && !q.isBlank()) {
        resultadosBusqueda = recetaService.buscarPorTituloOIngredientes(q);
        model.addAttribute("usuarios", usuarioService.buscarPorNombre(q));
    } else if ((pais == null || pais.isBlank()) &&
            (tipoDieta == null || tipoDieta.isBlank()) &&
            (alergia == null || alergia.isBlank()) &&
            (tipoPlato == null || tipoPlato.isBlank())) {
        resultadosBusqueda = recetaService.listarTodas();
    } else {
        if (pais != null && !pais.isBlank()) {
            resultadosBusqueda = recetaService.buscarPorPais(pais);
        } else if (tipoDieta != null && !tipoDieta.isBlank()) {
            resultadosBusqueda = recetaService.buscarPorTipoDieta(tipoDieta);
        } else if (alergia != null && !alergia.isBlank()) {
            resultadosBusqueda = recetaService.buscarPorAlergias(alergia);
        } else {
            resultadosBusqueda = recetaService.buscarPorTipoPlato(tipoPlato);
        }
    }
        model.addAttribute("recetas", resultadosBusqueda);
        model.addAttribute("q", q);
        model.addAttribute("pais", pais);
        model.addAttribute("tipoDieta", tipoDieta);
        model.addAttribute("alergia", alergia);
        model.addAttribute("tipoPlato", tipoPlato);

        return "recetas/lista";

    }

    // Esto es público, ver detalle de una receta
    @GetMapping("/{id}")
    public String verReceta(@PathVariable Long id,
                            @AuthenticationPrincipal Usuario usuarioActual,
                            Model model) {
        Optional<Receta> receta = recetaService.listarPorId(id);
        if (receta.isEmpty()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND);
        }
        model.addAttribute("receta", receta.get());
        model.addAttribute("comentarios", comentarioService.obtenerPorReceta(receta.get()));

        if (usuarioActual != null) {
            Usuario usuarioRecargado = usuarioService.buscarPorEmail(usuarioActual.getEmail());
            model.addAttribute("usuarioActual", usuarioRecargado);
        }
        return "recetas/detalle";
    }

    // Esto es privado, mostramos el formulario para crear receta
    @GetMapping("/nueva")
    @PreAuthorize("isAuthenticated()")
    public String nuevaReceta(Model model) {
        model.addAttribute("receta", new Receta());
        return "recetas/formulario";
    }

    // Esto es privado, guardar la nueva receta
    @PostMapping("/nueva")
    public String guardarReceta(@ModelAttribute Receta receta,
            @AuthenticationPrincipal Usuario autor) {
        recetaService.guardarConAutor(receta, autor);
        return "redirect:/recetas";
    }

    // Sólo lo puede hacer el autor, un formulario para editar una receta propia
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

    // Sólo lo puede hacer el autor, guardar la edición de receta
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

    // Sólo puede hacerlo el autor, eliminar receta
    @PostMapping("/{id}/eliminar")
    public String eliminarReceta(@PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioActual) {

        Optional<Receta> receta = recetaService.listarPorId(id);
        if (receta.isPresent() && receta.get().getAutor().getId().equals(usuarioActual.getId())) {
            recetaService.eliminar(id);
        }
        return "redirect:/recetas";
    }


    //Método guardar receta
    @PostMapping("/{id}/guardar")
    public String guardarReceta(@PathVariable Long id,
                                @AuthenticationPrincipal Usuario usuarioActual) {
        
        Optional<Receta> receta = recetaService.listarPorId(id);
        if (receta.isPresent()) {
            Usuario usuarioRecargado = usuarioService.buscarPorEmail(usuarioActual.getEmail());
            usuarioService.guardarReceta(usuarioRecargado, receta.get());
        }
        return "redirect:/recetas/" + id;
    }
    
    //Método quitar receta
    @PostMapping("/{id}/quitarGuardado")
    public String quitarRecetaGuardada(@PathVariable Long id,
                                        @AuthenticationPrincipal Usuario usuarioActual) {
        Optional<Receta> receta = recetaService.listarPorId(id);
        if (receta.isPresent()) {
            Usuario usuarioRecargado = usuarioService.buscarPorEmail(usuarioActual.getEmail());
            usuarioService.quitarRecetaGuardada(usuarioRecargado, receta.get());
        }
        return "redirect:/recetas/" + id;
    }
    

}
