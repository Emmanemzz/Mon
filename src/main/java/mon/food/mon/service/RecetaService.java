package mon.food.mon.service;

import mon.food.mon.model.Receta;
import mon.food.mon.model.Usuario;

import mon.food.mon.repository.RecetaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {
    @Autowired
    private RecetaRepository recetaRepository;

    public List<Receta> listarTodas() {
        return recetaRepository.findAll();
    }

    public Optional<Receta> listarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede estar vacío");
        }
        return recetaRepository.findById(id);
    }

    /*
     * public Receta guardar(Receta receta){
     * if (receta == null){
     * throw new IllegalArgumentException("La receta no puede estar vacía");
     * }
     * return recetaRepository.save(receta);
     * }
     */
    // Método para guardar receta
    public Receta guardarConAutor(Receta receta, Usuario autor) {
        if (receta == null) {
            throw new IllegalArgumentException("La receta no puede estar vacía");
        }
        if (autor == null) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        receta.setAutor(autor);
        return recetaRepository.save(receta);
    }

    // Método eliminar
    public void eliminar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede estar vacío");
        }
        recetaRepository.deleteById(id);
    }

    // Método para buscar por país
    public List<Receta> buscarPorPais(String pais) {
        return recetaRepository.findByPais(pais);
    }

    // Método para buscar por autor
    public List<Receta> buscarPorUsuario(Usuario usuario) {
        return recetaRepository.findByAutor(usuario);
    }

    // Método para buscar por el tipo de dieta
    public List<Receta> buscarPorTipoDieta(String tipoDieta) {
        return recetaRepository.findByTipoDieta(tipoDieta);
    }

    // Método para buscar por alergias
    public List<Receta> buscarPorAlergias(String alergia) {
        return recetaRepository.findByAlergiasContainingIgnoreCase(alergia);
    }

    // Método para buscar por ingredientes
    public List<Receta> buscarPorIngredientes(String ingrediente) {
        return recetaRepository.findByIngredientesContainingIgnoreCase(ingrediente);
    }

    // Método para buscar por tipo de plato
    public List<Receta> buscarPorTipoPlato(String tipoPlato) {
        return recetaRepository.findByTipoPlato(tipoPlato);
    }

    public List<Receta> listarPorUsuario(Usuario usuario) {
        return recetaRepository.findByAutor(usuario);
    }

    // ADMIN método para las recetas destacadas
    public List<Receta> listarDestacadas() {
        return recetaRepository.findByDestacadaTrue();
    }

    // Búsqueda general por título o ingredientes
    public List<Receta> buscarPorTituloOIngredientes(String q) {
        return recetaRepository.findByTituloContainingIgnoreCaseOrIngredientesContainingIgnoreCase(q, q);
    }

    // Método para buscar por tiempo de preparación
    public List<Receta> buscarPorTiempoPreparacion(String tiempoPreparacion) {
        return recetaRepository.findByTiempoPreparacion(tiempoPreparacion);
    }

    // Método para buscar por dificultad
    public List<Receta> buscarPorDificultad(String dificultad) {
        return recetaRepository.findByDificultad(dificultad);
    }


    /*A partir de aquí van métodos para buscar con paginación */

    public Page<Receta> listarTodasPaginadas(@NonNull Pageable pageable) {
    return recetaRepository.findAll(pageable);
    }

    public Page<Receta> buscarPorTituloOIngredientesPaginado(String q, Pageable pageable) {
        return recetaRepository.findByTituloContainingIgnoreCaseOrIngredientesContainingIgnoreCase(q, q, pageable);
    }

    public Page<Receta> buscarPorPaisPaginado(String pais, Pageable pageable) {
        return recetaRepository.findByPais(pais, pageable);
    }

    public Page<Receta> buscarPorTipoDietaPaginado(String tipoDieta, Pageable pageable) {
        return recetaRepository.findByTipoDieta(tipoDieta, pageable);
    }

    public Page<Receta> buscarPorAlergiasPaginado(String alergia, Pageable pageable) {
        return recetaRepository.findByAlergiasContainingIgnoreCase(alergia, pageable);
    }

    public Page<Receta> buscarPorTipoplatoPaginado(String tipoPlato, Pageable pageable) {
        return recetaRepository.findByTipoPlato(tipoPlato, pageable);
    }

    public Page<Receta> buscarPorTiempoPreparacionPaginado(String tiempoPreparacion, Pageable pageable) {
        return recetaRepository.findByTiempoPreparacion(tiempoPreparacion, pageable);
    }

    public Page<Receta> buscarPorDificultadPaginado(String dificultad, Pageable pageable) {
        return recetaRepository.findByDificultad(dificultad, pageable);
    }
}
