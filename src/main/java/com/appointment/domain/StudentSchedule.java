package com.appointment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class StudentSchedule extends BaseEntity {

    @NotNull
    @Column(name = "student_name")
    public String studentName;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "appointment_date")
    public Timestamp appointmentDate;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "appointment_finish_date")
    public Timestamp appointmentFinishDate;

    @NotNull
    @Column(name = "teacher_name")
    public String teacherName;

    @Column(name = "status")
    public String status;
}
