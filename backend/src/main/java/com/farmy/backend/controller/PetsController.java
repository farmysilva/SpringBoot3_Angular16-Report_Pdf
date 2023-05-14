package com.farmy.backend.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.farmy.backend.model.Pets;
import com.farmy.backend.service.PetsService;
import java.util.List;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PetsController {

    private final PetsService petsService;

    //Hello word
    @GetMapping("/")
    public String helloWordString()  {
        return "Hello World";
    }

    //Rest API CRUD - Create Pets POST
    //Read Pets - GET
    //Delete PEts - DELETE
    //Update PETS - PUT

    //Create pets
    @PostMapping("/add")
    public Pets createPets(@RequestBody Pets pets){
        Pets pet = petsService.createPets(pets);
        return pet;
    }

    //Read pets
    @GetMapping("/pets")
    public List<Pets> getPets() {
        return petsService.readPets();
    }

    //Delete pets
    @DeleteMapping("/delete/{id}")
    public String deletePet(@PathVariable("id") Long id){
        petsService.deletePet(id);
        return "Pet Deletado com Sucesso!";
    }

    //Update pets
    @PutMapping("/update/{id}")
    public Pets updatePetsById(@RequestBody Pets pets, @PathVariable("id") Long id) {
        return petsService.updatePet(pets, id);
    }    

    //Pets by id
    @GetMapping("/pets/{id}")
    public Pets getPetById(@PathVariable("id") Long id) {
        return petsService.readPetsById(id);
    }
}
