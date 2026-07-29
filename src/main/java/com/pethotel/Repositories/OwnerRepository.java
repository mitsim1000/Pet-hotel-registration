package com.pethotel.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pethotel.Models.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
