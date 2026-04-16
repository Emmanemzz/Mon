package mon.food.mon.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =  false, length = 1000)
    private String contenido;

    private LocalDateTime fechaCreacionComentario;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false)
    private Receta receta;


    //CONSTRUCTOR
    public Comentario(){

    }


    //MÉTODOS
    /*Con prePersist asignamos la hora y fecha actual automáticamente */
    @PrePersist
    public void prePersist(){
        this.fechaCreacionComentario = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaCreacionComentario() {
        return fechaCreacionComentario;
    }

    public void setFechaCreacionComentario(LocalDateTime fechaCreacionComentario) {
        this.fechaCreacionComentario = fechaCreacionComentario;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    //GETTER Y SETTER
    

}
