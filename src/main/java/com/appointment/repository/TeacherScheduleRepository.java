package com.appointment.repository;

import com.appointment.domain.TeacherSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherScheduleRepository extends JpaRepository<TeacherSchedule, Long> {

}
