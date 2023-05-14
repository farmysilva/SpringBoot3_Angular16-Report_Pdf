package com.farmy.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.farmy.backend.model.Pets;
import com.farmy.backend.repository.PetsRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PetsServiceImpl implements PetsService{

    private final PetsRepository petsRepository;  

    @Override
    public Pets createPets(Pets pets) {
        return petsRepository.save(pets);
    }

    @Override
    public List<Pets> readPets() {
        return petsRepository.findAll();
    }

    @Override
    public Pets readPetsById(Long id) {      
        return petsRepository.findById(id).get();
    }

    @Override
    public void deletePet(Long id) {
        petsRepository.deleteById(id);
    }

    @Override
    public Pets updatePet(Pets pets, Long id) {
        Pets pet = petsRepository.findById(id).get();
        pet.setNome(pets.getNome());
        pet.setRaca(pets.getRaca());
        pet.setTipo(pets.getTipo());
        pet.setDonos(pets.getDonos());  

        return petsRepository.save(pet);
    }
    
}
