/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.telegram.serviceImpl;

import com.example.telegram.service.LocationWeatherService;
import com.example.telegram.utility.CommonUtility;
import com.example.telegram.utility.LocationApiUtility;
import com.example.telegram.utility.XmlParserUtility;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

/**
 *
 * @author ITMCS-1
 */
@Service
public class LocationWeatherServiceImpl implements LocationWeatherService{

    @Override
    public Map<String, Object> getCurrentWeatherCondition(String location) throws Exception {
        String response = LocationApiUtility.callWeatherApi(location);
        return parseWeatherApiResponse(response);
    }
    
    private Map<String,Object> parseWeatherApiResponse(String response) throws Exception{
        XmlParserUtility utility = new XmlParserUtility(response);
        Map<String, Object> mapResponse = new HashMap();
        
        Node error = utility.getNodeByName(utility.getRootNode(), "error");
        if(error!=null){
            mapResponse.put("error",error.getTextContent());
        }else{
        Node node = utility.getNodeByName(utility.getRootNode(), "request");
        if(node!=null){
            mapResponse.put("location", utility.getNodeText(node, "query"));
        }
        
        Map<String, Object> currentWeather = new HashMap();
        node = utility.getNodeByName(utility.getRootNode(), "current_condition");
        if(node!=null){
            currentWeather.put("currentTime", utility.getNodeText(node, "observation_time"));
            if (currentWeather.get("currentTime") != null) {
                currentWeather.put("currentTime", CommonUtility.convertUTCDateToITC(currentWeather.get("currentTime").toString()));
            }
        }
        node = utility.getNodeByName(utility.getRootNode(), "current_condition");
        if(node!=null){
            currentWeather.put("celsius",utility.getNodeText(node, "temp_C"));
        }
        node = utility.getNodeByName(utility.getRootNode(), "current_condition");
        if(node!=null){
            currentWeather.put("fahrenheit",utility.getNodeText(node, "temp_F"));
        }
        node = utility.getNodeByName(utility.getRootNode(), "current_condition");
        if(node!=null){
            currentWeather.put("weatherDescription",utility.getNodeText(node, "weatherDesc"));
        }
        mapResponse.put("cuurentWeather",currentWeather);
        node = utility.getNodeByName(utility.getRootNode(), "weather");
        Map<String,Object> todayWeather = new LinkedHashMap<>();
        if(node!=null){
            Map<String,Object> map = new HashMap();
            Node n1 = utility.getNodeByName(node,"hourly");
            Node n2 = n1.getNextSibling();
            Node n3 = n2.getNextSibling();
            Node n4 = n3.getNextSibling();
            Node n5 = n4.getNextSibling();
            Node n6 = n5.getNextSibling();
            Node n7 = n6.getNextSibling();
            Node n8 = n7.getNextSibling();
            utility.getWeatherInformation(n1,map,utility);
            todayWeather.put(CommonUtility.formateTime(utility.getNodeText(n1,"time"))+"-"+CommonUtility.formateTime(utility.getNodeText(n2,"time")),map);
            
            map = new HashMap();
            utility.getWeatherInformation(n2,map,utility);
            
            todayWeather.put(CommonUtility.formateTime(utility.getNodeText(n2,"time"))+"-"+CommonUtility.formateTime(utility.getNodeText(n3,"time")),map);
            
            map = new HashMap();
            utility.getWeatherInformation(n3,map,utility);
            todayWeather.put(CommonUtility.formateTime(utility.getNodeText(n3,"time"))+"-"+CommonUtility.formateTime(utility.getNodeText(n4,"time")),map);
            
            map = new HashMap();
            utility.getWeatherInformation(n4,map,utility);
            todayWeather.put(CommonUtility.formateTime(utility.getNodeText(n4,"time"))+"-"+CommonUtility.formateTime(utility.getNodeText(n5,"time")),map);
            
            map = new HashMap();
            utility.getWeatherInformation(n5,map,utility);
            todayWeather.put(CommonUtility.formateTime(utility.getNodeText(n5,"time"))+"-"+CommonUtility.formateTime(utility.getNodeText(n6,"time")),map);
            
            map = new HashMap();
            utility.getWeatherInformation(n6,map,utility);
            todayWeather.put(CommonUtility.formateTime(utility.getNodeText(n6,"time"))+"-"+CommonUtility.formateTime(utility.getNodeText(n7,"time")),map);
            
            map = new HashMap();
            utility.getWeatherInformation(n7,map,utility);
            todayWeather.put(CommonUtility.formateTime(utility.getNodeText(n7,"time"))+"-"+CommonUtility.formateTime(utility.getNodeText(n8,"time")),map);
            
            map = new HashMap();
            utility.getWeatherInformation(n8,map,utility);
            todayWeather.put(CommonUtility.formateTime(utility.getNodeText(n8,"time"))+"-"+CommonUtility.formateTime(utility.getNodeText(n1,"time")),map);
        }
        mapResponse.put("todayWeather", todayWeather);
        }
        return mapResponse;
    }
}
