package com.geonlee.api.domin.greeting;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GEONLEE
 * @since 2024-07-15
 */
@RestController
public class GreetingController {

    @GetMapping(value = "/greeting", produces = MediaType.TEXT_PLAIN_VALUE)
    public String greeting() {
        return "Hello world!";
    }
}
