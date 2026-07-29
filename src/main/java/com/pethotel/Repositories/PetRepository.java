package com.pethotel.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pethotel.Models.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
