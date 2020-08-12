package com.appointment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
    @Column(name = "schedule", columnDefinition = "TEXT")
    @Convert(converter= JSONObjectConverter.class)
    public JSONObject schedule;
}
