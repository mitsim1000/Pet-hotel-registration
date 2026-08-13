package com.pethotel.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pethotel.Models.Owner;

@Service
public interface OwnerService {
    ResponseEntity<?> createOwner(Owner owner);
}
