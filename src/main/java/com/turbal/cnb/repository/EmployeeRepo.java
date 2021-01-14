/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.repository;

import com.turbal.cnb.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    Employee findByLogin(String login);


    @Query("select u "
        + " from Employee u"
        + " where u.googleId = LOWER(:id)")
    Optional<Employee> findByGoogleId(String id);
}
