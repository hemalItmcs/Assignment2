package com.example.telegram.service;

import java.util.Map;

public interface LocationWeatherService {
    public Map<String,Object> getCurrentWeatherCondition(String location)throws Exception;
}
