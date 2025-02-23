package com.mycompany.booktrack;

import javafx.beans.property.*;

public class User {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty phoneNumber;

    // Constructor
    public User(int id, String name, String email, String phoneNumber) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    // Property Getters
    public IntegerProperty getIdProperty() {
        return id;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public StringProperty getEmailProperty() {
        return email;
    }

    public StringProperty getPhoneNumberProperty() {
        return phoneNumber;
    }

    // Value Getters and Setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    @Override
    public String toString() {
        return "User [id=" + id.get() + ", name=" + name.get() + ", email=" + email.get() +
               ", phoneNumber=" + phoneNumber.get() + "]";
    }
}
