package com.example.projectofinal;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GameData {
    @SerializedName("idSQL")
    private int idSQL;
    private List<SetData> sets;

    public GameData(int idSQL, List<SetData> sets) {
        this.idSQL = idSQL;
        this.sets = sets;
    }

    public int getIdSQL() {
        return idSQL;
    }

    public void setIdSQL(int idSQL) {
        this.idSQL = idSQL;
    }

    public List<SetData> getSets() {
        return sets;
    }

    public void setSets(List<SetData> sets) {
        this.sets = sets;
    }
}

