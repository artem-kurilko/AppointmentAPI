package com.appointment.security;

import com.appointment.domain.TeacherRate;
import com.appointment.repository.TeacherRateRepository;
import com.appointment.repository.UniversityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityChecker {
    private final UniversityUserRepository userRepository;
    private final TeacherRateRepository teacherRateRepository;

    @Autowired
    public SecurityChecker(UniversityUserRepository userRepository, TeacherRateRepository teacherRateRepository) {
        this.userRepository = userRepository;
        this.teacherRateRepository = teacherRateRepository;
    }

    public void checkIfUserExists(String userName) throws Exception {
        if (userRepository.findAll().stream().noneMatch(user -> user.getUserName().equals(userName)))
            throw new Exception("There is no user with name " + userName + ".");
    }

    public void checkIfPriceRateAlreadyExists(TeacherRate teacherRate) throws Exception {
        if (teacherRateRepository.findAll().stream().filter(user -> user.getTeacherName().equals(teacherRate.getTeacherName())).anyMatch(user -> user.getTime() == teacherRate.getTime()))
            throw new Exception("Cannot add new price rate for time that already exist.");
    }

//    public void checkIfStudent
}
