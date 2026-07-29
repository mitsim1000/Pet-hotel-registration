package com.pethotel.Services;

import java.util.List;

import com.pethotel.Models.Registration;

public interface RegistrationService {
	Registration createRegistration(Registration registration);
    List<Registration> getAllRegistrations();
    Registration getRegistrationById(Long id);
    Registration updateRegistration(Long id, Registration registration);
    void cancelRegistration(Long id);
}
