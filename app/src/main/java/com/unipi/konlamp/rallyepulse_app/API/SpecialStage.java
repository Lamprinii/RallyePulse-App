package com.unipi.konlamp.rallyepulse_app.API;


public class SpecialStage {


    private Long id;

    private String name;
    private float distance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public SpecialStage(Long id, String name, float distance) {
        this.id = id;
        this.name = name;
        this.distance = distance;
    }
}
