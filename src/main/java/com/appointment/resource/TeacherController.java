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
public class TeacherController {
    private final UserService userService;
    private final SecurityChecker securityChecker;
    private String recipientName;

    @Autowired
    public TeacherController(UserService userService, SecurityChecker securityChecker) {
        this.userService = userService;
        this.securityChecker = securityChecker;
    }

    @PostMapping("/teacher")
    public ResponseEntity<String> createTeacher(@RequestBody @NotNull UniversityUser universityUser) {
        recipientName = universityUser.getUserEmail();
        userService.saveUser(universityUser, "teacher");
        String subject = "Регистрация пользователя";
        String text = "Учитель " + universityUser.getUserName() + " был успешно зарегестрирован.";
        try {
            new EmailNotification().sendMail(recipientName, subject, text);
        }catch (Exception e){
            System.out.println("Ошибка отправки сообщения. " + e);
        }
        return new ResponseEntity<>("Teacher has been created", HttpStatus.OK);
    }

    @GetMapping("/teacher/all")
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

    @PostMapping("/teacher/reservation")
    public ResponseEntity<String> applyReservation(@RequestBody @NotNull StudentSchedule reservation, @RequestParam @NotNull Timestamp teachersAppointmentDate) throws Exception {
        securityChecker.checkIfUserExists(reservation.getStudentName(), reservation.getTeacherName());
        securityChecker.checkIfReservationExists(reservation);

        userService.approveStudentReservation(reservation, teachersAppointmentDate);
        return new ResponseEntity<>("Reservation has been applied", HttpStatus.OK);
    }

    @DeleteMapping("/teacher/reservation")
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

    @GetMapping("/teacher/price-rate")
    public ResponseEntity<String> showTeacherRate(@RequestParam @NotNull String teacherName) throws Exception {

        securityChecker.checkIfUserExists(teacherName);
        String response = userService.getPriceRate(teacherName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/teacher/price-rate")
    public ResponseEntity<String> setTeacherRate(@RequestBody @NotNull TeacherRate rate) throws Exception {
        securityChecker.checkIfUserExists(rate.getTeacherName());
        securityChecker.checkIfPriceRateAlreadyExists(rate);

        userService.savePriceRate(rate);
        return new ResponseEntity<>("Teacher rate has been saved", HttpStatus.OK);
    }
}
