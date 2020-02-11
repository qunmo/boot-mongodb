package sjz.sgy.lb.entity.tbStatic;

import sjz.sgy.lb.util.Page;

public class TbStatic extends Page {
	private Integer staticId;
	private String code;
	private String staticKey;
	private String staticValue;
	public String getStaticValue() {
		return staticValue;
	}
	public void setStaticValue(String staticValue) {
		this.staticValue = staticValue;
	}
	private String description;
	private String status;
	private int sort;
	public Integer getStaticId() {
		return staticId;
	}
	public void setStaticId(Integer staticId) {
		this.staticId = staticId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStaticKey() {
		return staticKey;
	}
	public void setStaticKey(String staticKey) {
		this.staticKey = staticKey;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
	
	
}
