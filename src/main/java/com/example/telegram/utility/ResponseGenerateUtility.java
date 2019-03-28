package com.example.telegram.utility;

import java.util.HashMap;
import java.util.Map;

public class ResponseGenerateUtility {

	public static Map<String,Object> getSuccessResponse(String successmessage,Map<String,Object> response){
		Map<String,Object> successRes = new HashMap();
		successRes.put("isSuccess", 1);
		successRes.put("successMessage", successmessage);
                if(response!=null) {
                    successRes.put("success",response);
		}
                return successRes;
	}
	
	
	public static Map<String,Object> getErrorResponse(String errorMessage){
		Map<String,Object> errorRes = new HashMap();
		errorRes.put("isSuccess", 0);
		errorRes.put("errorMessage", errorMessage);
		return errorRes;
	}
}
