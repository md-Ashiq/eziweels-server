package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.ServiceProvider;
import com.example.demo.model.UserPrincipal;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.ServiceProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    private CustomerRepo customerRepo;
    private ServiceProviderRepo serviceProviderRepo;

    @Autowired
    public MyUserDetailService(CustomerRepo customerRepo, ServiceProviderRepo serviceProviderRepo){
        this.customerRepo = customerRepo;
        this.serviceProviderRepo = serviceProviderRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByEmail(email);
        ServiceProvider serviceProvider = serviceProviderRepo.findByEmail(email);
        if (customer != null){
            return new UserPrincipal(customer);
        }else if(serviceProvider != null){
            return new UserPrincipal(serviceProvider);
        }else{
            return null;
        }
    }
}
