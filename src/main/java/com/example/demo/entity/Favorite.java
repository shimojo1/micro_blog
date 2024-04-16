package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "favorite")
public class Favorite {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotNull(message = "ユーザIDは必須入力です")
	private User user;

	@ManyToOne
	@JoinColumn(name = "tweet_id")
	@NotNull(message = "ツイートIDは必須入力です")
	private Tweet tweet;

	@Column
	private Date modifiedAt;

	@PrePersist
	public void onPrePersist() {
		setModifiedAt(new Date());
	}

	@PreUpdate
	public void onPreUpdate() {
		setModifiedAt(new Date());
	}

}
