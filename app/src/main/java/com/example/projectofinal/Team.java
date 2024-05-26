package com.example.projectofinal;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class Team {
    private String id;
    private String teamName;
    private int nPlayers;
    private String logoPic;
    private String shortName;
    private int totalGames;
    private int wonGames;
    private int lostGames;
    private int totalPoints;
    private String idGame;
    private List<Usuario> players;


    // Constructor
    public Team(String id, String teamName, int nPlayers, String logoPic, String shortName, int totalGames, int wonGames, int lostGames, int totalPoints, String idGame) {
        this.id = id;
        this.teamName = teamName;
        this.nPlayers = nPlayers;
        this.logoPic = logoPic;
        this.shortName = shortName;
        this.totalGames = totalGames;
        this.wonGames = wonGames;
        this.lostGames = lostGames;
        this.totalPoints = totalPoints;
        this.idGame = idGame;
    }

    public void setPlayersFromJsonArray(JSONArray playersArray) {
        List<Usuario> playersList = new ArrayList<>();
        for (int i = 0; i < playersArray.length(); i++) {
            try {
                JSONObject playerObj = playersArray.getJSONObject(i);
                int id = playerObj.getInt("id");
                String firstname = playerObj.getString("firstname");
                String surname = playerObj.getString("surname");
                String profilePic = playerObj.getString("profilePic");
                // Crear un objeto Usuario con los datos y agregarlo a la lista
                Usuario player = new Usuario(id, firstname, surname, profilePic);
                playersList.add(player);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Asignar la lista de jugadores al atributo players
        this.players = playersList;
    }


    public List<Usuario> getPlayers() {
        return players;
    }

    public void setPlayers(List<Usuario> players) {
        this.players = players;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getnPlayers() {
        return nPlayers;
    }

    public void setnPlayers(int nPlayers) {
        this.nPlayers = nPlayers;
    }

    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getWonGames() {
        return wonGames;
    }

    public void setWonGames(int wonGames) {
        this.wonGames = wonGames;
    }

    public int getLostGames() {
        return lostGames;
    }

    public void setLostGames(int lostGames) {
        this.lostGames = lostGames;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }
}
