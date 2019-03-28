/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.telegram.serviceImpl;

import com.example.telegram.service.LocationLatLongService;
import com.example.telegram.utility.LocationApiUtility;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author ITMCS-1
 */
@Service
public class LocationLatLongServiceImpl implements LocationLatLongService{

    @Override
    public Map<String, Object> getLattitudeLongitude(String location) throws Exception{
        String response = LocationApiUtility.callGetLongitudeLattitude(location);
        return parseLatitudeLongitudeApiResponse(response);
    }
    
    private Map<String,Object> parseLatitudeLongitudeApiResponse(String response)throws Exception{
        JSONObject jsonObject = new JSONObject(response);
        Map<String,Object> mapResponse = new HashMap<>();
        if(jsonObject.has("status")){
            if(jsonObject.getString("status").equalsIgnoreCase("OK")){
                JSONArray results = jsonObject.getJSONArray("results");
                JSONArray addressComponent = results.getJSONObject(0).getJSONArray("address_components");
                for(int i=0 ; i<addressComponent.length() ; i++){
                    JSONObject json = addressComponent.getJSONObject(i);
                    if(json.has("types")){
                        JSONArray types =json.getJSONArray("types");
                        for(int j =0;j<types.length();j++){
                            if(types.getString(j).equalsIgnoreCase("country")){
                                mapResponse.put("country",json.get("long_name"));
                                break;
                            }
                        }

                    }
                }
                mapResponse.put("fullAddress",results.getJSONObject(0).get("formatted_address"));
                mapResponse.put("Latitude",results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat"));
                mapResponse.put("Longitude",results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng"));
            }else{
                mapResponse.put("error",  jsonObject.getString("error_message"));
            }
        }else{
            mapResponse.put("error", response);
        }
        return mapResponse;
    }
    
}
