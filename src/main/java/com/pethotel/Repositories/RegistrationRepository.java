package com.pethotel.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pethotel.Models.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

}