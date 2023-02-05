package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ScheduleService {

    @Autowired
    public ScheduleRepository scheduleRepository;

    @Autowired
    public EmployeeService employeeService;

    @Autowired
    public PetService petService;

    /**
     * It creates the schedule for all the associated entities. Safety checks will be performed along the method
     * to avoid creating a schedule for an invalid entity. Make sure that the list of employee IDs are valid,
     * they have the required skill and are available on that date. If the data passed in is invalid,
     * it will throw an exception.
     *
     * @param employeeIds A valid list of employee IDs that have the right skills and are available
     * @param petIds A valid list of existing Pet IDs
     * @param date A valid date
     * @param employeeSkills The list of required skills for the schedule
     * @return The Schedule created
     */
    @Transactional
    public Schedule createSchedule(List<Long> employeeIds, List<Long> petIds,
                                   LocalDate date, Set<EmployeeSkill> employeeSkills){

        //This will fetch the list of employees available at the moment with the right type of skills
        List<Employee> employeeForServiceList = employeeService.findEmployeeForService(employeeSkills, date);
        //Safety checks on the list of employees passed on the request
        if(CollectionUtils.isEmpty(employeeForServiceList)) {
            throw new InvalidScheduleException("There is no employee available for date and the skills requested");
        }else if (employeeForServiceList.size() != employeeIds.size())
            throw new InvalidScheduleException("There is a mismatch in the number of employee available and the employee list requested");

        employeeIds.forEach(employeeId -> {
            if(employeeForServiceList.contains(employeeId))
                throw new InvalidScheduleException("There employeeID: " + employeeId + " is not in the list of employees available based on skills and date");
        });

        //Next we perform some safety checks and validations on the Pet list
        if(CollectionUtils.isEmpty(petIds))
            throw new InvalidScheduleException("The list of pets is empty. Please provide a valid list pets for the schedule");

        List<Pet> petList = new ArrayList<>();
        petIds.forEach(petId -> {
            petList.add(petService.findPetById(petId));
        });

        Schedule schedule = new Schedule();
        schedule.setDate(date);
        schedule.setEmployees(employeeForServiceList);
        schedule.setPets(petList);
        schedule.setSkills(employeeSkills);
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    @Transactional
    public List<Schedule> findAllByEmployeeId(Long employeeId){
        return scheduleRepository.findAllByEmployeesId(employeeId);
    }

    @Transactional
    public List<Schedule> findAllSchedulesByPetId(Long petId){
        return scheduleRepository.findAllByPetsId(petId);
    }

    @Transactional
    public List<Schedule> findAllScheduleByCustomerId(Long customerId){
        return scheduleRepository.findAllByPetsCustomerId(customerId);
    }
}
