package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class TweetInfo {
	private Integer id;

	private Integer userId;

	private String nickname;

	private String body;

	private Boolean isFavorite;

	private String createdAt;
}
