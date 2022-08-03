package com.angela.codingdojo.util;

public enum Role {
	ADMIN("ROLE_ADMIN"), USER("ROLE_USER");
	private String name;

	private Role(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
