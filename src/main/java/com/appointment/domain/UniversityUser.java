package com.appointment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "university_user")
@Getter
@Setter
@AllArgsConstructor
public class UniversityUser extends BaseEntity {

    @NotNull
    @Column(name = "user_type")
    public String userType;

    @NotNull
    @Column(name = "user_name")
    public String userName;

    @NotNull
    @Column(name = "user_email")
    public String userEmail;

}
