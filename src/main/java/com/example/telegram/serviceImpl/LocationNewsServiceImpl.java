/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.telegram.serviceImpl;

import com.example.telegram.service.LocationNewsService;
import com.example.telegram.utility.LocationApiUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author ITMCS-1
 */
@Service
public class LocationNewsServiceImpl implements LocationNewsService {

    @Override
    public Map<String, Object> getLocationNews(String location) throws Exception {
        String response = LocationApiUtility.callNewsApi(location);
        return parseLocationNewsApiResponse(response);
    }

    private Map<String, Object> parseLocationNewsApiResponse(String response) {
        Map<String, Object> mapResponse = new HashMap<>();
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.has("status")) {
            if (jsonObject.getString("status").equalsIgnoreCase("OK")) {
                JSONArray jsonarray = jsonObject.getJSONArray("articles");
                List<Map<String, Object>> lstnews = new ArrayList();
                if (jsonarray.length() >= 3) {
                    for (int i = 0; i < 3; i++) {
                        lstnews.add(jsonarray.getJSONObject(i).toMap());
                    }
                } else {
                    for (int i = 0; i < jsonarray.length(); i++) {
                        lstnews.add(jsonarray.getJSONObject(i).toMap());
                    }
                }
                mapResponse.put("news", lstnews);

            } else {
                mapResponse.put("error", "Invalid Location, No Results Found");
            }
        } else {
            mapResponse.put("error", response);
        }
        return mapResponse;
    }
}
