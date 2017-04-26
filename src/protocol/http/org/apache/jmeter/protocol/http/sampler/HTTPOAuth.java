package org.apache.jmeter.protocol.http.sampler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import oauth1a.AuthorizationUtil;


public class HTTPOAuth {
	private static Logger log = LoggingManager.getLoggerForClass();
	public static final String ENC="UTF-8";
	
	
	public static HttpRequestBase httpRequest(Map<String,String> map,Map<String,Object> proper,HttpRequestBase requst){	
		if(!map.containsKey("consumekey")){
			try {
				if(requst.getLastHeader("Authorization") != null){
					requst.removeHeader(requst.getLastHeader("Authorization"));
					String oauth = appAuth2Treaty(map.get("appid"),map.get("appsecret"));
					requst.addHeader("Authorization",oauth);
				}
				
			} catch (UnsupportedEncodingException e) {
				log.error("get is oauth fail");
				e.printStackTrace();
			}
			return requst;
		}
		long timestamp = System.currentTimeMillis()/1000;
		String nonce = String.valueOf(timestamp + AuthorizationUtil.RAND.nextInt());
		float version = 1.0f;
		String signatureMethod = "HMAC-SHA1";
		String url = requst.getURI().toString();
		String verifier =null;
		String oauthToken = null;
		String oauthTokenSecret = null;
		if(map.containsKey("verifier")){
			verifier = map.get("verifier");
		}
		if(map.containsKey("oauthToken")){
			oauthToken = map.get("oauthToken");
			
		}
		if(map.containsKey("oauthTokenSecret")){
			oauthTokenSecret = map.get("oauthTokenSecret");
		}
		if(requst.getLastHeader("Authorization")!=null){
			try {
				requst.removeHeader(requst.getLastHeader("Authorization"));
				requst.addHeader("Authorization",AuthorizationUtil.generateAuthorizationHeader(map.get("consumekey"), map.get("consumerSecret"), signatureMethod, timestamp, nonce, version, oauthToken,oauthTokenSecret , verifier, url, proper, "POST"));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
		}else{
			requst.removeHeader(requst.getLastHeader("Authorization"));
		}
		
		return requst;
		
	}
	
	
	

	  public static String appAuth2Treaty(String appid, String appSecret) throws UnsupportedEncodingException {
	        String authorization = "OpenAuth2 version=\"%s\", appid=\"%s\", timestamp=%d, nonce=\"%s\", sign=\"%s\"";
	        String version = "1.1";
	        appid = URLEncoder.encode(appid, ENC);
	        long timestamp = System.currentTimeMillis();
	        String nonce = URLEncoder.encode(UUID.randomUUID().toString(), ENC);
	        String sign = shaHex(version, appid, timestamp + "", nonce, appSecret);
	        sign = URLEncoder.encode(sign, ENC);
	        authorization = String.format(authorization, version, appid, timestamp, nonce, sign);
	        return authorization;
	    }

	  public static String shaHex(String... data){
		  	Arrays.sort(data);
	        String join = StringUtils.join(data);
	        String sign = DigestUtils.shaHex(join);
	        return sign;
	  }
	
	
}
