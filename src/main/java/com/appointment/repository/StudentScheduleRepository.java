package com.appointment.repository;

import com.appointment.domain.StudentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentScheduleRepository extends JpaRepository<StudentSchedule, Long> {

}
