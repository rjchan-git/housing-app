package com.ravi.housing.domain;

public enum UserType {
	
	OWNER("owner"),TENANT("tenant");

	private final String user;
	
	 UserType(String user) {
		 this.user = user;
	 }
}
