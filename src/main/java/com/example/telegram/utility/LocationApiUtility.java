package com.example.telegram.utility;

public class LocationApiUtility {

    public static String callWeatherApi(String location) throws Exception {
        RestApiUtility resApiUtility = new RestApiUtility(CommonUtility.getProjectProperties("weatherApi"), "GET", "q", location.replaceAll(" ","+"), "num_of_days", 1, "key", CommonUtility.getProjectProperties("weatherApiAppKey"));
        return resApiUtility.Call();
    }
    
    public static String callGetLongitudeLattitude(String location)throws Exception{
        RestApiUtility resApiUtility = new RestApiUtility(CommonUtility.getProjectProperties("longitudeApi"), "GET", "address", location.replaceAll(" ","+"), "sensor", "true", "key", CommonUtility.getProjectProperties("longitudeApiAppKey"));
        return resApiUtility.Call();
    }
    
    public static String callRestaurantApi(String location)throws Exception{
        RestApiUtility resApiUtility = new RestApiUtility(CommonUtility.getProjectProperties("RestaurantApi"), "GET", "location", location.replaceAll(" ","+"),"type", "restaurant", "key", CommonUtility.getProjectProperties("RestaurantApiKey"),"radius",5000);
        return resApiUtility.Call();
    }
    
    public static String callNewsApi(String location)throws Exception{
        RestApiUtility resApiUtility = new RestApiUtility(CommonUtility.getProjectProperties("newsApi"), "GET", "q", location.replaceAll(" ","+"), "apiKey", CommonUtility.getProjectProperties("newsApiKey"));
        return resApiUtility.Call();
    }

}
