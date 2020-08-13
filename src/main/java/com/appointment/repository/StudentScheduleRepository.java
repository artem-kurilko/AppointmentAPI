package com.appointment.repository;

import com.appointment.domain.StudentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface StudentScheduleRepository extends JpaRepository<StudentSchedule, Long> {

    @Query("select u from StudentSchedule u where u.studentName = ?1 and u.appointmentDate = ?2")
    StudentSchedule findScheduleByNameAndAppointmentDate(String studentName, Timestamp appointmentDate);
}
