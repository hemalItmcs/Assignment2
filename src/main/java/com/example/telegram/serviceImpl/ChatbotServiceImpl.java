/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.telegram.serviceImpl;

import com.example.telegram.controller.UserRestController;
import com.example.telegram.service.ChatbotService;
import com.example.telegram.service.LocationLatLongService;
import com.example.telegram.service.LocationNewsService;
import com.example.telegram.service.LocationRestaurantService;
import com.example.telegram.service.LocationWeatherService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ITMCS-1
 */
@Service
public class ChatbotServiceImpl implements ChatbotService{
    
    @Autowired
    LocationWeatherService locationWeatherService;
    
    @Autowired
    LocationLatLongService locationLatLongService;
    
    @Autowired
    LocationNewsService locationNewsService;
    
    @Autowired
    LocationRestaurantService locationRestaurantService;
    
     private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Override
    public Map<String, Object> processRequest(int operation, String location) throws Exception {
        switch(operation){
            case 1 :
                logger.info("in weather");
                Map<String,Object> response = locationLatLongService.getLattitudeLongitude(location);
                if(response.containsKey("error")){
                    return response;
                }else{
                    String loc = String.valueOf(response.get("fullAddress"));
                    return locationWeatherService.getCurrentWeatherCondition(loc);
                }
            case 2 : 
                logger.info("in news");
                response = locationLatLongService.getLattitudeLongitude(location);
                if(response.containsKey("error")){
                    return response;
                }else{
                    String loc = String.valueOf(response.get("country"));
                    return locationNewsService.getLocationNews(loc);
                }
            case 3 :
                logger.info("in restaurant");
                response = locationLatLongService.getLattitudeLongitude(location);
                if(response.containsKey("error")){
                    return response;
                }else{
                    String loc = String.valueOf(response.get("Latitude"))+","+String.valueOf(response.get("Longitude"));
                    return locationRestaurantService.getNearByRestaurants(loc);
                }
            default:
                return null;
        }
    }
    
}
