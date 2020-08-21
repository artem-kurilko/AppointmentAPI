package com.appointment.resource;

import com.appointment.domain.*;
import com.appointment.notification.EmailNotification;
import com.appointment.security.SecurityChecker;
import com.appointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
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
    public ResponseEntity<String> createUser(@RequestBody @NotNull UniversityUser universityUser) {
        recipientName = universityUser.getUserEmail();
        userService.saveUser(universityUser);
        String subject = "Регистрация пользователя";
        String text = "Пользователь " + universityUser.getUserName() + " был успешно зарегестрирован.";
        try {
            new EmailNotification().sendMail(recipientName, subject, text);
        }catch (Exception e){
            System.out.println("Ошибка отправки сообщения. " + e);
        }
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

    @PostMapping(path = "/teacher/schedule")
    public ResponseEntity<String> createTeacherSchedule(@RequestBody @NotNull TeacherSchedule schedule) throws Exception {

        securityChecker.checkIfUserExists(schedule.getTeacherName());
        securityChecker.checkTimeSlotValidation(schedule.getAppointmentDate(), schedule.getAppointmentFinishDate());
        securityChecker.checkIfTimeSlotIntersectsWithOthers(schedule.getTeacherName(), "teacher", schedule.getAppointmentDate(), schedule.getAppointmentFinishDate());

        userService.saveTeacherSchedule(schedule);
        return new ResponseEntity<>("Schedule has been created ", HttpStatus.OK);
    }

    @PostMapping("/reservation")
    public ResponseEntity<String> createReservation(@RequestBody @NotNull StudentSchedule reservation) throws Exception {

        securityChecker.checkIfUserExists(reservation.getStudentName(), reservation.getTeacherName());
        securityChecker.checkTimeSlotValidation(reservation.getAppointmentDate(), reservation.getAppointmentFinishDate());
        securityChecker.checkIfTeacherScheduleIsFree(reservation.getTeacherName(), reservation.getAppointmentDate(), reservation.getAppointmentFinishDate());
        securityChecker.checkIfTimeSlotIntersectsWithOthers(reservation.getStudentName(), "student", reservation.getAppointmentDate(), reservation.getAppointmentFinishDate());

        String subject = "Уведомление о резервации";
        String text = "Студент " + reservation.getStudentName() + " зарезервировал у вас время.\nДанные о резервации:\nИмя студента " + reservation.getStudentName() + "\nВремя резервации: " + reservation.getAppointmentDate() + " - " + reservation.getAppointmentFinishDate() + "\nПожалуйста отмените или подтвердите резервацию.";
        userService.saveStudentReservation(reservation);
        recipientName = userService.getUserByName(reservation.getStudentName()).getUserEmail();
        try {
            new EmailNotification().sendMail(recipientName, subject, text);
        } catch (Exception e){
            System.out.println("Ошибка отправки сообщения. " + e);
        }
        return new ResponseEntity<>("Reservation has been created", HttpStatus.OK);
    }

    @DeleteMapping("/reservation")
    public ResponseEntity<String> cancelReservation(@RequestBody @NotNull StudentSchedule reservation) throws Exception {
        securityChecker.checkIfUserExists(reservation.getStudentName(), reservation.getTeacherName());
        securityChecker.checkIfReservationExists(reservation);
        userService.cancelStudentReservation(reservation);

        return new ResponseEntity<>("Reservation has been canceled", HttpStatus.OK);
    }

    @PostMapping("/reservation/apply")
    public ResponseEntity<String> applyReservation(@RequestBody @NotNull StudentSchedule reservation, @RequestParam @NotNull Timestamp teachersAppointmentDate) throws Exception {
        securityChecker.checkIfUserExists(reservation.getStudentName(), reservation.getTeacherName());
        securityChecker.checkIfReservationExists(reservation);

        userService.approveStudentReservation(reservation, teachersAppointmentDate);
        return new ResponseEntity<>("Reservation has been applied", HttpStatus.OK);
    }

    @DeleteMapping("/reservation/decline")
    public ResponseEntity<String> declineReservation(@RequestBody @NotNull StudentSchedule reservation) throws Exception {
        securityChecker.checkIfUserExists(reservation.getStudentName(), reservation.getTeacherName());
        securityChecker.checkIfReservationExists(reservation);

        userService.declineStudentReservation(reservation);
        String recipientName = userService.getUserByName(reservation.getStudentName()).getUserEmail();
        String subject = "Уведомление о резервации";
        String text = "Преподователь отклонил вашу резервацию.\nДанные о резервации:\n" + "Преподователь " + reservation.getTeacherName() + "\nВремя резервации "+ reservation.getAppointmentDate() + " - " + reservation.getAppointmentFinishDate() + "\nПожалуйста отмените её в вашем расписании.";
        try {
            new EmailNotification().sendMail(recipientName, subject, text);
        }catch (Exception e){
            System.out.println("Ошибка отправки сообщения. " + e);
        }
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
