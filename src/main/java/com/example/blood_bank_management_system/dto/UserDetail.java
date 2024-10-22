package com.example.blood_bank_management_system.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserDetail {
//    for id
    private final Integer id;
    private final StringProperty username;
    private final StringProperty email;
    private final StringProperty phone;
    private final StringProperty bloodGroup;

    public UserDetail(Integer id, String username, String email, String phone, String bloodGroup) {
        this.id = id;
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.bloodGroup = new SimpleStringProperty(bloodGroup);
    }

    public Integer getId() {
        return id;
    }


    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getBloodGroup() {
        return bloodGroup.get();
    }

    public StringProperty bloodGroupProperty() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup.set(bloodGroup);
    }
}
