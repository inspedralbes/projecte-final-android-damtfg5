package com.example.projectofinal;

public class GlobalData {
    private static GlobalData instance;
    private GlobalDataUser userData;

    private GlobalData() {
        // Constructor privado para evitar la creación de instancias externas
    }

    public static synchronized GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    public GlobalDataUser getUserData() {
        return userData;
    }

    public void setUserData(GlobalDataUser userData) {
        this.userData = userData;
    }
}
