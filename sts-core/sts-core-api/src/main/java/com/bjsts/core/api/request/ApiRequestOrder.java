package com.bjsts.core.api.request;


import com.bjsts.core.enums.PageOrderType;

import java.io.Serializable;

public class ApiRequestOrder implements Serializable {
	private static final long serialVersionUID = 2434398026798733708L;

	private String field;
	private PageOrderType orderType;
	
	public ApiRequestOrder(String field, PageOrderType orderType) {
		this.field = field;
		this.orderType = orderType;
	}
	
	public String getField() {
		return field;
	}

	public PageOrderType getOrderType() {
		return orderType;
	}
}
