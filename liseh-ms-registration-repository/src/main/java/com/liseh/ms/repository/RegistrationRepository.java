package com.liseh.ms.repository;

import com.liseh.ms.model.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Registration findByName(String name);
    Registration findByEmail(String email);
    Registration findByPhoneNumber(String email);
}
