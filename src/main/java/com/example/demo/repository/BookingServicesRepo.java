package com.example.demo.repository;

import com.example.demo.model.BookingServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingServicesRepo extends JpaRepository<com.example.demo.model.BookingServices, Integer> {
    List<BookingServices> findByCustomerIdAndStatus(int customerId, String status);
    List<BookingServices> findByCustomerId(int customerId);
    List<BookingServices> findByServiceProviderId(int serviceProviderId);
}
