package com.pethotel.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pethotel.Exceptions.RegistrationNotFoundException;
import com.pethotel.Models.Registration;
import com.pethotel.Repositories.RegistrationRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    // store response messages
    private static class ResponseMessage {
        private static final String REGISTRATION_ALREADY_EXISTS = "Registration with this ID already exists";
        private static final String CREATE_REGISTRATION_FAILED = "Failed creating registration. Please try again";
    }

    @Autowired
    private RegistrationRepository registrationRepository;

    @Override
    public ResponseEntity<?> createRegistration(Registration registration) {
        try {
            if (registrationRepository.existsById(registration.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMessage.REGISTRATION_ALREADY_EXISTS);
        }

        return ResponseEntity.ok(registrationRepository.save(registration));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage.CREATE_REGISTRATION_FAILED); 
        }
    }


    @Override
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }


    @Override
    public Registration getRegistrationById(Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() ->
                        new RegistrationNotFoundException(id));
    }


    @Override
    public Registration updateRegistration(
            Long id,
            Registration registration) {

        Registration existingRegistration = getRegistrationById(id);

        existingRegistration.setPet(registration.getPet());
        existingRegistration.setCheckInDate(registration.getCheckInDate());
        existingRegistration.setCheckOutDate(registration.getCheckOutDate());

        return registrationRepository.save(existingRegistration);
    }


    @Override
    public void cancelRegistration(Long id) {
        Registration existingRegistration = getRegistrationById(id);
        registrationRepository.delete(existingRegistration);
    }
}
