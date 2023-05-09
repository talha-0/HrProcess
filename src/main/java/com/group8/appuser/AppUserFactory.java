package com.group8.appuser;

public class AppUserFactory {
    public static AppUser createUser(String role, String firstName, String lastName, String email, String password) {
        if (role.equalsIgnoreCase("employee")) {
            return new Employee(firstName, lastName, email, password);
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
