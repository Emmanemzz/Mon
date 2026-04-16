package mon.food.mon.repository;

import mon.food.mon.model.Comentario;
import mon.food.mon.model.Receta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
    List<Comentario> findByRecetaOrderByFechaCreacionComentarioDesc(Receta receta);
}
