package com.example.telegram.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.telegram.utility.CommonUtility;
import com.example.telegram.utility.ResponseGenerateUtility;

@RestController
@RequestMapping("/user")
public class UserRestController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestParam String username) {
        try {
            logger.info("In User Save Api");
            if (username != null && !username.trim().isEmpty()) {
                logger.info("User name is not empty");
                Map<String, Object> response = new HashMap();
                response.put("apiUrl", CommonUtility.getProjectProperties("hostUrl") + "/chatbot/{oprationCode}");
                response.put("description", "Refer Chatbot api url for following options : 1) Check weather condition for particular location [opration code : 1]  2) Check Top 3 news about the location [opration code : 2]  3) Check Nearby Top 5 restarant [opration code : 3]");
                logger.info("ResPonse prepared "+response);
                return new ResponseEntity<>(ResponseGenerateUtility.getSuccessResponse("Hello " + username + ", Welcome to our chatbot application", response), HttpStatus.OK);
            } else {
                logger.info("Username is empty");
                return new ResponseEntity<>(ResponseGenerateUtility.getErrorResponse("Username is required"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(ResponseGenerateUtility.getErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
