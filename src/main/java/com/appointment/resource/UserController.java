package com.appointment.resource;

import com.appointment.domain.StudentSchedule;
import com.appointment.domain.TeacherRate;
import com.appointment.domain.TeacherSchedule;
import com.appointment.domain.UniversityUser;
import com.appointment.notification.EmailNotification;
import com.appointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class UserController {
    private final String DEFAULT_SENDER_NAME = "kurilko365@gmail.com";
    private final String DEFAULT_SENDER_PASSWORD = "Cfd802vds36";
    private String SENDER_NAME;
    private String SENDER_PASSWORD;
    private String RECIPIENT_NAME;

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody @NotNull UniversityUser universityUser) throws Exception {
        userService.saveUser(universityUser);

        SENDER_NAME = DEFAULT_SENDER_NAME;
        SENDER_PASSWORD = DEFAULT_SENDER_PASSWORD;
        RECIPIENT_NAME = universityUser.getUserEmail();

//        new EmailNotification().sendMail(SENDER_NAME, SENDER_PASSWORD, RECIPIENT_NAME);
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
    public ResponseEntity<String> createReservation(@RequestBody @NotNull StudentSchedule reservation) throws Exception {

        UniversityUser user = userService.getUserByName(reservation.getStudentName());
        SENDER_NAME = user.getUserEmail();
        SENDER_PASSWORD = user.getUserEmailPassword();
        RECIPIENT_NAME = "";

        userService.saveStudentReservation(reservation);
        new EmailNotification().sendMail(SENDER_NAME, SENDER_PASSWORD, RECIPIENT_NAME);

        return new ResponseEntity<>("Reservation has been created", HttpStatus.OK);
    }

    @DeleteMapping("/reservation")
    public ResponseEntity<String> cancelReservation(@RequestBody @NotNull  StudentSchedule reservation) throws Exception {

        UniversityUser user = userService.getUserByName(reservation.getStudentName());
        SENDER_NAME = user.getUserEmail();
        SENDER_PASSWORD = user.getUserEmailPassword();
        RECIPIENT_NAME = "";

//        userService.createReservation(reservation);
        new EmailNotification().sendMail(SENDER_NAME, SENDER_PASSWORD, RECIPIENT_NAME);

        return new ResponseEntity<>("Reservation has been canceled", HttpStatus.OK);
    }

    @PostMapping("/reservation/apply")
    public ResponseEntity<String> applyReservation(@RequestBody @NotNull TeacherSchedule schedule) throws Exception {
        SENDER_NAME = "";
        SENDER_PASSWORD = "";
        RECIPIENT_NAME = "";

        new EmailNotification().sendMail(SENDER_NAME, SENDER_PASSWORD, RECIPIENT_NAME);
        return new ResponseEntity<>("Reservation has been applied", HttpStatus.OK);
    }

    @DeleteMapping("/reservation/decline")
    public ResponseEntity<String> declineReservation() throws Exception {
        SENDER_NAME = "";
        SENDER_PASSWORD = "";
        RECIPIENT_NAME = "";

        new EmailNotification().sendMail(SENDER_NAME, SENDER_PASSWORD, RECIPIENT_NAME);
        return new ResponseEntity<>("Reservation has been declined", HttpStatus.OK);
    }

    @PostMapping("/price-rate")
    public ResponseEntity<String> setTeacherRate(@RequestBody @NotNull TeacherRate rate) throws Exception {

        userService.savePriceRate(rate);
        return new ResponseEntity<>("Teacher rate has been saved", HttpStatus.OK);
    }
}
