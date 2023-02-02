package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping
    public PetDTO savePet(@Valid @RequestBody PetDTO petDTO) {
        Pet pet = PetDTO.convertPetDTOToPet(petDTO);
        Long customerId = (petDTO.getOwnerId() != 0? petDTO.getOwnerId() : null);
        return PetDTO.convertPetToPetDTO(petService.savePet(pet, customerId));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return PetDTO.convertPetToPetDTO(petService.findPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@Valid @PathVariable long ownerId) {

        List<PetDTO> petDTOList = new ArrayList<>();
        List<Pet> petList = petService.findPetByCustomerId(ownerId);
        if(CollectionUtils.isEmpty(petList))
            return Collections.emptyList();

        petList.forEach(e -> petDTOList.add(PetDTO.convertPetToPetDTO(e)));
        return petDTOList;

    }
}
