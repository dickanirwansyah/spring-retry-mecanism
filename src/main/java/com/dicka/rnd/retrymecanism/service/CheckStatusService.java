package com.dicka.rnd.retrymecanism.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CheckStatusService {

    @Autowired
    private RestTemplate restTemplate;

    @Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(4000)) /** 4 detik **/
    public String checkAvailableStatus(String trackingNumber){

        log.info("DO CALL API SOMETHING  TO GET STATUS");
        /** do something call another api **/
        if (callJsonPlaceHolder(Integer.valueOf(trackingNumber)).isEmpty() || callJsonPlaceHolder(Integer.valueOf(trackingNumber)) == ""){
            throw new RuntimeException("service not available");
        }
        return callJsonPlaceHolder(Integer.valueOf(trackingNumber));
    }

    private String callJsonPlaceHolder(int exNumber){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> stringHttpEntity = new HttpEntity<String>(httpHeaders);
        String data = restTemplate.exchange("https://sonplaceholder.typicode.com/posts/"+exNumber,
                HttpMethod.GET, stringHttpEntity, String.class).getBody();
        return data;
    }

    @Recover
    public String fallbackRetry(){
        log.info("sorry service down..");
        return "Please try after some time !!";
    }
}
