package com.appointment.service;

import com.appointment.domain.UniversityUser;
import com.appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public void setPriceRate(){

    }
}
