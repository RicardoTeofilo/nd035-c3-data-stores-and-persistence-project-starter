package com.udacity.jdnd.course3.critter.user;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class EmployeeSkill {

    @Id
    @GeneratedValue
    private Long id;

    private String skill;

    public EmployeeSkill() {
    }

    /**
     * From Hibernate Documentation:
     *
     * When using Sets, it’s very important to supply proper equals/hashCode implementations for child entities.
     *
     * In the absence of a custom equals/hashCode implementation logic, Hibernate will use the default Java
     * reference-based object equality which might render unexpected results when mixing detached
     * and managed object instances.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.skill);
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null || getClass() != obj.getClass())
            return false;

        if(this == obj)
            return true;

        EmployeeSkill employeeSkill = (EmployeeSkill) obj;
        return Objects.equals(this.skill, employeeSkill.getSkill());
    }

    public EmployeeSkill(String skill) {
        this.skill = skill;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
