package com.appointment.service;

import com.appointment.domain.TeacherRate;
import com.appointment.domain.UniversityUser;
import com.appointment.repository.TeacherRateRepository;
import com.appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TeacherRateRepository teacherRateRepository;

    @Autowired
    public UserService(UserRepository userRepository, TeacherRateRepository teacherRateRepository) {
        this.userRepository = userRepository;
        this.teacherRateRepository = teacherRateRepository;
    }

    public void saveUser(UniversityUser user){
        userRepository.save(user);
    }

    public void createReservation(){

    }

    public void cancelReservation(){

    }

    public void applyReservation(){

    }

    public void declineReservation(){

    }

    public List<UniversityUser> getAllTeachers(){
        List<UniversityUser> teachers = (List<UniversityUser>) userRepository.findAll().stream().filter(user -> user.getUserType() == "teacher");
        return teachers;
    }

    public void setPriceRate(TeacherRate teacherRate) throws Exception {
        List<TeacherRate> rates = teacherRateRepository.findAllRatesByTeacherName(teacherRate.getTeacherName());

        if (rates.stream().anyMatch(rate -> rate.getTime() == teacherRate.getTime()))
            throw new Exception("Cannot add new price rate for time that already exist.");
        else
            teacherRateRepository.save(teacherRate);
    }
}
