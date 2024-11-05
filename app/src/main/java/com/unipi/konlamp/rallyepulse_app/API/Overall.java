package com.unipi.konlamp.rallyepulse_app.API;

import java.time.LocalTime;

public class Overall {
    private Long co_number;
    private String time;
    private String name;
    public Long getCo_number() {
        return co_number;
    }

    public void setCo_number(Long co_number) {
        this.co_number = co_number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Overall(Long co_number, String time, String name) {
        this.co_number = co_number;
        this.time = time;
        this.name = name;
    }


}
