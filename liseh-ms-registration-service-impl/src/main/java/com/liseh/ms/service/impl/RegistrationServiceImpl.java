package com.liseh.ms.service.impl;

import com.liseh.ms.model.dto.RegistrationDto;
import com.liseh.ms.model.entity.Registration;
import com.liseh.ms.repository.RegistrationRepository;
import com.liseh.ms.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public Registration createRegistration(RegistrationDto registrationDto) {
        Registration registration = new Registration();
        registration.setName(registrationDto.getName());
        registration.setEmail(registrationDto.getEmail());
        registration.setPhoneNumber(registrationDto.getPhoneNumber());
        registration.setPassword(registrationDto.getPassword());
        registrationRepository.save(registration);
        return registration;
    }
}
