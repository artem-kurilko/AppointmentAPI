package com.appointment.repository;

import com.appointment.domain.TeacherRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRateRepository extends JpaRepository<TeacherRate, Long> {

    @Query("select u from TeacherRate u where u.teacherName = ?1")
    List<TeacherRate> findAllRatesByTeacherName(String userName);
}
