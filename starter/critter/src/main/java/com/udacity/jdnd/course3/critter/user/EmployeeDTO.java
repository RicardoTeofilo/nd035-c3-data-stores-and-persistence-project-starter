package com.udacity.jdnd.course3.critter.user;

import org.springframework.util.CollectionUtils;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
public class EmployeeDTO {
    private long id;
    private String name;
    private Set<EmployeeSkillEnum> skills;
    private Set<DayOfWeek> daysAvailable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkillEnum> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkillEnum> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public static Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSkills(convertSkillEnumToEmployeeSkill(employeeDTO.getSkills()));
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }

    public static Set<EmployeeSkill> convertSkillEnumToEmployeeSkill(Set<EmployeeSkillEnum> employeeSkills){

        if(!CollectionUtils.isEmpty(employeeSkills)){
            Set<EmployeeSkill> skills = new HashSet<>();
            employeeSkills
                    .forEach(skill -> {
                        EmployeeSkill employeeSkill = new EmployeeSkill(skill.toString().toUpperCase());
                        skills.add(employeeSkill);
                    });
            return skills;

        }else{
            return Collections.emptySet();
        }
    }

    public static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        if(!CollectionUtils.isEmpty(employee.getSkills())){
            Set<EmployeeSkillEnum> skills = new HashSet<>();
            employee.getSkills()
                    .forEach(employeeSkill -> {
                        EmployeeSkillEnum skillEnum = EmployeeSkillEnum.valueOf(employeeSkill.getSkill());
                        skills.add(skillEnum);
                    });
            employeeDTO.setSkills(skills);
        }
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }
}
