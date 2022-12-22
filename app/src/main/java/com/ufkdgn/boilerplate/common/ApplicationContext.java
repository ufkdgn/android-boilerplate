package com.ufkdgn.boilerplate.common;

public class ApplicationContext {
    private static ApplicationContext _instance;

    private ApplicationContext() {
    }

    public static ApplicationContext getInstance() {
        if (_instance == null) {
            _instance = new ApplicationContext();
        }
        return _instance;
    }

    private String authorizationToken;

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }
}