package sjz.sgy.lb.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AppFrequent {
	private String id;
	private String otherId;
	private Integer type;
	private String token;
	//最新一次的请求时间
	private Date visitTime;
	private Map<String,Object> data=new HashMap<String,Object>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	
	public Object get(String key) {
		Object obj = null;
		if(data.get(key) instanceof Object[]) {
			Object[] arr = (Object[])data.get(key);
			obj =arr;
		} else {
			obj = data.get(key);
		}
		return obj;
	}

	public String getString(String key) {
		Object value = get(key);
		if(value != null){
			return value.toString();
		}
		return null;
	}
	public Object put(String key, Object value) {
		return data.put(key, value);
	}

	public Object remove(String key) {
		return data.remove(key);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOtherId() {
		return otherId;
	}

	public void setOtherId(String otherId) {
		this.otherId = otherId;
	}

	
	
}
