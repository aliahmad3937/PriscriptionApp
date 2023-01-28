package com.codecoy.prescriptionapp.models;

import java.util.ArrayList;

public class DetailResponse {
    private boolean status;
    private String message;
    private Integer New;
    private Integer Refill;
    ArrayList < Prescriber > data = new ArrayList<  >();


    // Getter Methods

    public boolean getStatus() {
        return status;
    }

    public boolean isStatus() {
        return status;
    }

    public ArrayList<Prescriber> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Integer getNew() {
        return New;
    }

    public Integer getRefill() {
        return Refill;
    }

    // Setter Methods

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNew(Integer New) {
        this.New = New;
    }

    public void setRefill(Integer Refill) {
        this.Refill = Refill;
    }
}
