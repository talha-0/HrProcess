package com.group8.registration;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest{
    private String firstName,lastName, email, password;
}