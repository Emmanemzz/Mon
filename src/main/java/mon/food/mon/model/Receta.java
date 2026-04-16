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

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario autor;

    @Column(length = 2000)
    private String ingredientes;
    
    private String alergias;

    private String tiempoPreparacion;

    private String dificultad;

    private String metodoPreparacion;

    private String tipoPlato;

    private String imagen1;

    private String imagen2;

    //CONSTRUCTOR
    public Receta() {

    }

    public Receta(String titulo, String descripcion, String pais, String tipoDieta){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.pais = pais;
        this.tipoDieta = tipoDieta;
    }


    //MÉTODOS
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Receta)) return false;
        Receta receta = (Receta) o;
        return id != null && id.equals(receta.id);
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
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

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(String tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getMetodoPreparacion() {
        return metodoPreparacion;
    }

    public void setMetodoPreparacion(String metodoPreparacion) {
        this.metodoPreparacion = metodoPreparacion;
    }

    public String getImagen1() {
        return imagen1;
    }

    public void setImagen1(String imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public String getTipoPlato() {
        return tipoPlato;
    }

    public void setTipoPlato(String tipoPlato) {
        this.tipoPlato = tipoPlato;
    }

    

    
}
