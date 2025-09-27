package com.example.szamlakezelo.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPT = 3;
    private ConcurrentHashMap<String, Integer> attemptsCache = new ConcurrentHashMap<>();

    public void loginFailed(String username) {
        int attempts = attemptsCache.getOrDefault(username, 0);
        attempts++;
        attemptsCache.put(username, attempts);
    }

    public void loginSucceeded(String username) {
        attemptsCache.remove(username);
    }

    public boolean isCaptchaRequired(String username) {
        return attemptsCache.getOrDefault(username, 0) >= MAX_ATTEMPT;
    }
}