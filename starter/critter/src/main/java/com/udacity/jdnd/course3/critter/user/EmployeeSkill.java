package com.udacity.jdnd.course3.critter.user;

import javax.persistence.*;

@Entity
public class EmployeeSkill {

    @Id
    @GeneratedValue
    private Long id;

    private String skill;

}
