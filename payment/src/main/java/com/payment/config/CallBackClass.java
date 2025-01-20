package com.payment.config;

import org.springframework.stereotype.Component;

@Component
public class CallBackClass {

    public String fallback(Exception ex){
        return " Fallback Response : Shopping service is unavailable!";
    }
}
