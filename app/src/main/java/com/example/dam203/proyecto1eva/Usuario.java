package com.example.dam203.proyecto1eva;

/**
 * Created by dam203 on 31/10/2017.
 */

public class Usuario {
    String nombre, login, password;
    int id;

    public Usuario(String nombre, String login, String password, int id) {
        this.nombre = nombre;
        this.login = login;
        this.password = password;
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
}
