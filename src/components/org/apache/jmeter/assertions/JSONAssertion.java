package org.apache.jmeter.assertions;

import java.io.Serializable;

import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.ThreadListener;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import com.google.gson.Gson;

public class JSONAssertion extends AbstractTestElement implements Serializable,Assertion,ThreadListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 240L;
	private static final Logger LOG = LoggingManager.getLoggerForClass();
	
	
	
	@Override
	public void threadStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void threadFinished() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AssertionResult getResult(SampleResult response) {
		AssertionResult result = new AssertionResult(getName());
		String resultData = response.getResponseDataAsString();
		if(resultData.length()==0){
			return result.setResultForNull();
		}else{
			Gson gson =new Gson();
			if(!gson.toJsonTree(resultData).isJsonObject()){
				LOG.debug("Cannot parse result content");
				result.setFailure(true);
				result.setFailureMessage("ResultData is not Json");
			}
		}
		return result;
	}

}
