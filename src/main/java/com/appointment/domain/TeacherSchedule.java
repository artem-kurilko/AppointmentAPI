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

@Entity
@Table(name = "teacher_schedule")
@Getter
@Setter
@AllArgsConstructor
public class TeacherSchedule extends BaseEntity {

    @NotNull
    @Column(name = "teacher_name")
    public String teacherName;

  /*  @NotNull
    @Column(name = "schedule")
    public JSONArray schedule;*/
}
