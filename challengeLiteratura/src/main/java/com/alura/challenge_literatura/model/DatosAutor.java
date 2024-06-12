package com.alura.challenge_literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") String anioDeNacimiento,
        @JsonAlias("death_year") String anioDeMuerte
) {
}
