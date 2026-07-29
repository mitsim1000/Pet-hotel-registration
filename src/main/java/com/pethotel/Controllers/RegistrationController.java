package com.pethotel.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pethotel.Services.RegistrationService;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

	@Autowired
    private RegistrationService registrationService;
}
