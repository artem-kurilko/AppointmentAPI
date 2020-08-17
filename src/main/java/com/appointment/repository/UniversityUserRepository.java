package com.appointment.repository;

import com.appointment.domain.UniversityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityUserRepository extends JpaRepository<UniversityUser, Long> {

    @Query("select u from UniversityUser u where u.userName = ?1")
    UniversityUser findByUserName(String name);
}
