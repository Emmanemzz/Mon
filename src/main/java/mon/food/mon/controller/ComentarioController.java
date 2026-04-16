package mon.food.mon.controller;

import mon.food.mon.model.Comentario;
import mon.food.mon.model.Receta;
import mon.food.mon.model.Usuario;
import mon.food.mon.service.ComentarioService;
import mon.food.mon.service.RecetaService;
import mon.food.mon.service.UsuarioService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;




@Controller
@RequestMapping("/comentarios")
public class ComentarioController {
    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/nueva")
    public String publicarComentario(@RequestParam Long recetaId,
                                        @RequestParam String contenido,
                                        @AuthenticationPrincipal Usuario usuarioActual) {
        Optional<Receta> receta = recetaService.listarPorId(recetaId);
        if (receta.isPresent()) {
            Usuario usuarioRecargado = usuarioService.buscarPorEmail(usuarioActual.getEmail());
            Comentario comentario = new Comentario();
            comentario.setContenido(contenido);
            comentarioService.guardar(comentario, usuarioRecargado, receta.get());
        }
        return "redirect:/recetas/" + recetaId;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarComentario(@PathVariable Long id,
                                    @RequestParam Long recetaId,
                                    @AuthenticationPrincipal Usuario usuarioActual) {
        Usuario usuarioRecargado = usuarioService.buscarPorEmail(usuarioActual.getEmail());
        comentarioService.eliminar(id, usuarioRecargado);
        return "redirect:/recetas/" + recetaId;
    }
    
    
}
