package com.example.demo.dto;

public class EzServiceProvidersRequestDTO {
    private int id;
    private int serviceProviderId;
    private int ezServicesId;
    private int price;
    private float rating;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(int serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public int getEzServicesId() {
        return ezServicesId;
    }

    public void setEzServicesId(int ezServicesId) {
        this.ezServicesId = ezServicesId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
