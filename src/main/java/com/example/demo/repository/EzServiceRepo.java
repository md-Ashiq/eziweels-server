package com.example.demo.repository;

import com.example.demo.model.EzServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EzServiceRepo extends JpaRepository<EzServices, Integer> {
}
