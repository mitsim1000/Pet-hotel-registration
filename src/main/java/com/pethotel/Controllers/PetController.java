package com.pethotel.Controllers;

import com.pethotel.Exceptions.InvalidPetException;
import com.pethotel.Exceptions.PetNotFoundException;
import com.pethotel.Models.Pet;
import com.pethotel.Services.PetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pets")
public class PetController {

    // store response messages
    private static class ResponseMessage {
        private static final String INVALID_PET_INFORMATION = "Invalid species/breed information provided";
        private static final String UPDATE_PET_FAILED = "Failed updating pet. Please try again";
        private static final String DELETE_PET_SUCCESS = "Deleted pet";
    }

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<?> createPet(
            @RequestBody Pet pet) {

        return petService.createPet(pet);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePet(
            @PathVariable Long id,
            @RequestBody Pet pet) {
        try {
            return ResponseEntity.ok(petService.updatePet(id, pet));
        } catch (InvalidPetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.INVALID_PET_INFORMATION);        }
        catch (PetNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage.UPDATE_PET_FAILED); 
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        petService.deletePet(id);
        return ResponseEntity.ok(ResponseMessage.DELETE_PET_SUCCESS);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validatePet(
            @RequestParam String species,
            @RequestParam String breed) {
        return ResponseEntity.ok(petService.validatePet(species, breed));
    }
}