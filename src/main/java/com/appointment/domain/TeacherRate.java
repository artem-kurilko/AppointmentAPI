package com.appointment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "teacher_rate")
@Getter
@Setter
public class TeacherRate extends BaseEntity{

    @NotNull
    @Column(name = "teacher_name")
    public String teacherName;

    @NotNull
    @Column(name = "time")
    public int time;

    @NotNull
    @Column(name = "price")
    public int price;

    public TeacherRate(@NotNull String teacherName, @NotNull int time, @NotNull int price) {
        this.teacherName = teacherName;
        this.time = time;
        this.price = price;
    }
}
