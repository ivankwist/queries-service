package com.pd2undav.queriesservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cancion {
    private String id_cancion;
    private String nombre;

    protected Cancion() {}

    public Cancion(String id_cancion, String nombre) {
        this.id_cancion = id_cancion;
        this.nombre = nombre;
    }

    public String getId_cancion() {
        return id_cancion;
    }

    public String getNombre() {
        return nombre;
    }
}
