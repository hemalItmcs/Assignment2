/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.telegram.service;

import java.util.Map;

/**
 *
 * @author ITMCS-1
 */
public interface LocationRestaurantService {
    public Map<String,Object> getNearByRestaurants(String location)throws Exception;
}
