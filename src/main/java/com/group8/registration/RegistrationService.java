package com.group8.registration;

import com.group8.appuser.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;

    public String register(String role, RegistrationRequest registrationRequest) {
        AppUser appUser= AppUserFactory.createUser(role, registrationRequest.getFirstName(), registrationRequest.getLastName(), registrationRequest.getEmail(), registrationRequest.getPassword());
        String token = appUserService.signUpUser(appUser);
        if(token.equals("emailTaken"))
            return token;
        appUserService.enableAppUser(appUser.getEmail());
        return "confirmed";
    }
}
