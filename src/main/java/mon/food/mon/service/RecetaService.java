package mon.food.mon.service;

import mon.food.mon.model.Receta;
import mon.food.mon.model.Usuario;

import mon.food.mon.repository.RecetaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {
    @Autowired
    private RecetaRepository recetaRepository;

    public List<Receta> listarTodas(){
        return recetaRepository.findAll();
    }

    public Optional<Receta> listarPorId(Long id){
        if (id == null){
            throw new IllegalArgumentException("El id no puede estar vacío");
        }
        return recetaRepository.findById(id);
    }

    /*public Receta guardar(Receta receta){
        if (receta == null){
            throw new IllegalArgumentException("La receta no puede estar vacía");
        }
        return recetaRepository.save(receta);
    } */
    public Receta guardarConAutor(Receta receta, Usuario autor){
        if (receta == null) {
            throw new IllegalArgumentException("La receta no puede estar vacía");
        }
        if (autor == null) {
            throw new IllegalArgumentException("El autor no puede estar vacío");            
        }
        receta.setAutor(autor);
        return recetaRepository.save(receta);
    }

    //Cuando avance lo quito de comentarios
    public void eliminar(Long id){
        if (id == null) {
            throw new IllegalArgumentException("El id no puede estar vacío");
        }
        recetaRepository.deleteById(id);
    }

    public List<Receta> buscarPorPais(String pais){
        return recetaRepository.findByCountry(pais);
    }

    public List<Receta> buscarPorUsuario(Usuario usuario){
        return recetaRepository.findByAutor(usuario);
    }

    
    
}
