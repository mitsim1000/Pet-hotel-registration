package com.pethotel.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pethotel.Models.Owner;
import com.pethotel.Repositories.OwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {

    // store response messages
    private static class ResponseMessage {
        private static final String CREATE_OWNER_FAILED = "Failed creating owner. Please try again";
        private static final String OWNER_ALREADY_EXISTS = "Owner with this ID already exists";
    }

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public ResponseEntity<?> createOwner(Owner owner) {
    	try {
            if (owner.getId() != null && ownerRepository.existsById(owner.getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseMessage.OWNER_ALREADY_EXISTS);
            }

            return ResponseEntity.ok(ownerRepository.save(owner));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage.CREATE_OWNER_FAILED);
        }
    }
    
}
