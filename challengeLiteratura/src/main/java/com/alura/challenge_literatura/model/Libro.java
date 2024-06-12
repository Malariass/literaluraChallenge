package com.alura.challenge_literatura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;
    @ManyToOne()
    private Autor autor;

    private Integer descargas;
    private String nombreAutor;
    private String idioma;
    private String tapa;


    public Libro() {}

    public Libro(DatosLibro datosLibro) {

        this.titulo = datosLibro.titulo();
        this.descargas = datosLibro.descargas();
        this.nombreAutor = obtenerPrimerAutor(datosLibro).getNombre();
        this.idioma = obtenerPrimerIdioma(datosLibro);
        this.tapa = datosLibro.tapa();

    }

    @Override
    public String toString() {
        return  " *********"+ '\n' +
                "Datos Libro, id:" + Id +", titulo='" + titulo + '\n' +
                ", descargas=" + descargas + ", autor='" + autor + '\'' +", idioma='" + idioma + '\n' +
                ", tapa='" + tapa;

    }



    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }


    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTapa() {
        return tapa;
    }

    public void setTapa(String tapa) {
        this.tapa = tapa;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    //metodos adicionales
    public Autor obtenerPrimerAutor(DatosLibro datosLibro){
        DatosAutor datosAutor = datosLibro.autor().get(0);
        return new Autor(datosAutor);
    }

    public String obtenerPrimerIdioma(DatosLibro datosLibro){
        String idioma = datosLibro.idiomas().toString();
        return idioma;
    }

}
