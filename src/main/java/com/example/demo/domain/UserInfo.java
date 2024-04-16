package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UserInfo {
	private Integer id;

	private String nickname;

	private Integer followCount;

	private Integer followerCount;

	private Boolean isFollow;
}
