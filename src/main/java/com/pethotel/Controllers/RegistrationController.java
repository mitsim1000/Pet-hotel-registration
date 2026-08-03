package com.pethotel.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pethotel.Exceptions.RegistrationNotFoundException;
import com.pethotel.Models.Registration;
import com.pethotel.Services.RegistrationService;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    // store response messages
    private static class ResponseMessage {
        private static final String FIND_BY_ID_FAILED = "Failed finding registration. Please try again";
        private static final String UPDATE_REGISTRATION_FAILED = "Failed upating registration. Please try again";
        private static final String DELETE_REGISTRATION_FAILED = "Failed deleting registration. Please try again";
        private static final String DELETE_REGISTRATION_SUCCESS = "Registration deleted successfully!";
    }

	@Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> registerPet(
            @RequestBody Registration registration) {

        return registrationService.createRegistration(registration);
    }

    @GetMapping
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.getAllRegistrations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRegistration(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(registrationService.getRegistrationById(id));
        } catch (RegistrationNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage.FIND_BY_ID_FAILED); 
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegistration(
            @PathVariable Long id,
            @RequestBody Registration registration) {

        try {
            return ResponseEntity.ok(registrationService.updateRegistration(id, registration));
        } catch (RegistrationNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage.UPDATE_REGISTRATION_FAILED); 
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> checkoutRegistration(
            @PathVariable Long id) {

        try {
            registrationService.cancelRegistration(id);

            return ResponseEntity.ok(ResponseMessage.DELETE_REGISTRATION_SUCCESS);
        } catch (RegistrationNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage.DELETE_REGISTRATION_FAILED); 
        }
    }
}
