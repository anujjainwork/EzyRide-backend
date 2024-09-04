package com.example.ezyride.EzyRide.advices;

import com.example.ezyride.EzyRide.exceptions.ResourceNotFoundException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class WebSocketExceptionHandler {

    @MessageExceptionHandler(ResourceNotFoundException.class)
    @SendToUser("/queue/errors")
    public String handleResourceNotFoundException(ResourceNotFoundException exception){
        return exception.getMessage();
    }
}
