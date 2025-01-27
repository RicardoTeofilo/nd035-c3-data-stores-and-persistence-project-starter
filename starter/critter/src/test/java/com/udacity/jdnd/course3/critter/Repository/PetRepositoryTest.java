package com.udacity.jdnd.course3.critter.Repository;


import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class PetRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void testCreatePet(){

        PetType petType = createPetType("CAT");
        Pet pet = createPet(petType);
        Pet newPet = this.testEntityManager.persist(pet);

        Assertions.assertEquals(pet.getName(), newPet.getName());
        Assertions.assertEquals(pet.getPetType().getType(), newPet.getPetType().getType());
    }

    @Test
    public void testAddPetsToCustomer() {

        Customer customer = createCustomer();
        Customer newCustomer = this.testEntityManager.persist(customer);

        PetType petType = createPetType("CAT");
        Pet pet = createPet(petType);
        pet.setCustomer(customer);
        Pet newPet = this.testEntityManager.persist(pet);

        List<Pet> newPetList = new ArrayList<>();
        newPetList.add(newPet);
        newCustomer.setPetList(newPetList);
        this.testEntityManager.merge(newCustomer);
        //test we can find the PET by customer ID
        List<Pet> petList = petRepository.findByCustomerId(customer.getId());
        Assertions.assertEquals(newPet.getId(), petList.get(0).getId());

        //test that we can find the Pet from the customer Repository
        Optional<Customer> fetchedCustomer = customerRepository.findById(customer.getId());
        if(fetchedCustomer.isPresent()){
            List<Pet> petListFromCustomer = fetchedCustomer.get().getPetList();
            Assertions.assertEquals(newPet.getId(), petListFromCustomer.get(0).getId());
        }

    }

    @Test
    public void testFindPetListFromCustomer(){
        //create and persist customer
        Customer customer = createCustomer();
        Customer newCustomer = this.testEntityManager.persist(customer);

        //create and persist Pet with the customer set. In theory, it should have cascaded to customer.
        PetType petType = createPetType("CAT");
        Pet pet = createPet(petType);
        pet.setCustomer(customer);
        Pet newPet = this.testEntityManager.persist(pet);

        //The owning side of the relationship Customer -> Pet is on the Pet side
        //Therefore, we need to explicitly set the pet list on the Customer side
        List<Pet> newPetList = new ArrayList<>();
        newPetList.add(newPet);
        newCustomer.setPetList(newPetList);
        this.testEntityManager.merge(newCustomer);

        //test that we can find the Pet from the customer Repository
        Optional<Customer> fetchedCustomer = customerRepository.findById(customer.getId());
        if(fetchedCustomer.isPresent()){
            List<Pet> petListFromCustomer = fetchedCustomer.get().getPetList();
            if(!CollectionUtils.isEmpty(petListFromCustomer))
                Assertions.assertEquals(newPet.getId(), petListFromCustomer.get(0).getId());
        }

        //test that we can find the Pet from the customer findAll repository
        List<Customer> customerList = customerRepository.findAll();
        if(!CollectionUtils.isEmpty(customerList)){
            List<Pet> petList1 = customerList.get(0).getPetList();
            if(!CollectionUtils.isEmpty(petList1)){
                Assertions.assertEquals(newPet.getId(), petList1.get(0).getId());
            }
        }
    }

    private static PetType createPetType(String type){
        PetType petType = new PetType(type);
        return petType;
    }

    private static Pet createPet(PetType petType){
        Pet pet = new Pet( petType, "Nice Cat",
                LocalDate.of(2022, 01, 01), "This is a Cat");
        return pet;
    }

    private static Customer createCustomer(){
        Customer newCustomer = new Customer("John", "617-555-5555");
        return newCustomer;
    }
}
