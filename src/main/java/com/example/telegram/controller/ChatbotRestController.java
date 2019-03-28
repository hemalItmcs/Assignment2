package com.example.telegram.controller;

import com.example.telegram.service.ChatbotService;
import com.example.telegram.utility.ResponseGenerateUtility;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatbot")
public class ChatbotRestController {

    @Autowired
    ChatbotService chatbotService;
    
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @RequestMapping(value = "/{opration}", method = RequestMethod.GET)
    public ResponseEntity chatbot(@PathVariable("opration") int opration, @RequestParam("username") String username, @RequestParam("location") String location) {
        try {
            if (opration> 0 && opration <= 3) {
                logger.info("Operation is valid");
                if (username != null && !username.trim().isEmpty()) {
                    logger.info("Username is not empty");
                    if (location != null && !location.trim().isEmpty()) {
                        logger.info("Location is not empty");
                        Map<String, Object> response = chatbotService.processRequest(opration, location);
                        if (!response.containsKey("error")) {
                            
                            return new ResponseEntity<>(ResponseGenerateUtility.getSuccessResponse(opration == 1 ? "Weather condition successfully fetched" : (opration == 2 ? "News successfully Fetched" : "Restaurant successfully fetched"), response), HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(ResponseGenerateUtility.getErrorResponse(String.valueOf(response.get("error"))), HttpStatus.OK);
                        }
                    } else {
                        return new ResponseEntity<>(ResponseGenerateUtility.getErrorResponse("Location is required"), HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>(ResponseGenerateUtility.getErrorResponse("Username is required"), HttpStatus.BAD_REQUEST);
                }
            } else {
                logger.info("Operation is not valid");
                return new ResponseEntity<>(ResponseGenerateUtility.getErrorResponse("Invalid Operation Type"), HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ResponseGenerateUtility.getErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
