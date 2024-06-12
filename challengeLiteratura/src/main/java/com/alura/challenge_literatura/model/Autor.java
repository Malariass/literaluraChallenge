package com.alura.challenge_literatura.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "autores")
public class Autor {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String nombre;
    private Integer anioDeNacimiento;
    private Integer anioDeMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Libro> libros;

    public Autor() {
    }

    // Constructor
    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anioDeNacimiento = Integer.valueOf(datosAutor.anioDeNacimiento());
        this.anioDeMuerte = Integer.valueOf(datosAutor.anioDeMuerte());
    }

    // getters and setters

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getanioDeNacimiento() {
        return anioDeNacimiento;
    }

    public void setanioDeNacimiento(Integer anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public Integer getanioDeMuerte() {
        return anioDeMuerte;
    }

    public void setanioDeMuerte(Integer anioDeMuerte) {
        this.anioDeMuerte = anioDeMuerte;
    }

    // Metodo que busca primer autor del array de autores de un libro
    public Autor obtenerPrimerAutor(DatosLibro datosLibro){
        DatosAutor datosAutor = datosLibro.autor().get(0);
        return new Autor(datosAutor);
    }

    @Override
    public String toString() {
        return
                "Nombre = " + nombre + "\'" +
                        ", Año de naciemiento =" + anioDeNacimiento +
                        ", Año de muerte =" + anioDeMuerte
                ;
    }
}
