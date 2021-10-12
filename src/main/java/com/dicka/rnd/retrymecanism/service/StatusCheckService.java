package com.dicka.rnd.retrymecanism.service;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class StatusCheckService {

    /** using resilience4j **/
    private int attemps = 1;

    @Autowired
    private RestTemplate restTemplate;

    @Retry(name = "exampleService", fallbackMethod = "fallbackRetry")
    public String responseData(String trackNumber){
        log.info("calling service : {}",attemps++);
        String responseData = restTemplate.getForObject("https://sonplaceholder.typicode.com/posts/"+trackNumber, String.class);
        log.info("calling service to get data dummy");
        return responseData;
    }

    public String fallbackRetry(Exception e){
        log.info("error because : {}", e.getMessage());
        attemps=1;
        return "maaf coba lagi lain kali";
    }
}
