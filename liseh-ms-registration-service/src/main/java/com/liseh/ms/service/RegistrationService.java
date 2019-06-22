package com.liseh.ms.service;

import com.liseh.ms.model.dto.RegistrationDto;
import com.liseh.ms.model.entity.Registration;

public interface RegistrationService {
    Registration createRegistration(RegistrationDto registrationDto);
}
