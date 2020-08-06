package com.appointment.repository;

import com.appointment.domain.TeacherRate;
import com.appointment.domain.UniversityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRateRepository extends JpaRepository<TeacherRate, Long> {

    @Query("select u from university_user u where u.user_name = ?1")
    List<TeacherRate> findAllRatesByTeacherName(String userName);
}
