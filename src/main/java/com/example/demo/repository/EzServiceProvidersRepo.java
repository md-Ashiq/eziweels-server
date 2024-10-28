package com.example.demo.repository;

import com.example.demo.model.EzServiceProviders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EzServiceProvidersRepo extends JpaRepository<com.example.demo.model.EzServiceProviders,Integer> {
    @Query("SELECT sp FROM EzServiceProviders sp WHERE sp.Title = :title")
    List<EzServiceProviders> findByTitle(@Param("title") String title);
    @Query("SELECT sp FROM EzServiceProviders sp WHERE sp.serviceProvider.id = :id")
    List<EzServiceProviders> findByServiceProvider(@Param("id") Integer id);

}
