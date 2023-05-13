package com.farmy.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farmy.backend.model.Pets;

@Repository
public interface PetsRepository extends JpaRepository<Pets, Long>{

}
