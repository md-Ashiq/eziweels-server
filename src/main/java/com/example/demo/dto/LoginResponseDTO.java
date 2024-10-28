package com.example.demo.dto;

public class LoginResponseDTO {
    private String email;
    private int id;
    private boolean user;
    private boolean serviceProvider;
    private String jWtToken;

    public LoginResponseDTO(String email, int id, boolean b, boolean b1) {

    }

    public LoginResponseDTO() {

    }

    public boolean isServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(boolean serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getjWtToken() {
        return jWtToken;
    }

    public void setjWtToken(String jWtToken) {
        this.jWtToken = jWtToken;
    }
}
