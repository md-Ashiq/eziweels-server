package com.example.demo.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String mobileNumber;
    private String serviceTitle;
    private Date date;
    private int price;
    private float rating;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BookingServices> bookingServices;
    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<EzServiceProviders> ezServiceProviders;

    public ServiceProvider(ServiceProvider serviceProvider) {

    }
    public ServiceProvider(){

    }

    public List<EzServiceProviders> getEzServiceProviders() {
        return ezServiceProviders;
    }

    public void setEzServiceProviders(List<EzServiceProviders> ezServiceProviders) {
        this.ezServiceProviders = ezServiceProviders;
    }

    public List<BookingServices> getBookedServices() {
        return bookingServices;
    }

    public void setBookedServices(List<BookingServices> bookingServices) {
        this.bookingServices = bookingServices;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
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

    public int getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BookingServices> getBookingServices() {
        return bookingServices;
    }

    public void setBookingServices(List<BookingServices> bookingServices) {
        this.bookingServices = bookingServices;
    }
}
