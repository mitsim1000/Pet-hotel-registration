package com.pethotel.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Registration() {
    }

    public Registration(Pet pet, LocalDate checkInDate, LocalDate checkOutDate) {
        this.pet = pet;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Long getId() {
        return id;
    }

    public Pet getPet() {
        return pet;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
