package mon.food.mon.repository;

import mon.food.mon.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long>{
    List<Receta> findByPais(String pais);
    List<Receta> findByTipoDieta(String tipoDieta);
    List<Receta> findByTituloContainingIgnoreCase(String titulo);
}
