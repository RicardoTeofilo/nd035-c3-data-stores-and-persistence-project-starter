package com.udacity.jdnd.course3.critter.pet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * I have decided to use an entity to store the pet type information.
 * Using enums for the data types can become challenging as the code evolves
 * and more type of PET are created.
 * If we used a ENUM, even if used the EnumType.STRING, it would require a code change
 * every time we needed to add a new PET type.
 * Adding its own entity for pet type makes the code cleaner and more robust in the long run.
 */
@Entity
public class PetType {

    @Id
    @GeneratedValue
    private Long id;

    private String type;

    public PetType() {
    }

    public PetType(String type) {
        this.type = type;
    }

    public PetType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
