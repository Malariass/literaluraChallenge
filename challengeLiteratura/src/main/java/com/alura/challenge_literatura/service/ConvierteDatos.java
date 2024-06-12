package com.alura.challenge_literatura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class ConvierteDatos implements IConvierteDatos {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            System.out.println("Procesando:" + json + "....");

            if (json == null || json.isEmpty()) {
                System.out.println("No se encontraron datos en el JSON proporcionado");
                throw new RuntimeException("No se encontraron datos en el JSON proporcionado");
            }
            System.out.println("Datos convertidos exitosamente");
            return objectMapper.readValue(json,clase);

        } catch (JsonProcessingException e) {
            System.out.println("Error al convertir datos " + e);
            throw new RuntimeException(e);
        }
    }
}
