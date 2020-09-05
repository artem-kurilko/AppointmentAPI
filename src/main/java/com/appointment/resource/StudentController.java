package com.appointment.resource;

import com.appointment.domain.StudentSchedule;
import com.appointment.domain.UniversityUser;
import com.appointment.notification.EmailNotification;
import com.appointment.security.SecurityChecker;
import com.appointment.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class StudentController {
    private final UserService userService;
    private final SecurityChecker securityChecker;
    private String recipientName;

    public StudentController(UserService userService, SecurityChecker securityChecker) {
        this.userService = userService;
        this.securityChecker = securityChecker;
    }

    @PostMapping("/user")
    public ResponseEntity<String> createStudent(@RequestBody @NotNull UniversityUser universityUser) {
        recipientName = universityUser.getUserEmail();
        userService.saveUser(universityUser, "student");
        String subject = "Регистрация пользователя";
        String text = "Студент " + universityUser.getUserName() + " был успешно зарегестрирован.";
        try {
            new EmailNotification().sendMail(recipientName, subject, text);
        }catch (Exception e){
            System.out.println("Ошибка отправки сообщения. " + e);
        }
        return new ResponseEntity<>("Student has been created", HttpStatus.OK);
    }

    @PostMapping("/student/reservation")
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

    @DeleteMapping("/student/reservation")
    public ResponseEntity<String> cancelReservation(@RequestBody @NotNull StudentSchedule reservation) throws Exception {
        securityChecker.checkIfUserExists(reservation.getStudentName(), reservation.getTeacherName());
        securityChecker.checkIfReservationExists(reservation);
        userService.cancelStudentReservation(reservation);

        return new ResponseEntity<>("Reservation has been canceled", HttpStatus.OK);
    }
}
