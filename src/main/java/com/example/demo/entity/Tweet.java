package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.common.ValidationGroups.Update;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "tweet")
public class Tweet {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	@NotEmpty(groups = { Create.class, Update.class }, message = "つぶやきが未入力です")
	private String body;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotNull(message = "ユーザIDは必須入力です")
	private User user;

	@OneToMany(mappedBy = "tweet", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	List<Favorite> favorite;

	@Column(updatable = false)
	private Date createdAt;

	@PrePersist
	public void onPrePersist() {
		setCreatedAt(new Date());
	}
}
