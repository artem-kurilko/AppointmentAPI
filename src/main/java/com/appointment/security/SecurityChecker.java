package com.appointment.security;

import com.appointment.domain.StudentSchedule;
import com.appointment.domain.TeacherRate;
import com.appointment.domain.TeacherSchedule;
import com.appointment.repository.StudentScheduleRepository;
import com.appointment.repository.TeacherRateRepository;
import com.appointment.repository.TeacherScheduleRepository;
import com.appointment.repository.UniversityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityChecker {
    private final UniversityUserRepository userRepository;
    private final TeacherRateRepository teacherRateRepository;
    private final TeacherScheduleRepository teacherScheduleRepository;
    private final StudentScheduleRepository studentScheduleRepository;

    @Autowired
    public SecurityChecker(UniversityUserRepository userRepository, TeacherRateRepository teacherRateRepository, TeacherScheduleRepository teacherScheduleRepository, StudentScheduleRepository studentScheduleRepository) {
        this.userRepository = userRepository;
        this.teacherRateRepository = teacherRateRepository;
        this.teacherScheduleRepository = teacherScheduleRepository;
        this.studentScheduleRepository = studentScheduleRepository;
    }

    /** check if user/users exist in database */
    public void checkIfUserExists(String ... names) throws Exception {
        for (String name : names) {
            if (userRepository.findAll().stream().noneMatch(user -> user.getUserName().equals(name)))
                throw new Exception("User not found error. There is no user with name " + name + ".");
        }
    }

    /** check if teacher already defined price for the specified time slot */
    public void checkIfPriceRateAlreadyExists(TeacherRate teacherRate) throws Exception {
        if (teacherRateRepository.findAll().stream().filter(user -> user.getTeacherName().equals(teacherRate.getTeacherName())).anyMatch(user -> user.getTime() == teacherRate.getTime()))
            throw new Exception("Cannot add new price rate for time that already exist.");
    }

    /** check that appointment finish date should be after appointment date */
    public void checkTimeSlotValidation(Timestamp appointmentDate, Timestamp appointmentFinishDate) throws Exception {
        if (appointmentDate.after(appointmentFinishDate) || appointmentDate.equals(appointmentFinishDate))
            throw new Exception("Time slot validation error. Appointment date is after or equals appointment finish date");
    }

    /** check if user has a time slot that intersects in time with another time slot */
    public void checkIfTimeSlotIntersectsWithOthers(String userName, String role, Timestamp appointmentDate, Timestamp appointmentFinishDate) throws Exception {
        if (role.equals("teacher")){
            List<TeacherSchedule> schedules = teacherScheduleRepository.findAll().stream().filter(teacher -> teacher.getTeacherName().equals(userName)).collect(Collectors.toList());
            for (TeacherSchedule schedule : schedules){
                if (!appointmentFinishDate.before(schedule.getAppointmentDate()) || !appointmentDate.after(schedule.getAppointmentFinishDate()))
                    throw new Exception("Time slot validation error. Teacher's schedule time slot intersects in time with another time slot.");
            }
            return;
        }else if(role.equals("student")){
            List<StudentSchedule> schedules = studentScheduleRepository.findAll().stream().filter(student -> student.getStudentName().equals(userName)).collect(Collectors.toList());
            for (StudentSchedule schedule : schedules){
                if (!appointmentFinishDate.before(schedule.getAppointmentDate()) || !appointmentDate.after(schedule.getAppointmentFinishDate()))
                    throw new Exception("Time slot validation error. Student's schedule time slot intersects in time with another time slot.");
            }
            return;
        } throw new Exception("User role validation error. Cannot determine user role.");
    }

    /** check if teacher has free time in the specified time slot */
    public void checkIfTeacherScheduleIsFree(String teacherName, Timestamp appointmentDate, Timestamp appointmentFinishDate) throws Exception {
        List<TeacherSchedule> schedules = teacherScheduleRepository.findAll().stream().filter(teacher -> teacher.getTeacherName().equals(teacherName)).collect(Collectors.toList());
        for (TeacherSchedule schedule : schedules){
            if ((appointmentDate.after(schedule.getAppointmentDate()) || appointmentDate.equals(schedule.getAppointmentDate())) && (appointmentFinishDate.before(schedule.getAppointmentFinishDate()) || appointmentFinishDate.equals(schedule.getAppointmentFinishDate())))
                return;
        } throw new Exception("Time slot validation error. Cannot add reservation time slot that doesn't match teacher's schedule.");
    }
}
