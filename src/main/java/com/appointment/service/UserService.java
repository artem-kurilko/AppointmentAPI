package com.appointment.service;

import com.appointment.domain.StudentSchedule;
import com.appointment.domain.TeacherRate;
import com.appointment.domain.TeacherSchedule;
import com.appointment.domain.UniversityUser;
import com.appointment.repository.StudentScheduleRepository;
import com.appointment.repository.TeacherRateRepository;
import com.appointment.repository.TeacherScheduleRepository;
import com.appointment.repository.UniversityUserRepository;
import com.appointment.security.SecurityChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UniversityUserRepository universityUserRepository;
    private final TeacherRateRepository teacherRateRepository;
    private final StudentScheduleRepository studentScheduleRepository;
    private final TeacherScheduleRepository teacherScheduleRepository;
    private final SecurityChecker securityChecker;

    @Autowired
    public UserService(UniversityUserRepository universityUserRepository, TeacherRateRepository teacherRateRepository, StudentScheduleRepository studentScheduleRepository, TeacherScheduleRepository teacherScheduleRepository, SecurityChecker securityChecker) {
        this.universityUserRepository = universityUserRepository;
        this.teacherRateRepository = teacherRateRepository;
        this.studentScheduleRepository = studentScheduleRepository;
        this.teacherScheduleRepository = teacherScheduleRepository;
        this.securityChecker = securityChecker;
    }

    public void saveUser(UniversityUser user){
        universityUserRepository.save(user);
    }

    public UniversityUser getUserByName(String name){
        return universityUserRepository.findByUserName(name);
    }

    public void saveStudentReservation(StudentSchedule studentSchedule) throws Exception {
        String status = "reserved";

        securityChecker.checkIfUserExists(studentSchedule.studentName);
        securityChecker.checkIfUserExists(studentSchedule.teacherName);

        StudentSchedule schedule = new StudentSchedule(studentSchedule.getStudentName(), studentSchedule.getAppointmentDate(), studentSchedule.getAppointmentFinishDate(), studentSchedule.getTeacherName(), status);
        studentScheduleRepository.save(schedule);
    }

    public void cancelStudentReservation(String studentName, Timestamp appointmentDate) throws Exception {
        StudentSchedule reservation = studentScheduleRepository.findScheduleByNameAndAppointmentDate(studentName, appointmentDate);

        securityChecker.checkIfUserExists(studentName);
        studentScheduleRepository.delete(reservation);
    }

    public void saveTeacherSchedule(TeacherSchedule teacherSchedule){
        teacherScheduleRepository.save(teacherSchedule);
    }

    public List<String> getAllTeachers(){
        List<String> teachersName = new LinkedList<>();
        universityUserRepository.findAll().stream().filter(user -> user.getUserType().equals("teacher")).collect(Collectors.toList()).forEach(name -> teachersName.add(name.getUserName()));
        return teachersName;
    }

    public void savePriceRate(TeacherRate teacherRate) throws Exception {

        securityChecker.checkIfUserExists(teacherRate.teacherName);
        securityChecker.checkIfPriceRateAlreadyExists(teacherRate);
        teacherRateRepository.save(teacherRate);
    }
}
