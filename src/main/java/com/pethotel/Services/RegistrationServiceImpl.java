package com.pethotel.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pethotel.Models.Registration;
import com.pethotel.Repositories.RegistrationRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService {


    @Autowired
    private RegistrationRepository registrationRepository;


    @Override
    public Registration createRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }


    @Override
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }


    @Override
    public Registration getRegistrationById(Long id) {

        return registrationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Registration not found"));
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
