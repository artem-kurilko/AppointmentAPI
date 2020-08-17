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

    public void saveStudentReservation(StudentSchedule studentSchedule) {
        String status = "reserved";


        StudentSchedule schedule = new StudentSchedule(studentSchedule.getStudentName(), studentSchedule.getAppointmentDate(), studentSchedule.getAppointmentFinishDate(), studentSchedule.getTeacherName(), status);
        studentScheduleRepository.save(schedule);
    }

    public void cancelStudentReservation(StudentSchedule reservation) {
        studentScheduleRepository.delete(reservation);
    }

    public void saveTeacherSchedule(TeacherSchedule teacherSchedule) {
        teacherScheduleRepository.save(teacherSchedule);
    }

    public String getTeacherSchedule(String teacherName) {
        String response = "Расписание преподователя " + teacherName + ":\n";

        List<TeacherSchedule> schedules = teacherScheduleRepository.findAll().stream().filter(teacher -> teacher.getTeacherName().equals(teacherName)).collect(Collectors.toList());
        for (int i = 0; i < schedules.size(); i++){
            response += schedules.get(i).getAppointmentDate() + " - ";
            response += schedules.get(i).getAppointmentFinishDate() + "\n";
        }

        return response;
    }

    public List<String> getAllTeachers(){
        List<String> teachersName = new LinkedList<>();
        universityUserRepository.findAll().stream().filter(user -> user.getUserType().equals("teacher")).collect(Collectors.toList()).forEach(name -> teachersName.add(name.getUserName()));
        return teachersName;
    }

    public String getPriceRate(String teacherName) {
        String response = "Стоимость занятий у " + teacherName + ":\n";
        List<TeacherRate> teacherRates = teacherRateRepository.findAllRatesByTeacherName(teacherName);

        for (int i = 0; i < teacherRates.size(); i++){
            response += teacherRates.get(i).getTime() + " минут = " + teacherRates.get(i).getPrice() + "грн \n";
        }

        return response;
    }

    public void savePriceRate(TeacherRate teacherRate) {
        teacherRateRepository.save(teacherRate);
    }
}
