package com.pethotel.Controllers;

import com.pethotel.Models.Owner;
import com.pethotel.Services.OwnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping
    public ResponseEntity<?> createOwner(
            @RequestBody Owner owner) {

        return ownerService.createOwner(owner);
    }
}