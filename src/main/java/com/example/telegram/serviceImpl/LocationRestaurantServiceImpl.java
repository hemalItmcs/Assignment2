/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.telegram.serviceImpl;

import com.example.telegram.controller.UserRestController;
import com.example.telegram.service.LocationRestaurantService;
import com.example.telegram.utility.LocationApiUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author ITMCS-1
 */
@Service
public class LocationRestaurantServiceImpl implements LocationRestaurantService{

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
    
    @Override
    public Map<String, Object> getNearByRestaurants(String location) throws Exception {
        String response = LocationApiUtility.callRestaurantApi(location);
        logger.info("response::"+response);
        return parseRestaurantApiResponse(response);
    }
    
    private Map<String,Object> parseRestaurantApiResponse(String response)throws Exception{
        Map<String,Object> mapResponse = new HashMap<>();
        JSONObject jsonObject = new JSONObject(response);
        if(jsonObject.has("status")){
            if(jsonObject.getString("status").equalsIgnoreCase("OK")){
                JSONArray results = jsonObject.getJSONArray("results");
                if(results.length()>=5){
                    List<Map<String,Object>> lstRestaurant = new ArrayList<>();
                    for(int i = 0 ; i < 5 ; i++){
                        Map<String,Object> restaurant = new HashMap();
                        restaurant.put("restaurantName",results.getJSONObject(i).getString("name"));
                        restaurant.put("opening_hours",results.getJSONObject(i).has("opening_hours") ? results.getJSONObject(i).getJSONObject("opening_hours").toMap() : "-");
                        if(results.getJSONObject(i).has("photos")){
                            JSONArray imgUrl  = results.getJSONObject(i).getJSONArray("photos").getJSONObject(0).getJSONArray("html_attributions");
                            restaurant.put("photosUrl",imgUrl.getString(0).split("\"")[1]);
                        }
                        restaurant.put("ratings",results.getJSONObject(i).has("rating") ? results.getJSONObject(i).getFloat("rating") : "-");
                        restaurant.put("address",results.getJSONObject(i).has("vicinity") ? results.getJSONObject(i).getString("vicinity") : "-");
                        lstRestaurant.add(restaurant);
                    }
                    mapResponse.put("restarants", lstRestaurant);
                }else{
                    List<Map<String,Object>> lstRestaurant = new ArrayList<>();
                    for(int i = 0 ; i < results.length() ; i++){
                        Map<String,Object> restaurant = new HashMap();
                        restaurant.put("restaurantName",results.getJSONObject(i).getString("name"));
                        restaurant.put("opening_hours",results.getJSONObject(i).has("opening_hours") ? results.getJSONObject(i).getJSONObject("opening_hours").toMap() : "-");
                        if(results.getJSONObject(i).has("photos")){
                            JSONArray imgUrl = results.getJSONObject(i).getJSONArray("photos").getJSONObject(0).getJSONArray("html_attributions");
                            restaurant.put("photosUrl",imgUrl.getString(0).split("\"")[1]);
                        }
                        restaurant.put("ratings",results.getJSONObject(i).has("rating") ? results.getJSONObject(i).get("rating") : "-");
                        restaurant.put("address",results.getJSONObject(i).has("vicinity") ? results.getJSONObject(i).get("vicinity") : "-");
                        lstRestaurant.add(restaurant);
                    }
                    mapResponse.put("restarants", lstRestaurant);
                }
            }else{
                mapResponse.put("error", jsonObject.getString("error_message"));
            }
        }else{
            mapResponse.put("error", response);
        }
        return mapResponse;
    }
}
