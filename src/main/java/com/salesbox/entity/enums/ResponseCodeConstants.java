package com.salesbox.entity.enums;

public enum ResponseCodeConstants
{
	SUCCESS(200, "Success"),
	
	CAN_NOT_GET_ANY_EMAIL(-3001, "CAN_NOT_GET_ANY_EMAIL"),
	CAN_NOT_GET_ANY_ACCOUNT(-3002, "CAN_NOT_GET_ANY_ACCOUNT"),
	CAN_NOT_GET_ANY_LEAD(-3003, "CAN_NOT_GET_ANY_LEAD"),
	INVALID_INPUT(-3004, "INVALID_INPUT"),
	USER_NOT_FOUND(-3005, "USER_NOT_FOUND");
	
	private Integer value;
	private String display;
	
	ResponseCodeConstants(Integer value, String display){
		this.value = value;
		this.display = display;
	}
	
	public Integer getValue(){
		return value;
	}
	
	public String getDisplay(){
		return display;
	}
}
