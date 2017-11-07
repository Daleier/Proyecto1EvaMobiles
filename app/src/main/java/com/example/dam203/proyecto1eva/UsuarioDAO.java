package com.example.dam203.proyecto1eva;

/**
 * Created by dam203 on 31/10/2017.
 */
public interface UsuarioDAO {
    Usuario getUsuario(String login, String password);
    boolean insertarUsuario(Usuario usr);
}

