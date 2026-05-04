package mon.food.mon.controller;

import mon.food.mon.model.Receta;
import mon.food.mon.model.Usuario;

import mon.food.mon.service.ComentarioService;
import mon.food.mon.service.RecetaService;
import mon.food.mon.service.UsuarioService;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/recetas")
public class RecetaController {
    private final ComentarioService comentarioService;
    private final RecetaService recetaService;
    private final UsuarioService usuarioService;

    public RecetaController(RecetaService recetaService, ComentarioService comentarioService,
            UsuarioService usuarioService) {
        this.recetaService = recetaService;
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService;
    }

    // Esto es público, listamos todas las recetas disponibles y filtros
    @GetMapping
    public String listarRecetas(@RequestParam(required = false) String q,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) String tipoDieta,
            @RequestParam(required = false) List<String> alergia,
            @RequestParam(required = false) String tipoPlato,
            @RequestParam(required = false) String tiempoPreparacion,
            @RequestParam(required = false) String dificultad,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 12);
        Page<Receta> resultado;

        if (q != null && !q.isBlank()) {
            List<Usuario> usuariosEncontrados = usuarioService.buscarPorNombre(q);
            model.addAttribute("usuarios", usuariosEncontrados);
            if (usuariosEncontrados.size() == 1) {
                resultado = Page.empty(pageable);
                model.addAttribute("recetas", recetaService.buscarPorUsuario(usuariosEncontrados.get(0)));
            } else {
                resultado = recetaService.buscarPorTituloOIngredientesPaginado(q, pageable);
                model.addAttribute("recetas", resultado.getContent());
            }
        } else if (pais != null && !pais.isBlank()) {
            resultado = recetaService.buscarPorPaisPaginado(pais, pageable);
            model.addAttribute("recetas", resultado.getContent());
        } else if (tipoDieta != null && !tipoDieta.isBlank()) {
            resultado = recetaService.buscarPorTipoDietaPaginado(tipoDieta, pageable);
            model.addAttribute("recetas", resultado.getContent());
        } else if (alergia != null && !alergia.isEmpty()) {
            resultado = recetaService.buscarPorAlergiasPaginado(alergia.get(0), pageable);
            model.addAttribute("recetas", resultado.getContent());
        } else if (tipoPlato != null && !tipoPlato.isBlank()) {
            resultado = recetaService.buscarPorTipoplatoPaginado(tipoPlato, pageable);
            model.addAttribute("recetas", resultado.getContent());
        } else if (tiempoPreparacion != null && !tiempoPreparacion.isBlank()) {
            resultado = recetaService.buscarPorTiempoPreparacionPaginado(tiempoPreparacion, pageable);
            model.addAttribute("recetas", resultado.getContent());
        } else if (dificultad != null && !dificultad.isBlank()) {
            resultado = recetaService.buscarPorDificultadPaginado(dificultad, pageable);
            model.addAttribute("recetas", resultado.getContent());
        } else {
            resultado = recetaService.listarTodasPaginadas(pageable);
            model.addAttribute("recetas", resultado.getContent());
        }

        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", resultado.getTotalPages());
        model.addAttribute("q", q);
        model.addAttribute("pais", pais);
        model.addAttribute("tipoDieta", tipoDieta);
        model.addAttribute("alergia", alergia != null ? String.join(",", alergia) : null);
        model.addAttribute("tipoPlato", tipoPlato);
        model.addAttribute("tiempoPreparacion", tiempoPreparacion);
        model.addAttribute("dificultad", dificultad);

        return "recetas/lista";
    }

    // Esto es público, ver detalle de una receta
    @GetMapping("/{id}")
    public String verReceta(@PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioActual,
            Model model) {
        Optional<Receta> receta = recetaService.listarPorId(id);
        if (receta.isEmpty()) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND);
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
            @AuthenticationPrincipal Usuario autor,
            @RequestParam(value = "alergias", required = false) List<String> alergias) {
        if (alergias != null) {
            receta.setAlergias(String.join(",", alergias));
        } else {
            receta.setAlergias(null);
        }
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
            @AuthenticationPrincipal Usuario usuarioActual,
            @RequestParam(value = "alergias", required = false) List<String> alergias) {

        Optional<Receta> receta = recetaService.listarPorId(id);
        if (receta.isEmpty() || !receta.get().getAutor().getId().equals(usuarioActual.getId())) {
            return "redirect:/recetas";
        }
        if (alergias != null) {
            recetaEditada.setAlergias(String.join(",", alergias));
        } else {
            recetaEditada.setAlergias(null);
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

    // Método guardar receta
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

    // Método quitar receta
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
