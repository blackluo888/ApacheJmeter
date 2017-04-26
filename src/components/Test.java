import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.jmeter.samplers.SampleResult;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

public class Test {
	private static boolean flag = false;
	private static String resultValue = null;
	static public void main(String[] args){
		
	}

	
	
	
	
	private static boolean isconkey(Object json,String key){
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
					return resultValue;
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
	
	public static JSONObject getJSONVar(String responData ,List<Object> objList){
		JSONObject json = new JSONObject();
		JSONObject jsonString = JSON.parseObject(responData);
		if (objList !=null && !"".equals(objList)){
			for (Object key:objList){
				json.put(key.toString(), getkeyToTValue(jsonString,key.toString()));
			} 
			
		}
			
		return json;
	}
	
	
}
