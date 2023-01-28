package com.codecoy.prescriptionapp.models;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String fname;
    private String lname;
    private String fullname;
    private String rx;
    private Integer New;
    private Integer Refill;
    private Integer Total;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getFullname() {
        return fullname;
    }

    public String getRx() {
        return rx;
    }

    public Integer getNew() {
        return New;
    }

    public Integer getRefill() {
        return Refill;
    }

    public Integer getTotal() {
        return Total;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setRx(String rx) {
        this.rx = rx;
    }

    public void setNew(Integer New) {
        this.New = New;
    }

    public void setRefill(Integer Refill) {
        this.Refill = Refill;
    }

    public void setTotal(Integer Total) {
        this.Total = Total;
    }
}

