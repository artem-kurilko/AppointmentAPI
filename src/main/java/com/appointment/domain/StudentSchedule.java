package com.appointment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "student_schedule")
@Getter
@Setter
@AllArgsConstructor
public class StudentSchedule extends BaseEntity {

    @NotNull
    @Column(name = "student_name")
    public String studentName;

    @NotNull
    @Column(name = "appointment_date")
    public Timestamp appointmentDate;

    @NotNull
    @Column(name = "appointment_finish_date")
    public Timestamp appointmentFinishDate;

    @NotNull
    @Column(name = "appointment_teacher")
    public String appointmentTeacher;

    @NotNull
    @Column(name = "appointment_status")
    public String appointmentStatus;
}
