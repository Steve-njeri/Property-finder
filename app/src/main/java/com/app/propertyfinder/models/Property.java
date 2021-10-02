package com.app.propertyfinder.models;

import org.parceler.Parcel;

@Parcel
public class Property {
    String title;
    String description;
    String location;
    String imageUrl;
    Boolean occupied;
    String price;
    String phone;
    String id;

    public Property() {
        // Default constructor
    }

    public Property(String title, String description, String location, String price, String phone, String imageUrl) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.price = price;
        this.phone = phone;
        this.occupied = false;
        this.id = "";
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public String getPrice() {
        return price;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public String getImageUri() {
        return imageUrl;
    }

    public Property(String imageUri) {
        this.imageUrl = imageUri;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImageUri(String imageUri) {
        this.imageUrl = imageUri;
    }

    public void setId(String id) {
        this.id = id;
    }
}
