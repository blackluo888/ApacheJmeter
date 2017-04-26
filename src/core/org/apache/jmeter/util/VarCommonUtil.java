package org.apache.jmeter.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.jmeter.util.entity.JSONVarEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class VarCommonUtil {
	private static boolean flag = false;
	private static String resultValue;
	
	
	public static boolean isconkey(Object json,String key){
		if(json instanceof JSONObject){
			if (((JSONObject) json).containsKey(key)){
				flag = true;
			}else{
				Set<String> set = ((JSONObject) json).keySet();
				for (String skey : set){
					Object obj = ((JSONObject) json).get(skey);
					isconkey(obj, key);
				}
			}
			
		}else if(json instanceof JSONArray){
			JSONArray jsonArray = (JSONArray)json;
			for(int i=0;i<jsonArray.size();i++){
				Object obj = jsonArray.get(i);
				isconkey(obj, key);
			}
		}else if(json instanceof String){
			String jsonString = (String) json;
			if(key.equals(jsonString)){
				flag = true;
			}
		}
		 
		return flag;
	}

	
	public  static String getkeyToTValue(Object object,String premiseKey,String premisevalue,String resultKey){
		
		if(object instanceof JSONObject){
			JSONObject jsonObject = (JSONObject) object;
			if (jsonObject.containsKey(premiseKey)){
				String value = jsonObject.getString(premiseKey);
				if(premisevalue.equals(value) && jsonObject.containsKey(resultKey)){
					resultValue = jsonObject.getString(resultKey);
					
				}
			}else{
				Set<String> set = jsonObject.keySet();
				for( String key :set){
					Object obj = jsonObject.get(key);
					getkeyToTValue(obj, premiseKey, premisevalue, resultKey);
				}
				
			}
			
			
		}else if(object instanceof JSONArray){
			JSONArray jsonArray = (JSONArray) object;
			for(int i=0;i<jsonArray.size();i++){
				Object ject = jsonArray.get(i);
				getkeyToTValue(ject, premiseKey, premisevalue, resultKey);
			}
		}
		return resultValue;
	}

	public static  String getkeyToTValue(Object object,String premiseKey){
	
		if(object instanceof JSONObject){
			JSONObject jsonObject = (JSONObject) object;
		
			if (jsonObject.containsKey(premiseKey)){
				resultValue = jsonObject.getString(premiseKey);
			
			}else{
				Set<String> set = jsonObject.keySet();
				for( String key :set){
					Object obj = jsonObject.get(key);
					getkeyToTValue(obj, premiseKey);
				}
			
			}
		
		
		}else if(object instanceof JSONArray){
			JSONArray jsonArray = (JSONArray) object;
			for(int i=0;i<jsonArray.size();i++){
				Object ject = jsonArray.get(i);
				getkeyToTValue(ject, premiseKey);
			}
		}
		return resultValue;
	}


	public static JSONObject setJSONVar(String responData ,List<? extends Object> listEntity){
		JSONObject json = new JSONObject();
		JSONObject jsonString = JSON.parseObject(responData);
		List<JSONVarEntity> entityList = (List<JSONVarEntity>) listEntity;
		if (entityList !=null && !"".equals(entityList)){
			for (int i=0 ; i<entityList.size(); i++){
				JSONVarEntity entity = entityList.get(i);
				String value = getkeyToTValue(jsonString, entity.getOldProperty().toString());
				json.put(entity.getVarName().toString(),value);
			}
			
		}
		
		return json;
	}

	
}
