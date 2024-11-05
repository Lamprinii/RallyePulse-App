package com.unipi.konlamp.rallyepulse_app.API;


import java.io.Serializable;
import java.time.LocalTime;

public class StartListOb implements Serializable {


    private Competitor competitor;
    private String time;

    private int position;

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public StartListOb(Competitor competitor, String time, int position) {
        this.competitor = competitor;
        this.time = time;
        this.position = position;
    }
}
