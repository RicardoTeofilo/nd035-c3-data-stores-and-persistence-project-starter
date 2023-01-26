package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Transactional()
    public Pet savePet(Pet pet){
        return petRepository.save(pet);
    }

    /**
     * Gets the PET object by ID (or throws exception if non-existent)
     * @param id the Pet ID
     * @return PET with the ID
     */
    @Transactional()
    public Pet findPetById(Long id){
        return petRepository.findById(id)
                .orElseThrow(PetNotFoundException::new);
    }

}
