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
@Table(name = "student_schedule")
@Getter
@Setter
@AllArgsConstructor
public class StudentSchedule extends BaseEntity {

    @NotNull
    @Column(name = "student_name")
    public String studentName;

  /*  @NotNull
    @Column(name = "schedule")
    public JSONArray schedule;*/
}
