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
public interface ChatbotService {
    public Map<String,Object> processRequest(int operation,String location)throws Exception;
}
