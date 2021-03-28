/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.repository;

import com.turbal.cnb.entity.Dislike;
import com.turbal.cnb.entity.Employee;
import com.turbal.cnb.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DislikeRepo extends JpaRepository<Dislike, Integer> {
    Optional<Dislike> findDislikeByPostAndAndEmployee(Post post, Employee employee);
}
