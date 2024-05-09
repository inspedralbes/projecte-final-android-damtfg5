package com.example.projectofinal;

public class RegisterRequest {
    private String firstname;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private String country;
    private String birthDate;

    public RegisterRequest(String firstname, String surname, String email, String password, String phone, String country, String birthDate) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.country = country;
        this.birthDate = birthDate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
