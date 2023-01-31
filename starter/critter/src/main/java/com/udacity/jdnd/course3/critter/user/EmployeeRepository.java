package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select distinct e from Employee e inner join e.skills skills " +
            "where skills.skill in (:skills) and e.daysAvailable in (:daysAvailable)")
    List<Employee> findAllBySkillAndAvailableDate(@Param("skills") Set<String> skills, @Param("daysAvailable") Set<DayOfWeek> daysAvailable);

    @Query(value = " SELECT DISTINCT e.* FROM employee e " +
            " LEFT JOIN employee_skills skills ON e.id = skills.employee_id " +
            " LEFT JOIN employee_skill skill ON skills.skills_id = skill.id " +
            " LEFT JOIN employee_days_available eda ON e.id = eda.employee_id " +
            " WHERE eda.days_available = :daysAvailable " +
            " AND skill.skill IN (:skills)",
            nativeQuery = true)
    List<Employee> findAllBySkillAndAvailableDateNative(@Param("skills") Set<String> skills, @Param("daysAvailable") String daysAvailable);



    List<Employee> findBySkills_skillIn(Set<EmployeeSkill> employeeSkills);


}
