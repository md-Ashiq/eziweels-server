package com.example.demo.model;

import com.example.demo.repository.CustomerRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private Customer customer;
    private ServiceProvider serviceProvider;

    public UserPrincipal(Customer customer) {
        this.customer = customer;
    }
    public UserPrincipal(ServiceProvider serviceProvider){
        this.serviceProvider = serviceProvider;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        if (customer != null){
            return customer.getPassword();
        }else if (serviceProvider != null){
            return serviceProvider.getPassword();
        }else{
            return "";
        }
    }

    @Override
    public String getUsername() {
        if (customer != null){
            return customer.getEmail();
        }else if (serviceProvider != null){
            return serviceProvider.getEmail();
        }else{
            return "";
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
