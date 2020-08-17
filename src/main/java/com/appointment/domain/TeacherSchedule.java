package com.appointment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "teacher_schedule")
@TypeDefs(@TypeDef(name = "string-array", typeClass = StringArrayType.class))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSchedule extends BaseEntity {

    @NotNull
    @Column(name = "teacher_name")
    public String teacherName;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "appointment_date")
    public Timestamp appointmentDate;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "appointment_finish_date")
    public Timestamp appointmentFinishDate;

    @Column(name = "students")
    @Type(type = "string-array" )
    public String[] students;
}
