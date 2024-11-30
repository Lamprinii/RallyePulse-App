package com.unipi.konlamp.rallyepulse_app.API;


public class SpecialStage {


    private Long id;

    private String name;
    private String distance;

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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public SpecialStage(Long id, String name, String distance) {
        this.id = id;
        this.name = name;
        this.distance = distance;
    }
}
