package com.appointment.resource;

import com.appointment.domain.StudentSchedule;
import com.appointment.domain.TeacherRate;
import com.appointment.domain.TeacherSchedule;
import com.appointment.domain.UniversityUser;
import com.appointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody @NotNull UniversityUser universityUser){
        userService.saveUser(universityUser);
        return new ResponseEntity<>("User has been created", HttpStatus.OK);
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<String>> showAllTeachers(){
        List<String> teachers = userService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @PostMapping("/teacher/schedule")
    public ResponseEntity<String> createTeacherSchedule(@RequestBody @NotNull TeacherSchedule schedule){
        userService.saveTeacherSchedule(schedule);
        return new ResponseEntity<>("Schedule has been created ", HttpStatus.OK);
    }

    @PostMapping("/reservation")
    public ResponseEntity<String> createReservation(@RequestBody @NotNull StudentSchedule reservation){
        userService.saveStudentReservation(reservation);
        return new ResponseEntity<>("Reservation has been created", HttpStatus.OK);
    }

    @DeleteMapping("/reservation")
    public void cancelReservation(){

    }

    @PostMapping("/reservation/apply")
    public void applyReservation(){

    }

    @DeleteMapping("/reservation/decline")
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
