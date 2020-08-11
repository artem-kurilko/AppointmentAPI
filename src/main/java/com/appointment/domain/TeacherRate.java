package com.appointment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "teacher_rate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRate extends BaseEntity{

    @NotNull
    @Column(name = "teacher_name")
    public String teacherName;

    @Column(name = "rate", columnDefinition = "TEXT")
    @Convert(converter= JSONArrayConverter.class)
    public JSONArray jsonData;
}
