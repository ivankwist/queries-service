package com.pd2undav.queriesservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String id_user;
    private String username;
    private String nombre;

    protected User() {}

    public User(String id_user, String username, String nombre) {
        this.id_user = id_user;
        this.username = username;
        this.nombre = nombre;
    }

    public String getId_user() {
        return id_user;
    }

    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }
}
