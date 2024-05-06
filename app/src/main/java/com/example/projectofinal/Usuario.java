package com.example.projectofinal;

public class Usuario {
    private int id;
    private String firstname;
    private String surname;
    private String profilePic;

    // Constructor
    public Usuario(int id, String firstname, String surname, String profilePic) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.profilePic = profilePic;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}

