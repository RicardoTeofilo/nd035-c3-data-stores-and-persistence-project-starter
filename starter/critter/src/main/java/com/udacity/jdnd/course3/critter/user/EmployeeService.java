package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findEmployeeById(Long id){
        return employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findAllEmployees(){
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee setEmployeeAvailability(Set<DayOfWeek> daysAvailable, Long employeeId){

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);

        employee.setDaysAvailable(daysAvailable);
        return employeeRepository.save(employee);
    }

    public List<Employee> findEmployeeForService(Set<EmployeeSkill> skills, LocalDate localDate){

        if(CollectionUtils.isEmpty(skills))
            throw new InvalidSkillsException();

        if(localDate == null) {
            throw new InvalidDateException();
        }else {
            LocalDate now = LocalDate.now();
            if (localDate.isBefore(now))
                throw new InvalidDateException();
        }
        //Date is valid and not in the past. Let's extract the Day of Week from it.
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        Set<String> employeeSkills = new HashSet<>();
        skills.forEach(e -> {
                employeeSkills.add(e.getSkill());
        });

        List<Employee> employeeList =  employeeRepository.findAllBySkillAndAvailableDateNative(
                employeeSkills, dayOfWeek.toString().toUpperCase());
        List<Employee> finalEmployeeList = new ArrayList<>();
        //Here we are going to include only the employees that have all of the
        //skills required on the EmployeeRequestDTO object.
        if(!CollectionUtils.isEmpty(employeeList)){
            employeeList.forEach(employee -> {
                Set<EmployeeSkill> thisEmployeeSkillSet = employee.getSkills();
                if (!CollectionUtils.isEmpty(thisEmployeeSkillSet)){
                    if (thisEmployeeSkillSet.containsAll(skills)){
                        finalEmployeeList.add(employee);
                    }
                }
            });

        }
        return finalEmployeeList;
    }
}
