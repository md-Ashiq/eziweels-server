package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class EzServiceProviders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ezService_id")
    private EzServices ezServices;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "serviceProvider_id")
    private ServiceProvider serviceProvider;
    private int price;
    private float rating;
    private String Title;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EzServices getEzServices() {
        return ezServices;
    }

    public void setEzServices(EzServices ezServices) {
        this.ezServices = ezServices;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
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
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
