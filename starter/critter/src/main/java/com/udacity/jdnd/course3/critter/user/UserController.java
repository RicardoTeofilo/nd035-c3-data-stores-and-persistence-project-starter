package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.*;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;
    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        Customer customer = CustomerDTO.convertCustomerDTOToCustomer(customerDTO);
        return CustomerDTO.convertCustomerToCustomerDTO(customerService.saveCustomer(customer));
    }

    @PutMapping("/customer/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO){
        //TODO: Implement this here
        Customer customer = CustomerDTO.convertCustomerDTOToCustomer(customerDTO);
        return null;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        List<Customer> customerList = customerService.findAllCustomers();
        if(CollectionUtils.isEmpty(customerList))
            return Collections.emptyList();

        customerList.forEach(e -> {
            customerDTOList.add(CustomerDTO.convertCustomerToCustomerDTO(e));
        });
        return customerDTOList;
    }

    @GetMapping("/customer/{customerId}")
    public CustomerDTO getCustomer(@Valid @PathVariable long customerId){
        Customer customer = customerService.findCustomerById(customerId);
        return CustomerDTO.convertCustomerToCustomerDTO(customer);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@Valid @PathVariable long petId){
        Optional<Customer> customer = petService.findCustomerByPetId(petId);
        if (customer.isEmpty())
            return null;

        return CustomerDTO.convertCustomerToCustomerDTO(customer.get());
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = EmployeeDTO.convertEmployeeDTOToEmployee(employeeDTO);
        return EmployeeDTO.convertEmployeeToEmployeeDTO(employeeService.saveEmployee(employee));
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@Valid @PathVariable long employeeId) {
        return EmployeeDTO.convertEmployeeToEmployeeDTO(employeeService.findEmployeeById(employeeId));

    }

    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployees(){
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList = employeeService.findAllEmployees();
        if(CollectionUtils.isEmpty(employeeList))
            return Collections.emptyList();

        employeeList.forEach(employee -> employeeDTOList.add(EmployeeDTO.convertEmployeeToEmployeeDTO(employee)));
        return employeeDTOList;
    }


    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@Valid @RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        Set<EmployeeSkill> skills = EmployeeDTO.convertSkillEnumToEmployeeSkill(employeeDTO.getSkills());

        List<Employee> employeeList = employeeService.findEmployeeForService(skills, employeeDTO.getDate());
        if(CollectionUtils.isEmpty(employeeList))
            return Collections.emptyList();

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        employeeList.forEach(employee -> {
            employeeDTOList.add(EmployeeDTO.convertEmployeeToEmployeeDTO(employee));
        });

        return employeeDTOList;
    }

}
