package com.uncledavecode.methodsauthorization.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ConditionEvaluator {

    public boolean canPreAuth3(String param, Authentication authentication) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return param.equals(authentication.getName()) && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("write"));
    }
}
