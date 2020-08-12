package com.appointment.service;

import com.appointment.domain.StudentSchedule;
import com.appointment.domain.TeacherRate;
import com.appointment.domain.TeacherSchedule;
import com.appointment.domain.UniversityUser;
import com.appointment.repository.StudentScheduleRepository;
import com.appointment.repository.TeacherRateRepository;
import com.appointment.repository.TeacherScheduleRepository;
import com.appointment.repository.UniversityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UniversityUserRepository universityUserRepository;
    private final TeacherRateRepository teacherRateRepository;
    private final StudentScheduleRepository studentScheduleRepository;
    private final TeacherScheduleRepository teacherScheduleRepository;

    @Autowired
    public UserService(UniversityUserRepository universityUserRepository, TeacherRateRepository teacherRateRepository, StudentScheduleRepository studentScheduleRepository, TeacherScheduleRepository teacherScheduleRepository) {
        this.universityUserRepository = universityUserRepository;
        this.teacherRateRepository = teacherRateRepository;
        this.studentScheduleRepository = studentScheduleRepository;
        this.teacherScheduleRepository = teacherScheduleRepository;
    }

    public void saveUser(UniversityUser user){
        universityUserRepository.save(user);
    }

    public UniversityUser getUserByName(String name){
        return universityUserRepository.findByUserName(name);
    }

    public void saveStudentReservation(StudentSchedule studentSchedule){
        studentScheduleRepository.save(studentSchedule);
    }

    public void cancelStudentReservation(StudentSchedule reservation){
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
        if (universityUserRepository.findAll().stream().noneMatch(user -> user.getUserName().equals(teacherRate.getTeacherName()))){
            throw new Exception("Cannot add price rate as there is no teacher with such name.");
        }

        if (teacherRateRepository.findAll().stream().anyMatch(user -> user.getTime() == teacherRate.getTime()))
            throw new Exception("Cannot add new price rate for time that already exist.");
        else
            teacherRateRepository.save(teacherRate);
    }
}
