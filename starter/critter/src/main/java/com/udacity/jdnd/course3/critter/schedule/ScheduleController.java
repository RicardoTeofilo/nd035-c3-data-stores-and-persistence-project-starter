package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Set<EmployeeSkill> employeeSkills =
                EmployeeDTO.convertSkillEnumToEmployeeSkill(scheduleDTO.getActivities());
        Schedule schedule = scheduleService.createSchedule(scheduleDTO.getEmployeeIds(),
                scheduleDTO.getPetIds(), scheduleDTO.getDate(), employeeSkills);
        scheduleDTO.setId(schedule.getId());
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        return ScheduleDTO.convertListOfScheduleToListOfScheduleDTO(scheduleList);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@Valid @PathVariable long petId) {
        List<Schedule> scheduleList = scheduleService.findAllSchedulesByPetId(petId);
        return ScheduleDTO.convertListOfScheduleToListOfScheduleDTO(scheduleList);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@Valid @PathVariable long employeeId) {
        List<Schedule> scheduleList = scheduleService.findAllByEmployeeId(employeeId);
        return ScheduleDTO.convertListOfScheduleToListOfScheduleDTO(scheduleList);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@Valid @PathVariable long customerId) {
        List<Schedule> scheduleList = scheduleService.findAllScheduleByCustomerId(customerId);
        return ScheduleDTO.convertListOfScheduleToListOfScheduleDTO(scheduleList);
    }
}
