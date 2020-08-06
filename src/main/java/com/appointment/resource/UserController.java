package com.appointment.resource;

import com.appointment.domain.TeacherRate;
import com.appointment.domain.UniversityUser;
import com.appointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/user")
    public void createUser(@RequestParam @NotNull  String userType,
                              @RequestParam @NotNull String userName,
                              @RequestParam @NotNull String userEmail){

        UniversityUser newUniversityUser = new UniversityUser(userType, userName, userEmail);
        userService.saveUser(newUniversityUser);
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<UniversityUser>> showAllTeachers(){
        List<UniversityUser> teachers = userService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @PostMapping("/create/reservation")
    public void createReservation(){

    }

    @DeleteMapping("/delete/reservation")
    public void cancelReservation(){

    }

    @PostMapping("/apply/reservation")
    public void applyReservation(){

    }

    @DeleteMapping("/decline/reservation")
    public void declineReservation(){

    }

    @PostMapping("/price_rate")
    public void setTeacherRate(@RequestParam @NotNull String teacherName,
                               @RequestParam @NotNull int time,
                               @RequestParam @NotNull int price) throws Exception {

        TeacherRate rate = new TeacherRate(teacherName, time, price);
        userService.setPriceRate(rate);
    }
}
