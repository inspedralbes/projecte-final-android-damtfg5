package com.example.projectofinal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginRequestBody {
    private String email;
    private String password;

    public LoginRequestBody(String email, String password) {
        this.email = email;
        this.password = hashPassword(password);
    }

    private String hashPassword(String password) {
        try {
            // Crear instancia de MessageDigest con algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("MD5");

            // Aplicar el algoritmo de hash a la contraseña
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convertir el arreglo de bytes a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return password;
    }

    public void setHashedPassword(String hashedPassword) {
        this.password = hashedPassword;
    }
}
