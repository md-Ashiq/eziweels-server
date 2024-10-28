package com.example.demo.repository;

import com.example.demo.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceProviderRepo extends JpaRepository<ServiceProvider, Integer> {
    List<ServiceProvider> findByAddressCity(String city);

    @Query("SELECT sp FROM ServiceProvider sp WHERE sp.serviceTitle = :title")
    List<ServiceProvider> findByTitle(@Param("title") String title);
    ServiceProvider findByEmail(String email);

}
