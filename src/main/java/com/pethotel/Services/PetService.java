package com.pethotel.Services;

import com.pethotel.Models.Pet;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

@Service
public interface PetService {
    ResponseEntity<?> createPet(Pet pet);
    Pet updatePet(Long id, Pet updatedPet);
    Pet getPetById(Long id);
    void deletePet(Long id);
    boolean validatePet(String species, String breed);
}