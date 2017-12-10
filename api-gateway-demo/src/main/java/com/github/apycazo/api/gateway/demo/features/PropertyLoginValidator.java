package com.github.apycazo.api.gateway.demo.features;

import com.github.apycazo.api.gateway.provider.rest.LoginValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@ConfigurationProperties(prefix = "login")
public class PropertyLoginValidator extends LoginValidator
{
    private static final Logger log = LoggerFactory.getLogger(PropertyLoginValidator.class);
    private Map<String, String> users = new HashMap<>();

    @PostConstruct
    protected void demoInfo()
    {
        if (!users.isEmpty()) {
            log.info("Use any of the following Authorization headers to register:");
            for (String user : users.keySet()) {
                String pass = users.get(user);
                String header = new String(Base64.getEncoder().encode((user + ":" + pass).getBytes()));
                log.info("=> {}: Basic {}", user, header);
            }
        } else {
            log.warn("No users have been registered");
        }
    }

    @Override
    public boolean isAuthValid(String user, String pass)
    {
        if (users.containsKey(user) && !StringUtils.isEmpty(pass)) {
            return pass.equals(users.get(user));
        } else {
            return false;
        }
    }

    public Map<String, String> getUsers()
    {
        return users;
    }

    public void setUsers(Map<String, String> users)
    {
        this.users = users;
    }
}
