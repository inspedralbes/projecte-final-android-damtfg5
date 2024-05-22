package com.example.projectofinal;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class SetData {
    private List<Map<String, Integer>> set;

    public SetData(List<Map<String, Integer>> set) {
        this.set = set;
    }

    public List<Map<String, Integer>> getSet() {
        return set;
    }

    public void setSet(List<Map<String, Integer>> set) {
        this.set = set;
    }
}
