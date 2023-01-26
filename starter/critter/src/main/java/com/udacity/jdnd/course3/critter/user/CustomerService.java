package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    /**
     * Gets the Customer object by ID (or throws exception if non-existent)
     * @param id the Customer Id
     * @return the Customer with the Id
     */
    @Transactional
    public Customer findCustomerById(Long id){
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Transactional
    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }
}
