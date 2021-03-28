/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.repository;

import com.turbal.cnb.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    Optional<Employee> findEmployeeByLogin(String login);

    @Modifying
    @Query("""
            update Employee e 
            set e.role = 1 where e.id = :id
            """)
    void setCreatorRole(Integer id);

    @Query("""
            select e 
            from Employee e
            where e.googleId = LOWER(:id) 
            """)
    Optional<Employee> findByGoogleId(String id);

    /**
     * This solution consumes a lot of resources, but for my small demo-project this is acceptable
     */
    @Query("""
            select e 
            from Employee e 
            where e.login like concat('%', :request, '%') or 
            e.name like concat(:request, '%') or 
            e.surname like concat(:request, '%')
            """)
    List<Employee> findEmployeeByRequest(String request);
}
