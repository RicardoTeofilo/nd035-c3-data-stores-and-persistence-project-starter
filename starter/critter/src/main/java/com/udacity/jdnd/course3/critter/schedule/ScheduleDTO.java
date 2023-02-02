package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkillEnum;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private long id;
    private List<Long> employeeIds;
    private List<Long> petIds;
    private LocalDate date;
    private Set<EmployeeSkillEnum> activities;

    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkillEnum> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkillEnum> activities) {
        this.activities = activities;
    }

    public static ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){

        if(schedule == null)
            return null;

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());

        List<Long> employeeIds = new ArrayList<>();
        schedule.getEmployees().forEach(employee -> employeeIds.add(employee.getId()));
        scheduleDTO.setEmployeeIds(employeeIds);

        List<Long> petIdList = new ArrayList<>();
        schedule.getPets().forEach(pet -> petIdList.add(pet.getId()));
        scheduleDTO.setPetIds(petIdList);

        scheduleDTO.setActivities(EmployeeDTO
                .convertEmployeeSkillToEmployeeSkillEnum(schedule.getSkills()));

        return scheduleDTO;
    }

    public static List<ScheduleDTO> convertListOfScheduleToListOfScheduleDTO(List<Schedule> scheduleList){

        if(CollectionUtils.isEmpty(scheduleList))
            return Collections.emptyList();

        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleList.forEach(schedule -> {
            scheduleDTOList.add(convertScheduleToScheduleDTO(schedule));
        });
        return scheduleDTOList;
    }
}
