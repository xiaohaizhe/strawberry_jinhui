package com.jh.strawberry.model;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jh.strawberry.utils.Constants;

/**
 * @author pyt
 * @createTime 2018年12月7日上午10:19:10
 */
public enum RESCODE {
	SUCCESS(0, "成功"),
	FAILURE(1, "失败");
	
	private int code;
	private String msg;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private RESCODE(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	/**
	 * 最新的返回json
	 */
	public JSONObject getJSONRES(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Constants.RESPONSE_CODE_KEY, this.code);
		jsonObject.put(Constants.RESPONSE_MSG_KEY, this.msg);
		return jsonObject;
	}
	
	public JSONObject getJSONRES(Object entity){
		JSONObject jsonres = getJSONRES();
		jsonres.put(Constants.RESPONSE_DATA_KEY, entity);
		return jsonres;
	}
	
	public JSONObject getJSONRES(Object entity,int pages,long count){
		JSONObject jsonres = getJSONRES();
		jsonres.put(Constants.RESPONSE_DATA_KEY, entity);
		jsonres.put(Constants.RESPONSE_SIZE_KEY, pages);
		jsonres.put(Constants.RESPONSE_REAL_SIZE_KEY, count);
		return jsonres;
	}
	
	

}

