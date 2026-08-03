package com.pethotel.Exceptions;

public class InvalidPetException extends RuntimeException {
     public InvalidPetException(String species, String breed) {
        super("Invalid species and breed combination: " + species + ", " + breed);
    }
}
