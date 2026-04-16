package mon.food.mon.service;

import mon.food.mon.model.Comentario;
import mon.food.mon.model.Receta;
import mon.food.mon.model.Usuario;
import mon.food.mon.repository.ComentarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    public List<Comentario> obtenerPorReceta(Receta receta){
        return comentarioRepository.findByRecetaOrderByFechaCreacionComentarioDesc(receta);
    }

    public void guardar(Comentario comentario, Usuario autor, Receta receta){
        if (comentario == null || comentario.getContenido().isBlank()) {
            throw new IllegalArgumentException("El comentario no puede estar vacío");
        }
        comentario.setAutor(autor);
        comentario.setReceta(receta);
        comentarioRepository.save(comentario);
    }
    
    public void eliminar(Long id, Usuario usuarioActual){
        if (id == null) {
            throw new IllegalArgumentException("El id no puede estar vacío");
        }
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        if (comentario.isPresent() && comentario.get().getAutor().getId().equals(usuarioActual.getId())) {
            comentarioRepository.deleteById(id);
        }
    }
}
