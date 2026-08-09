package com.pethotel.Repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pethotel.Models.HotelStaff;

public interface HotelStaffRepository extends JpaRepository<HotelStaff, Long> {

    Optional<HotelStaff> findByUsername(String username);
}
