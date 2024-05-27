package com.example.projectofinal;

import com.google.gson.annotations.SerializedName;

public class UpdateUserRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("firstname")
    private String firstName;

    @SerializedName("phone")
    private int phone;

    @SerializedName("birthDate")
    private String birthDate;

    @SerializedName("country")
    private String country;

    @SerializedName("gender")
    private String gender;

    @SerializedName("bio")
    private String bio;

    @SerializedName("userId")
    private int userId;

    // Constructor
    public UpdateUserRequest(String email, String firstName, int phone, String birthDate, String country, String gender, String bio, int userId) {
        this.email = email;
        this.firstName = firstName;
        this.phone = phone;
        this.birthDate = birthDate;
        this.country = country;
        this.gender = gender;
        this.bio = bio;
        this.userId = userId;
    }

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
