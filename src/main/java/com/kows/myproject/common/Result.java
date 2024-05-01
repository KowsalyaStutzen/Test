package com.kows.myproject.common;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
private Map<String, Object> data;
	private boolean success;
	private String message = "";
	private Integer total;
	private Integer page;

	public Map<String, Object> getData()
	{
		return this.data;
	}

	public void addData(String name, Object obj) {
		if (this.data == null) this.data = new HashMap<String, Object>();
		this.data.put(name, obj);
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPage() {
		return this.page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String toString()
	{
		JSONObject result = new JSONObject();
		try {
			result.put("success", this.success);
			result.put("message", this.message);
			if (this.data != null) {
				result.put("data", this.data);
			}
			if (this.total != null) {
				result.put("total", this.total);
			}
			if (this.page != null)
				result.put("page", this.page);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		return result.toString();
	}
}
