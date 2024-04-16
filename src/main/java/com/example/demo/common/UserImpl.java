package com.example.demo.common;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserImpl extends User {
	private final com.example.demo.entity.User user;

	public UserImpl(String username, String password, com.example.demo.entity.User user) {
		super(username, password, true, true, true, true, AuthorityUtils.createAuthorityList("ADMIN"));
		this.user = user;
	}

	public com.example.demo.entity.User getUser() {
		return this.user;
	}
}