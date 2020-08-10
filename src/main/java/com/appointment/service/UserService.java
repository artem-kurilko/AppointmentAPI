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

import java.util.List;
import java.util.stream.Stream;

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

    public void saveStudentReservation(StudentSchedule studentSchedule){
        studentScheduleRepository.save(studentSchedule);
    }

    /*public TeacherSchedule getTeacherSchedule(String teacherName){
        teacherScheduleRepository.
    }*/

    public void saveTeacherSchedule(TeacherSchedule teacherSchedule){
        teacherScheduleRepository.save(teacherSchedule);
    }

    public void createReservation(){

    }

    public void cancelReservation(){

    }

    public void applyReservation(){

    }

    public void declineReservation(){

    }

    public Stream<UniversityUser> getAllTeachers(){
        Stream<UniversityUser> teachers = universityUserRepository.findAll().stream().filter(user -> user.getUserType() == "teacher");
        return teachers;
    }

    public void setPriceRate(TeacherRate teacherRate) throws Exception {
        List<TeacherRate> rates = teacherRateRepository.findAllRatesByTeacherName(teacherRate.getTeacherName());

        if (rates.stream().anyMatch(rate -> rate.getTime() == teacherRate.getTime()))
            throw new Exception("Cannot add new price rate for time that already exist.");
        else{
            TeacherRate rate = teacherRateRepository.findTeacherRateByNameAndTime(teacherRate.getTeacherName(), teacherRate.getTime());
            teacherRateRepository.delete(rate);
            teacherRateRepository.save(teacherRate);
        }
    }
}
