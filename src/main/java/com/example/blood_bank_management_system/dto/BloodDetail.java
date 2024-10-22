package com.example.blood_bank_management_system.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BloodDetail {
    private final StringProperty bloodGroup;
    private final StringProperty quantity;

    public BloodDetail(String bloodGroup, String quantity) {
        this.bloodGroup = new SimpleStringProperty(bloodGroup);
        this.quantity = new SimpleStringProperty(quantity);
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

    public String getQuantity() {
        return quantity.get();
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }
}
