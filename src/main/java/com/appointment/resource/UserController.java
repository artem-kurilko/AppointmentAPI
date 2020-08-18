package com.appointment.resource;

import com.appointment.domain.*;
import com.appointment.notification.EmailNotification;
import com.appointment.security.SecurityChecker;
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
    private final SecurityChecker securityChecker;
    private String recipientName;

    @Autowired
    public UserController(UserService userService, SecurityChecker securityChecker) {
        this.userService = userService;
        this.securityChecker = securityChecker;
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody @NotNull UniversityUser universityUser) throws Exception {

        recipientName = universityUser.getUserEmail();
        userService.saveUser(universityUser);
        new EmailNotification().sendMail(EmailType.REGISTERED, recipientName);
        return new ResponseEntity<>("User has been created", HttpStatus.OK);
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<String>> showAllTeachers(){

        List<String> teachers = userService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/teacher/schedule")
    public ResponseEntity<String> showTeacherSchedule(@RequestParam @NotNull String teacherName) throws Exception {

        securityChecker.checkIfUserExists(teacherName);
        String response = userService.getTeacherSchedule(teacherName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/teacher/schedule")
    public ResponseEntity<String> createTeacherSchedule(@RequestBody @NotNull TeacherSchedule schedule) throws Exception {

        securityChecker.checkIfUserExists(schedule.getTeacherName());
        userService.saveTeacherSchedule(schedule);
        return new ResponseEntity<>("Schedule has been created ", HttpStatus.OK);
    }

    @PostMapping("/reservation")
    public ResponseEntity<String> createReservation(@RequestBody @NotNull StudentSchedule reservation) throws Exception {
        securityChecker.checkIfUserExists(reservation.getStudentName(), reservation.getTeacherName());
        securityChecker.checkTimeSlotValidation(reservation.getAppointmentDate(), reservation.getAppointmentFinishDate());
        securityChecker.checkIfTeacherScheduleIsFree(reservation.getTeacherName(), reservation.getAppointmentDate(), reservation.getAppointmentFinishDate());

        UniversityUser user = userService.getUserByName(reservation.getTeacherName());
        recipientName = user.getUserEmail();
        userService.saveStudentReservation(reservation);

        new EmailNotification().sendMail(EmailType.RESERVED, recipientName);
        return new ResponseEntity<>("Reservation has been created", HttpStatus.OK);
    }

    @DeleteMapping("/reservation")
    public ResponseEntity<String> cancelReservation(@RequestBody @NotNull StudentSchedule reservation) throws Exception {
        securityChecker.checkIfUserExists(reservation.getStudentName(), reservation.getTeacherName());
        UniversityUser user = userService.getUserByName(reservation.getStudentName());
        recipientName = user.getUserEmail();
        userService.cancelStudentReservation(reservation);

        return new ResponseEntity<>("Reservation has been canceled", HttpStatus.OK);
    }

    @PostMapping("/reservation/apply")
    public ResponseEntity<String> applyReservation(@RequestParam @NotNull String teacherName) throws Exception {

        securityChecker.checkIfUserExists(teacherName);
        return new ResponseEntity<>("Reservation has been applied", HttpStatus.OK);
    }

    @DeleteMapping("/reservation/decline")
    public ResponseEntity<String> declineReservation() throws Exception {

        securityChecker.checkIfUserExists();
        String recipientName = "";
        new EmailNotification().sendMail(EmailType.RESERVED, recipientName);
        return new ResponseEntity<>("Reservation has been declined", HttpStatus.OK);
    }

    @GetMapping("/price-rate")
    public ResponseEntity<String> showTeacherRate(@RequestParam @NotNull String teacherName) throws Exception {

        securityChecker.checkIfUserExists(teacherName);
        String response = userService.getPriceRate(teacherName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/price-rate")
    public ResponseEntity<String> setTeacherRate(@RequestBody @NotNull TeacherRate rate) throws Exception {
        securityChecker.checkIfUserExists(rate.getTeacherName());
        securityChecker.checkIfPriceRateAlreadyExists(rate);

        userService.savePriceRate(rate);
        return new ResponseEntity<>("Teacher rate has been saved", HttpStatus.OK);
    }
}
