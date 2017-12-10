package com.github.apycazo.api.gateway.provider.rest;

public abstract class LoginValidator
{
    public abstract boolean isAuthValid (String user, String pass);
}
