package com.unipi.konlamp.rallyepulse_app.API;
import java.io.Serializable;

public class Competitor implements Serializable {

    private Long co_number;
    private String driver;
    private String codriver;
    private String email;
    private String telephone;

    private String vehicle;
    private String category;
    private String car_class;
    private String passcode;

    public Long getCo_number() {
        return co_number;
    }

    public void setCo_number(Long co_number) {
        this.co_number = co_number;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCodriver() {
        return codriver;
    }

    public void setCodriver(String codriver) {
        this.codriver = codriver;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
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

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public Competitor(Long co_number, String driver, String codriver, String email, String telephone, String vehicle, String category, String car_class) {
        this.co_number = co_number;
        this.driver = driver;
        this.codriver = codriver;
        this.email = email;
        this.telephone = telephone;
        this.vehicle = vehicle;
        this.category = category;
        this.car_class = car_class;
    }
}

