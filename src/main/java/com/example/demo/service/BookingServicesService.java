package com.example.demo.service;

import com.example.demo.dto.BookingResponseDTO;
import com.example.demo.model.*;
import com.example.demo.repository.BookingServicesRepo;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.EzServiceProvidersRepo;
import com.example.demo.repository.ServiceProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServicesService {
    private BookingServicesRepo bookingServicesRepo;
    private ServiceProviderRepo serviceProviderRepo;
    private CustomerRepo customerRepo;
    private EzServiceProvidersRepo ezServiceProvidersRepo;
    @Autowired
    public BookingServicesService(BookingServicesRepo bookingServicesRepo,EzServiceProvidersRepo ezServiceProvidersRepo, ServiceProviderRepo serviceProviderRepo, CustomerRepo customerRepo){
        this.bookingServicesRepo = bookingServicesRepo;
        this.serviceProviderRepo = serviceProviderRepo;
        this.customerRepo = customerRepo;
        this.ezServiceProvidersRepo = ezServiceProvidersRepo;
    }
    public String bookService(int cusId,int serId){
        Customer customer = customerRepo.findById(cusId).orElse(null);
        EzServiceProviders ezServiceProvider = ezServiceProvidersRepo.findById(serId).orElse(null);
        if (customer == null || ezServiceProvider == null){
            return "Invalid Credentials";
        }
         com.example.demo.model.BookingServices bookingServices = new BookingServices();
         bookingServices.setStoreName(ezServiceProvider.getTitle());
         bookingServices.setCustomer(customer);
         bookingServices.setServiceProvider(ezServiceProvider.getServiceProvider());
         bookingServices.setPrice(ezServiceProvider.getPrice());
         bookingServices.setStatus("Created");
         bookingServices.setDate(new Date());
         bookingServicesRepo.save(bookingServices);
         return "Booking Service Created";
    }
    public List<BookingServices> getBookingServices(){
        List<BookingServices> bookingServices = bookingServicesRepo.findAll();
        return bookingServices;
    }
    public List<BookingResponseDTO> getPendingServices(int customerId, String status) {
        List<BookingServices> bookingServicesList = bookingServicesRepo.findByCustomerIdAndStatus(customerId, status);
        return bookingServicesList.stream()
                .map(this::convertToBookingResponseDTO)
                .collect(Collectors.toList());
    }
    public List<BookingResponseDTO> getCustomerServices(int customerId) {
        List<BookingServices> bookingServicesList = bookingServicesRepo.findByCustomerId(customerId);
        return bookingServicesList.stream()
                .map(this::convertToBookingResponseDTO)
                .collect(Collectors.toList());
    }
    private BookingResponseDTO convertToBookingResponseDTO(BookingServices bookingServices) {
        ServiceProvider serviceProvider = bookingServices.getServiceProvider();
        Address address= serviceProvider.getAddress();
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setDate(bookingServices.getDate());
        bookingResponseDTO.setPrice(bookingServices.getPrice());
        bookingResponseDTO.setStoreName(address.getsCName());
        bookingResponseDTO.setStatus(bookingServices.getStatus());
        bookingResponseDTO.setDate(bookingServices.getDate());
        bookingResponseDTO.setMobileNumber(serviceProvider.getMobileNumber());
        bookingResponseDTO.setEmail(serviceProvider.getEmail());
        // Add more fields as necessary
        return bookingResponseDTO;
    }

    public List<BookingResponseDTO> getServiceProviders(int serviceProviderId){
        List<BookingServices> bookingServicesList = bookingServicesRepo.findByServiceProviderId(serviceProviderId);
        return bookingServicesList.stream()
                .map(this::convertToBookingResponseServiceProviderDTO)
                .collect(Collectors.toList());
    }
    private BookingResponseDTO convertToBookingResponseServiceProviderDTO(BookingServices bookingServices) {
        Customer customer = bookingServices.getCustomer();
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setDate(bookingServices.getDate());
        bookingResponseDTO.setStoreName(bookingServices.getStoreName());
        bookingResponseDTO.setStatus(bookingServices.getStatus());
        bookingResponseDTO.setPrice(bookingServices.getPrice());
        bookingResponseDTO.setMobileNumber(customer.getMobileNumber());
        bookingResponseDTO.setEmail(customer.getEmail());
        // Add more fields as necessary
        return bookingResponseDTO;
    }
    public List<BookingResponseDTO> getServiceProvidersServices(int serviceProviderId){
        List<BookingServices> bookingServicesList = bookingServicesRepo.findByServiceProviderId(serviceProviderId);
        return bookingServicesList.stream()
                .map(this::convertToBookingResponseServiceProviderServicesDTO)
                .collect(Collectors.toList());
    }
    private BookingResponseDTO convertToBookingResponseServiceProviderServicesDTO(BookingServices bookingServices) {
        Customer customer = bookingServices.getCustomer();
        ServiceProvider serviceProvider = bookingServices.getServiceProvider();
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setDate(bookingServices.getDate());
        bookingResponseDTO.setStoreName(serviceProvider.getServiceTitle());
        bookingResponseDTO.setStatus(bookingServices.getStatus());
        bookingResponseDTO.setPrice(bookingServices.getPrice());
        bookingResponseDTO.setMobileNumber(customer.getMobileNumber());
        bookingResponseDTO.setEmail(customer.getEmail());
        // Add more fields as necessary
        return bookingResponseDTO;
    }
}
