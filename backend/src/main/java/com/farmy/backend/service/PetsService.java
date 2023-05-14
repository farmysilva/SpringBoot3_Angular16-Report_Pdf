package com.farmy.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.farmy.backend.model.Pets;

@Service
public interface PetsService {
    //create a new Pets
    public Pets createPets(Pets pets);

    //read the pets
    public List<Pets> readPets();

    //read the pets by id
    public Pets readPetsById(Long id);

    //delete one pet
    public void deletePet(Long id);

    //update one pet
    public Pets updatePet(Pets pets, Long id);
    
}
