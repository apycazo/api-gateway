package com.github.apycazo.api.gateway.provider.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("app")
public class ApiGatewaySettings
{
    private long tokenTimeToLiveInSeconds = 300;
    private String keyAlgorithm = "AES";
    private String secret = "B^#vdaD$ZEM8j<>";
    private String authenticationEndpoint = "http://localhost:8080/register";
    private List<String> publicEndpoints = new ArrayList<>();

    public long getTokenTimeToLiveInSeconds()
    {
        return tokenTimeToLiveInSeconds;
    }

    public void setTokenTimeToLiveInSeconds(long tokenTimeToLiveInSeconds)
    {
        this.tokenTimeToLiveInSeconds = tokenTimeToLiveInSeconds;
    }

    public String getKeyAlgorithm()
    {
        return keyAlgorithm;
    }

    public void setKeyAlgorithm(String keyAlgorithm)
    {
        this.keyAlgorithm = keyAlgorithm;
    }

    public String getSecret()
    {
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    public String getAuthenticationEndpoint()
    {
        return authenticationEndpoint;
    }

    public void setAuthenticationEndpoint(String authenticationEndpoint)
    {
        this.authenticationEndpoint = authenticationEndpoint;
    }

    public List<String> getPublicEndpoints()
    {
        return publicEndpoints;
    }

    public void setPublicEndpoints(List<String> publicEndpoints)
    {
        this.publicEndpoints = publicEndpoints;
    }
}
