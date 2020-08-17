package com.appointment.security;

import com.appointment.domain.TeacherRate;
import com.appointment.repository.TeacherRateRepository;
import com.appointment.repository.TeacherScheduleRepository;
import com.appointment.repository.UniversityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class SecurityChecker {
    private final UniversityUserRepository userRepository;
    private final TeacherRateRepository teacherRateRepository;
    private final TeacherScheduleRepository teacherScheduleRepository;

    @Autowired
    public SecurityChecker(UniversityUserRepository userRepository, TeacherRateRepository teacherRateRepository, TeacherScheduleRepository teacherScheduleRepository) {
        this.userRepository = userRepository;
        this.teacherRateRepository = teacherRateRepository;
        this.teacherScheduleRepository = teacherScheduleRepository;
    }

    // check if user exists in database
    public void checkIfUserExists(String userName) throws Exception {
        if (userRepository.findAll().stream().noneMatch(user -> user.getUserName().equals(userName)))
            throw new Exception("There is no user with name " + userName + ".");
    }

    // check if teacher already defined price for the specified timeframe
    public void checkIfPriceRateAlreadyExists(TeacherRate teacherRate) throws Exception {
        if (teacherRateRepository.findAll().stream().filter(user -> user.getTeacherName().equals(teacherRate.getTeacherName())).anyMatch(user -> user.getTime() == teacherRate.getTime()))
            throw new Exception("Cannot add new price rate for time that already exist.");
    }

    public void validateTimeframe(Timestamp appointmentDate, Timestamp appointmentFinishDate) throws Exception {
        if (appointmentDate.after(appointmentFinishDate) || appointmentDate.equals(appointmentFinishDate))
            throw new Exception("Timeframe validation error. Appointment date is after or equals appointment finish date");
    }

    // check if teacher has free time in the specified timeframe
    public void checkIfTeacherScheduleIsFree(String teacherName, Timestamp appointmentDate, Timestamp appointmentFinishDate){

    }
}
