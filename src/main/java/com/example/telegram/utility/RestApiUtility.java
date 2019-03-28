package com.example.telegram.utility;

import com.example.telegram.controller.UserRestController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestApiUtility {
    URL url;
    HttpURLConnection urlConnection;
    String requestMethod;
    HttpServletRequest httpServletReq;
    
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
    
    public RestApiUtility(){
    }
    
    public RestApiUtility(String apiurl, String requestMethod,Object... urlParams)throws Exception {
        apiurl=apiurl+"?";
    	for(int i=0; i< urlParams.length;){            
    		apiurl=apiurl+String.valueOf(urlParams[i])+"="+String.valueOf(urlParams[i+1])+"&";
            i += 2;
        } 
    	apiurl=apiurl.substring(0, apiurl.length()-1);
        logger.info("apiurl::"+apiurl);
        url = new URL(apiurl);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(requestMethod);
    }
    
    void setHeaderParameters(Map<String, String> headers) {
        Set<String> keys = headers.keySet();
        for (String tmp : keys) {
            urlConnection.setRequestProperty(tmp, headers.get(tmp));
        }
    }
    
    void setBodyParameters(Map<String, Object> bodyParam) throws Exception{
        Set<String> keys = bodyParam.keySet();
        StringBuilder body = new StringBuilder();
        for (String tmp : keys) {
            String s = tmp + "=" + bodyParam.get(tmp) + "&";
            body.append(s);
        }
        urlConnection.setDoOutput(true);
        urlConnection.getOutputStream().write(body.toString().getBytes());
    }
    
    public void setApiheaders(Object... object) {
        Map<String, String> headers = new HashMap();                    
        for(int i=0; i< object.length;){  
            headers.put(String.valueOf(object[i]), String.valueOf(object[i+1]));
            i+=2;
        }
        setHeaderParameters(headers);
    }
     
    public void setApiBody(Object... object) throws Exception {
        Map<String, Object> body = new HashMap();        
        for(int i=0; i< object.length;){            
            body.put(String.valueOf(object[i]), String.valueOf(object[i+1]));
            i += 2;
        } 
        setBodyParameters(body);
    }
    
    public void setBodyPayload(String body)throws Exception{
    	urlConnection.setDoOutput(true);
        urlConnection.getOutputStream().write(body.getBytes("UTF-8"));
    }
    
    public String Call()throws Exception {
        StringBuilder response = new StringBuilder();
        Reader in = null;
        int responseCode = urlConnection.getResponseCode();
        logger.info("responseCode::"+responseCode);
        if(responseCode == 200){
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
            for(int i;(i = in.read())>=0;){
                response.append((char) i);
            }
            in.close();
            urlConnection.disconnect();
            logger.info("response::"+response.toString());
            return response.toString();
        }else{
            in = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(),"UTF-8"));
            for(int i;(i = in.read())>=0;){
                response.append((char) i);
            }
            in.close();
            urlConnection.disconnect();
            logger.info("response::"+response.toString());
            return response.toString();
        }  
    }
      
}
