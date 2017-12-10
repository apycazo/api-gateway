package com.github.apycazo.api.gateway.provider.interceptor;

import com.github.apycazo.api.gateway.provider.core.ApiGatewaySettings;
import com.github.apycazo.api.gateway.provider.core.RateLimiter;
import com.github.apycazo.api.gateway.provider.core.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@ConditionalOnProperty(value = "app.gatewayEnabled", havingValue = "true", matchIfMissing = true)
public class ApiGatewayInterceptor extends HandlerInterceptorAdapter
{
    private static final Logger log = LoggerFactory.getLogger(ApiGatewayInterceptor.class);

    @Autowired
    private ApiGatewaySettings apiGatewaySettings;
    @Autowired
    private SessionManager sessionManager;
    @Autowired(required = false)
    private RateLimiter rateLimiter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        // makes sure requests contains auth header
        String requestURI = request.getRequestURI();
        if (apiGatewaySettings.getPublicEndpoints().contains(requestURI)) {
            log.debug("Endpoint {} is public", requestURI);
            return true;
        } else {
            // this is a secured endpoint
            String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.isEmpty(auth) || !auth.toLowerCase().startsWith("bearer ")) {
                response.setHeader(HttpHeaders.WWW_AUTHENTICATE, apiGatewaySettings.getAuthenticationEndpoint());
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return false;
            } else {
                String token = auth.substring(7);
                if (!sessionManager.isSessionValid(token)) {
                    response.setHeader(HttpHeaders.WWW_AUTHENTICATE, apiGatewaySettings.getAuthenticationEndpoint());
                    response.sendError(HttpStatus.UNAUTHORIZED.value());
                    return false;
                } else if (rateLimiter == null || rateLimiter.isRequestAcceptable(request)) {
                    return true;
                } else {
                    response.sendError(HttpStatus.TOO_MANY_REQUESTS.value());
                    return false;
                }
            }
        }
    }
}
