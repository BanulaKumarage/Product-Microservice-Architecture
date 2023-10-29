package com.springprojects.inventryservice.repository;

import com.springprojects.inventryservice.model.Inventry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventryRepository extends JpaRepository<Inventry, Long> {
    List<Inventry> findBySkuCodeIn(List<String> skuCode);
}
