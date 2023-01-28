package com.codecoy.prescriptionapp.models;

import java.util.ArrayList;

public class LoginResponse {
    private boolean status;
    private String message;
    private String user_id;
    private ArrayList <User> data = new ArrayList <> ();


    // Getter Methods

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUser_id() {
        return user_id;
    }

    public ArrayList<User> getPrescriberList() {
        return data;
    }


    // Setter Methods

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }
}


