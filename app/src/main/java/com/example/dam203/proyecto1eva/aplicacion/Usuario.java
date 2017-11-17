package com.example.dam203.proyecto1eva.aplicacion;

import java.io.Serializable;

/**
 * Created by dam203 on 31/10/2017.
 */

public class Usuario implements  Serializable {
    private String nombre, login, password, email, direccion;
    private boolean subscripcion;
    int id;

    public Usuario(String nombre, String login, String password, String email, String direccion, boolean subscripcion, int id) {
        this.nombre = nombre;
        this.login = login;
        this.password = password;
        this.email = email;
        this.direccion = direccion;
        this.subscripcion = subscripcion;
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean getSubscripcion() {
        return subscripcion;
    }

    public void setSubscripcion(boolean subscripcion) {
        this.subscripcion = subscripcion;
    }
}
