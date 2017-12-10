package com.github.apycazo.api.gateway.demo.features;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("secured")
public class SecuredRestController
{
    private static final Logger log = LoggerFactory.getLogger(SecuredRestController.class);

    @GetMapping
    public Map runProcess () {

        Map<String,Object> map = new LinkedHashMap<>();

        map.put("timestamp", Instant.now().getEpochSecond());
        map.put("secured", true);
        return map;
    }
}
