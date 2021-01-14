/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.config;

import com.turbal.cnb.entity.Employee;
import com.turbal.cnb.entity.enums.Roles;
import com.turbal.cnb.repository.EmployeeRepo;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .mvcMatchers("/").permitAll()
            .anyRequest().authenticated()
            .and()
            .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(EmployeeRepo employeeRepo) {
        return map -> {
            String googleID = (String) map.get("sub");
            Employee employee = employeeRepo.findByGoogleId(googleID).orElseGet(() -> {
                Employee newEmployee = new Employee();

                newEmployee.setName((String) map.get("given_name"));
                newEmployee.setSurname((String) map.get("family_name"));
                newEmployee.setLogin((String) map.get("email"));
                newEmployee.setRole(Roles.READER);

                return newEmployee;
            });

            employeeRepo.save(employee);
            return new Employee();
        };
    }
}
