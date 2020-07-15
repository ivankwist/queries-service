package com.pd2undav.queriesservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ambito {

    private String id_ambito;
    private String nombre;
    private String tipo;

    public Ambito() {}

    public Ambito(String id_ambito, String nombre, String tipo) {
        this.id_ambito = id_ambito;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getId_ambito() {
        return id_ambito;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }
}
