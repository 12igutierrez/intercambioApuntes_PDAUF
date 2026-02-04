package com.example.apuntes.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private SecurityUtils() {
    }

    public static String getUsernameActual() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null) {
            return null;
        }
        if (!authentication.isAuthenticated()) {
            return null;
        }
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return authentication.getName();
    }
}
