package com.unipi.konlamp.rallyepulse_app.API;

public class Overall {
    private Long co_number;
    private String time;
    private String name;
    private String first;

    private String prev;
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

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public Overall(Long co_number, String time, String name, String first, String prev) {
        this.co_number = co_number;
        this.time = time;
        this.name = name;
        this.first = first;
        this.prev = prev;
    }
}
