package mon.food.mon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recetas")
public class Receta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 2000)
    private String descripcion;

    private String pais;

    private String tipoDieta;

    //CONSTRUCTOR
    public Receta() {

    }

    public Receta(String titulo, String descripcion, String pais, String tipoDieta){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.pais = pais;
        this.tipoDieta = tipoDieta;
    }


    //GETTER Y SETTER
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTipoDieta() {
        return tipoDieta;
    }

    public void setTipoDieta(String tipoDieta) {
        this.tipoDieta = tipoDieta;
    }

    
}
