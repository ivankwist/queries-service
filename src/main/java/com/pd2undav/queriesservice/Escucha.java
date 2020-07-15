package com.pd2undav.queriesservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Escucha {
    private Long id;
    private String usuario;
    private String cancion;
    private String ambito;
    private Date fecha;

    protected Escucha() {}

    public Escucha(String usuario, String cancion, String ambito) {
        this.usuario = usuario;
        this.cancion = cancion;
        this.ambito = ambito;
        this.fecha = new Date();
    }

    @Override
    public String toString() {
        return String.format("Escucha[id=%d, usuario=%s, cancion=%s, ambito=%s, fecha=%s]", id, usuario, cancion, ambito, fecha.toString());
    }

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getCancion() {
        return cancion;
    }

    public String getAmbito() {
        return ambito;
    }

    public Date getFecha() {
        return fecha;
    }
}
