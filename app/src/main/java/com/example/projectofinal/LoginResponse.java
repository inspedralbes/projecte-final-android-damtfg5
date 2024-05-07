package com.example.projectofinal;

public class LoginResponse {
    private boolean authoritzation;
    private Usuario userData; // Cambia "Usuario" por el nombre de tu clase de usuario

    public boolean isAuthorization() {
        return authoritzation;
    }

    public void setAuthorization(boolean authorization) {
        this.authoritzation = authorization;
    }

    public Usuario getUserData() {
        return userData;
    }

    public void setUserData(Usuario userData) {
        this.userData = userData;
    }
}
