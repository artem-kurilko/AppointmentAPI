package com.appointment.repository;

import com.appointment.domain.TeacherRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRateRepository extends JpaRepository<TeacherRate, Long> {

}
