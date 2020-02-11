package sjz.sgy.lb.interceptor;

import java.util.Date;
import java.util.HashMap;

import sjz.sgy.lb.util.jwt.LoginConstant;

public class AppFrequentContext {
	 private static AppFrequentContext instance;  
	 private HashMap<String,AppFrequent> appFrequentMap; 
	 
	 private AppFrequentContext() {  
		 appFrequentMap = new HashMap<String,AppFrequent>();  
	    }  

	    public static AppFrequentContext getInstance() {  
	        if (instance == null) {  
	            instance = new AppFrequentContext();  
	        }  
	        return instance;  
	    }  

	    public synchronized void addAppFrequent(AppFrequent appFrequent) {  
	        if (appFrequent != null) {  
	        	appFrequentMap.put(appFrequent.getId(), appFrequent);  
	        }  
	    }  

	    public synchronized void delAppFrequent(AppFrequent appFrequent) {  
	        if (appFrequent != null) {  
	        	appFrequentMap.remove(appFrequent.getId());  
	        }  
	    }  

	    public synchronized AppFrequent getAppFrequent(String appFrequentId,String type) {  
	        if (appFrequentId == null) {  
	            return null;  
	        }  
	        AppFrequent appFrequent=appFrequentMap.get(appFrequentId);
	        if(appFrequent==null) {
	        	return null;
	        }
	        //判断是什么token 1：accesstoken，2：refreshToken
	        long visitTime=appFrequent.getVisitTime().getTime();;
	        boolean flag=false;
	        if(("1").equals(type)){
	        	//accessToken是否过期 
	        	//System.err.println("1        "+(new Date().getTime()-visitTime)/1000);
		        flag=((System.currentTimeMillis()-visitTime)/1000)<LoginConstant.ACCESS_TOKEN_TIME?true:false;
	        }else{
	        	//System.err.println("2        "+(new Date().getTime()-visitTime)/1000);
	        	flag=((System.currentTimeMillis()-visitTime)/1000)<LoginConstant.REFRESH_TOKEN_TIME?true:false;
	        }
	        if(flag) {
	        	appFrequent.setVisitTime(new Date());
	        	//更新refreshToken时间
	        	AppFrequent other=appFrequentMap.get(appFrequent.getOtherId());
	        	if(other!=null){
	        		other.setVisitTime(new Date());
	        	}
	        }else {
	        	delAppFrequent(appFrequent);
	        	
	        	appFrequent=null;
	        }
	        return appFrequent;  
	    }
	  
	    public synchronized boolean judgeToken(String token,AppFrequent appFrequent) {  
	    	if(token==null ||appFrequent==null) {
	    		return false;
	    	}
	        return true;
	    }
		public HashMap<String, AppFrequent> getAppFrequentMap() {
			return appFrequentMap;
		}  
	 
	 
	 
	 
}
