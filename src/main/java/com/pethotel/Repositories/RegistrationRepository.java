package com.pethotel.Repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pethotel.Models.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

	@Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Registration r
            WHERE r.pet.id = :petId
            AND r.checkInDate < :checkOutDate
            AND r.checkOutDate > :checkInDate
            """)
    boolean existsOverlappingRegistration(
            @Param("petId") Long petId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate);
}
