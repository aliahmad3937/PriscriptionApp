package com.codecoy.prescriptionapp.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Prescriber implements Serializable {
    private String id;
    private String fname;
    private String lname;
    private String rx;
    private String fullname;
    private String item;
    private String status;
    private String date;
    private String quantity;
    private String transaction;
    private String refill;


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

    public String getRx() {
        return rx;
    }

    public String getFullname() {
        return fullname;
    }

    public String getItem() {
        return item;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTransaction() {
        return transaction;
    }

    public String getRefill() {
        return refill;
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

    public void setRx(String rx) {
        this.rx = rx;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public void setRefill(String refill) {
        this.refill = refill;
    }
}
