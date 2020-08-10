package com.appointment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "teacher_schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSchedule extends BaseEntity {

    @NotNull
    @Column(name = "teacher_name")
    public String teacherName;

    @NotNull
    @Column(name = "schedule")
    public JSONArray schedule;

    @Column(name = "students")
    public String[] students;

    @NotNull
    @Column(name = "type")
    public String type;
}
