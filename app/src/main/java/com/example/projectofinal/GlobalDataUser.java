// Clase GlobalDataUser para almacenar los datos del usuario y su equipo
package com.example.projectofinal;

public class GlobalDataUser {
    private int idUser;
    private String firstname;
    private String surname;
    private int admin;
    private String email;
    private int phone;
    private int totalGames;
    private String dominantHand;
    private String position;
    private int height;
    private int verticalJump;
    private String location;
    private String gender;
    private String birthDate;
    private String bio;
    private String availability;
    private String country;
    private String profilePic;
    private int userIdTeam;
    private int spikePointsTotal;
    private int spikeErrorsTotal;
    private int spikeAttemptsTotal;
    private int blockPointsTotal;
    private int blockErrorsTotal;
    private int blockReboundsTotal;
    private int servePointsTotal;
    private int serveErrorsTotal;
    private int serveAttemptsTotal;
    private int setSuccessfulTotal;
    private int setErrorsTotal;
    private int setAttemptsTotal;
    private int receiveSuccessfulTotal;
    private int receiveErrorsTotal;
    private int receiveAttemptsTotal;
    private String rol;
    private int teamId;
    private String teamName;
    private int nPlayers;
    private String teamLogoPic;
    private String shortName;
    private int teamTotalGames;
    private int wonGames;
    private int lostGames;
    private int totalPoints;
    private int idGame;

    // Getters y Setters para todos los campos


    public int isAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Integer getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(Integer totalGames) {
        this.totalGames = totalGames;
    }

    public String getDominantHand() {
        return dominantHand;
    }

    public void setDominantHand(String dominantHand) {
        this.dominantHand = dominantHand;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getVerticalJump() {
        return verticalJump;
    }

    public void setVerticalJump(int verticalJump) {
        this.verticalJump = verticalJump;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Integer getUserIdTeam() {
        return userIdTeam;
    }

    public void setUserIdTeam(Integer userIdTeam) {
        this.userIdTeam = userIdTeam;
    }

    public int getSpikePointsTotal() {
        return spikePointsTotal;
    }

    public void setSpikePointsTotal(int spikePointsTotal) {
        this.spikePointsTotal = spikePointsTotal;
    }

    public int getSpikeErrorsTotal() {
        return spikeErrorsTotal;
    }

    public void setSpikeErrorsTotal(int spikeErrorsTotal) {
        this.spikeErrorsTotal = spikeErrorsTotal;
    }

    public int getSpikeAttemptsTotal() {
        return spikeAttemptsTotal;
    }

    public void setSpikeAttemptsTotal(int spikeAttemptsTotal) {
        this.spikeAttemptsTotal = spikeAttemptsTotal;
    }

    public int getBlockPointsTotal() {
        return blockPointsTotal;
    }

    public void setBlockPointsTotal(int blockPointsTotal) {
        this.blockPointsTotal = blockPointsTotal;
    }

    public int getBlockErrorsTotal() {
        return blockErrorsTotal;
    }

    public void setBlockErrorsTotal(int blockErrorsTotal) {
        this.blockErrorsTotal = blockErrorsTotal;
    }

    public int getBlockReboundsTotal() {
        return blockReboundsTotal;
    }

    public void setBlockReboundsTotal(int blockReboundsTotal) {
        this.blockReboundsTotal = blockReboundsTotal;
    }

    public int getServePointsTotal() {
        return servePointsTotal;
    }

    public void setServePointsTotal(int servePointsTotal) {
        this.servePointsTotal = servePointsTotal;
    }

    public int getServeErrorsTotal() {
        return serveErrorsTotal;
    }

    public void setServeErrorsTotal(int serveErrorsTotal) {
        this.serveErrorsTotal = serveErrorsTotal;
    }

    public int getServeAttemptsTotal() {
        return serveAttemptsTotal;
    }

    public void setServeAttemptsTotal(int serveAttemptsTotal) {
        this.serveAttemptsTotal = serveAttemptsTotal;
    }

    public Integer getSetSuccessfulTotal() {
        return setSuccessfulTotal;
    }

    public void setSetSuccessfulTotal(Integer setSuccessfulTotal) {
        this.setSuccessfulTotal = setSuccessfulTotal;
    }

    public Integer getSetErrorsTotal() {
        return setErrorsTotal;
    }

    public void setSetErrorsTotal(Integer setErrorsTotal) {
        this.setErrorsTotal = setErrorsTotal;
    }

    public Integer getSetAttemptsTotal() {
        return setAttemptsTotal;
    }

    public void setSetAttemptsTotal(Integer setAttemptsTotal) {
        this.setAttemptsTotal = setAttemptsTotal;
    }

    public Integer getReceiveSuccessfulTotal() {
        return receiveSuccessfulTotal;
    }

    public void setReceiveSuccessfulTotal(Integer receiveSuccessfulTotal) {
        this.receiveSuccessfulTotal = receiveSuccessfulTotal;
    }

    public Integer getReceiveErrorsTotal() {
        return receiveErrorsTotal;
    }

    public void setReceiveErrorsTotal(Integer receiveErrorsTotal) {
        this.receiveErrorsTotal = receiveErrorsTotal;
    }

    public Integer getReceiveAttemptsTotal() {
        return receiveAttemptsTotal;
    }

    public void setReceiveAttemptsTotal(Integer receiveAttemptsTotal) {
        this.receiveAttemptsTotal = receiveAttemptsTotal;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getnPlayers() {
        return nPlayers;
    }

    public void setnPlayers(Integer nPlayers) {
        this.nPlayers = nPlayers;
    }

    public String getTeamLogoPic() {
        return teamLogoPic;
    }

    public void setTeamLogoPic(String teamLogoPic) {
        this.teamLogoPic = teamLogoPic;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getTeamTotalGames() {
        return teamTotalGames;
    }

    public void setTeamTotalGames(Integer teamTotalGames) {
        this.teamTotalGames = teamTotalGames;
    }

    public Integer getWonGames() {
        return wonGames;
    }

    public void setWonGames(Integer wonGames) {
        this.wonGames = wonGames;
    }

    public Integer getLostGames() {
        return lostGames;
    }

    public void setLostGames(Integer lostGames) {
        this.lostGames = lostGames;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getIdGame() {
        return idGame;
    }

    public void setIdGame(Integer idGame) {
        this.idGame = idGame;
    }
}
