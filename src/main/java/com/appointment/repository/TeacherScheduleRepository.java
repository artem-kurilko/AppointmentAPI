package com.appointment.repository;

import com.appointment.domain.TeacherSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface TeacherScheduleRepository extends JpaRepository<TeacherSchedule, Long> {

    @Query("select u from TeacherSchedule u where u.teacherName = ?1 and u.appointmentDate = ?2")
    TeacherSchedule findByNameAndAppointmentDate(String teacherName, Timestamp appointmentDate);

    @Query("update TeacherSchedule u set u.students =?3 where u.teacherName =?1 and u.appointmentDate =?2")
    void addStudentToSchedule(String teacherName, Timestamp appointmentDate, String studentName);
}
