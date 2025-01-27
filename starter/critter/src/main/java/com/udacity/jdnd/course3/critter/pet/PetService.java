package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Pet savePet(Pet pet, Long customerId){
        Pet savedPet;
        Customer existingCustomer = null;
        if(customerId != null){
            //If there is a customer Id to be associated with the Pet,
            //it must be an existing valid customer Id
            existingCustomer = customerRepository.findById(customerId)
                    .orElseThrow(CustomerNotFoundException::new);
            pet.setCustomer(existingCustomer);
        }
        savedPet = petRepository.save(pet);
        if(existingCustomer != null){
            //Because the owning side of the relationship between Customer <-> Pet
            //is on the Pet.customer side, we must do the proper wiring and update the
            //Pet List on the customer side as well
            List<Pet> petList = existingCustomer.getPetList();
            if(CollectionUtils.isEmpty(petList))
                petList = new ArrayList<>();

            petList.add(savedPet);
            existingCustomer.setPetList(petList);
            customerRepository.save(existingCustomer);
        }

        return savedPet;
    }

    /**
     * Gets the PET object by ID (or throws exception if non-existent)
     * @param id the Pet ID
     * @return PET with the ID
     */
    @Transactional
    public Pet findPetById(Long id){
        return petRepository.findById(id)
                .orElseThrow(PetNotFoundException::new);
    }

    @Transactional
    public List<Pet> findPetByCustomerId(Long id){
        return petRepository.findByCustomerId(id);
    }

    @Transactional
    public Optional<Customer> findCustomerByPetId(Long petId){
        return Optional.ofNullable(this.findPetById(petId).getCustomer());
    }

    @Transactional
    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

}
