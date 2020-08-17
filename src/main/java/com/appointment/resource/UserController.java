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
import java.sql.Timestamp;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final String DEFAULT_SENDER_NAME = "universityappointmentapi@gmail.com";
    private final String DEFAULT_SENDER_PASSWORD = "A123tmp123key";
    private String senderName;
    private String senderPassword;
    private String recipientName;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody @NotNull UniversityUser universityUser) throws Exception {

        senderName = DEFAULT_SENDER_NAME;
        senderPassword = DEFAULT_SENDER_PASSWORD;
        recipientName = universityUser.getUserEmail();

        new EmailNotification().sendMail(senderName, senderPassword, recipientName);
        userService.saveUser(universityUser);

        return new ResponseEntity<>("User has been created", HttpStatus.OK);
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<String>> showAllTeachers(){

        List<String> teachers = userService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/teacher/schedule")
    public ResponseEntity<String> showTeacherSchedule(@RequestParam @NotNull String teacherName) throws Exception {

        String response = userService.getTeacherSchedule(teacherName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/teacher/schedule")
    public ResponseEntity<String> createTeacherSchedule(@RequestBody @NotNull TeacherSchedule schedule) throws Exception {

        userService.saveTeacherSchedule(schedule);
        return new ResponseEntity<>("Schedule has been created ", HttpStatus.OK);
    }

    @PostMapping("/reservation")
    public ResponseEntity<String> createReservation(@RequestBody @NotNull StudentSchedule reservation) throws Exception {

        UniversityUser user = userService.getUserByName(reservation.getStudentName());
        senderName = DEFAULT_SENDER_NAME;
        senderPassword = DEFAULT_SENDER_PASSWORD;
        recipientName = user.getUserEmail();

        userService.saveStudentReservation(reservation);

//        new EmailNotification().sendMail(senderName, senderPassword, recipientName);
        return new ResponseEntity<>("Reservation has been created", HttpStatus.OK);
    }

    @DeleteMapping("/reservation")
    public ResponseEntity<String> cancelReservation(@RequestParam @NotNull String userName, @RequestParam @NotNull Timestamp appointmentDate) throws Exception {
        UniversityUser user = userService.getUserByName(userName);
        senderName = user.getUserEmail();
        senderPassword = user.getUserEmailPassword();
        recipientName = "";
        userService.cancelStudentReservation(userName, appointmentDate);

//        new EmailNotification().sendMail(senderName, senderPassword, recipientName);
        return new ResponseEntity<>("Reservation has been canceled", HttpStatus.OK);
    }

    @PostMapping("/reservation/apply")
    public ResponseEntity<String> applyReservation(@RequestParam @NotNull String teacherName) throws Exception {
        senderName = "";
        senderPassword = "";
        recipientName = "";

//        new EmailNotification().sendMail(senderName, senderPassword, recipientName);
        return new ResponseEntity<>("Reservation has been applied", HttpStatus.OK);
    }

    @DeleteMapping("/reservation/decline")
    public ResponseEntity<String> declineReservation() throws Exception {
        senderName = "";
        senderPassword = "";
        recipientName = "";

//        new EmailNotification().sendMail(senderName, senderPassword, recipientName);
        return new ResponseEntity<>("Reservation has been declined", HttpStatus.OK);
    }

    @GetMapping("/price-rate")
    public ResponseEntity<String> showTeacherRate(@RequestParam @NotNull String teacherName) throws Exception {

        String response = userService.getPriceRate(teacherName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/price-rate")
    public ResponseEntity<String> setTeacherRate(@RequestBody @NotNull TeacherRate rate) throws Exception {

        userService.savePriceRate(rate);
        return new ResponseEntity<>("Teacher rate has been saved", HttpStatus.OK);
    }
}
