package com.pethotel.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pethotel.Exceptions.InvalidPetException;
import com.pethotel.Exceptions.PetNotFoundException;
import com.pethotel.Models.Owner;
import com.pethotel.Models.Pet;
import com.pethotel.Repositories.OwnerRepository;
import com.pethotel.Repositories.PetRepository;

@Service
public class PetServiceImpl implements PetService {
    // store response messages
    private static class ResponseMessage {
        private static final String CREATE_PET_FAILED = "Failed creating pet. Please try again";
        private static final String INVALID_PET = "Invalid species/breed information provided";
        private static final String PET_ALREADY_EXISTS = "Pet with this ID already exists";
        private static final String OWNER_DOES_NOT_EXIST = "Invalid owner provided. Please try again";
    }

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private BreedService breedService;

    @Override
    public ResponseEntity<?> createPet(Pet pet) {
    	try {
            if (!validatePet(pet.getSpecies(), pet.getBreed())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.INVALID_PET);
            }

            if (pet.getId() != null && petRepository.existsById(pet.getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMessage.PET_ALREADY_EXISTS);
            }

            if (pet.getOwner() == null ||
                pet.getOwner().getId() == null) {

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ResponseMessage.OWNER_DOES_NOT_EXIST);
            }

            Owner owner = ownerRepository.findById(pet.getOwner().getId())
                    .orElse(null);

            if (owner == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ResponseMessage.OWNER_DOES_NOT_EXIST);
            }

            pet.setOwner(owner);
            return ResponseEntity.ok(petRepository.save(pet));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage.CREATE_PET_FAILED);
        }
    }

    @Override
    public Pet updatePet(Long id, Pet updatedPet) {
        Pet existingPet = getPetById(id);

        if (!validatePet(
                updatedPet.getSpecies(),
                updatedPet.getBreed()
            )
        ) {
            throw new InvalidPetException(updatedPet.getSpecies(), updatedPet.getBreed());
        }

        existingPet.setName(updatedPet.getName());
        existingPet.setSpecies(updatedPet.getSpecies());
        existingPet.setBreed(updatedPet.getBreed());
        Owner owner = ownerRepository.findById(updatedPet.getOwner().getId())
            .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.OWNER_DOES_NOT_EXIST));

        existingPet.setOwner(owner);

        return petRepository.save(existingPet);
    }

    @Override
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() ->
                        new PetNotFoundException(id));
    }

    @Override
    public void deletePet(Long id) {
        Pet existingPet = getPetById(id);
        petRepository.delete(existingPet);
    }

    @Override
    public boolean validatePet(String species, String breed) {
        return breedService.isValidBreed(species, breed);
    }
}
