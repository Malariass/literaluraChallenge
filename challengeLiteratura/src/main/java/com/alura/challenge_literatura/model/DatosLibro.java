package com.alura.challenge_literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
    @JsonAlias("title") String titulo,
    @JsonAlias("download_count") Integer descargas,
    @JsonAlias("authors") List<DatosAutor> autor,
    @JsonAlias("languages") List<String> idiomas,
    @JsonAlias("image/jpeg") String tapa ) {
    }
