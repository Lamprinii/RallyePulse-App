package com.unipi.konlamp.rallyepulse_app.API;


import java.io.Serializable;
import java.time.LocalTime;

public class TimeKeeping implements Serializable {


    private TimeKeepingid id;


    private String start_time;


    private String finish_time;


    private String total_time;
    private String name;

    public TimeKeepingid getId() {
        return id;
    }

    public void setId(TimeKeepingid id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimeKeeping(TimeKeepingid id, String start_time, String finish_time, String total_time, String name) {
        this.id = id;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.total_time = total_time;
        this.name = name;
    }

}


