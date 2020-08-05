package com.appointment.repository;

import com.appointment.domain.UniversityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UniversityUser, Long> {
}
