package com.unipi.konlamp.rallyepulse_app.API;


import java.io.Serializable;

public class TimeKeeping implements Serializable {

    private TimeKeepingid id;

    private String start_time;

    private String finish_time;

    private String total_time;

    private String competitor;

    private String category;

    private String car_class;

    private String diffToFirst;

    private String diffToPrevious;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCar_class() {
        return car_class;
    }

    public void setCar_class(String car_class) {
        this.car_class = car_class;
    }

    public String getDiffToFirst() {
        return diffToFirst;
    }

    public void setDiffToFirst(String diffToFirst) {
        this.diffToFirst = diffToFirst;
    }

    public String getDiffToPrevious() {
        return diffToPrevious;
    }

    public void setDiffToPrevious(String diffToPrevious) {
        this.diffToPrevious = diffToPrevious;
    }

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

//    public TimeKeeping(TimeKeepingid id, LocalTime start_time, LocalTime finish_time, LocalTime total_time, String name) {
//        this.id = id;
//        this.start_time = start_time;
//        this.finish_time = finish_time;
//        this.total_time = total_time;
//        this.name = name;
//    }


    public TimeKeeping(TimeKeepingid id, String start_time, String finish_time, String total_time, String name, String category, String car_class, String diffToFirst, String diffToPrevious) {
        this.id = id;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.total_time = total_time;
        this.competitor = name;
        this.category = category;
        this.car_class = car_class;
        this.diffToFirst = diffToFirst;
        this.diffToPrevious = diffToPrevious;
    }
}


