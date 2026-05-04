package mon.food.mon.repository;

import mon.food.mon.model.Receta;
import mon.food.mon.model.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long>{
    List<Receta> findByPais(String pais);
    List<Receta> findByTipoDieta(String tipoDieta);
    List<Receta> findByTituloContainingIgnoreCase(String titulo);
    List<Receta> findByAutor(Usuario autor);
    List<Receta> findByAlergiasContainingIgnoreCase(String alergia);
    List<Receta> findByIngredientesContainingIgnoreCase(String ingrediente);
    List<Receta> findByTipoPlato(String tipoPlato);
    List<Receta> findByDestacadaTrue();
    List<Receta> findByTituloContainingIgnoreCaseOrIngredientesContainingIgnoreCase(String titulo, String ingredientes);
    List<Receta> findByTiempoPreparacion(String tiempoPreparacion);
    List<Receta> findByDificultad(String dificultad);
    /*A partir de aquí van métodos para buscar con paginación */
    Page<Receta> findByPais(String pais, Pageable pageable);
    Page<Receta> findByTipoDieta(String tipoDieta, Pageable pageable);
    Page<Receta> findByAlergiasContainingIgnoreCase(String alergia, Pageable pageable);
    Page<Receta> findByTipoPlato(String tipoPlato, Pageable pageable);
    Page<Receta> findByTituloContainingIgnoreCaseOrIngredientesContainingIgnoreCase(String titulo, String ingredientes, Pageable pageable);
    Page<Receta> findByTiempoPreparacion(String tiempoPreparacion, Pageable pageable);
    Page<Receta> findByDificultad(String dificultad, Pageable pageable);
}
