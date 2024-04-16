package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {
	@Query(value = "SELECT"
			+ " * "
			+ " FROM"
			+ " tweet as t "
			+ " WHERE "
			+ " t.user_id = :userId "
			+ " OR t.user_id in ( "
			+ " SELECT "
			+ " f.follow_user_id "
			+ " FROM "
			+ " follow as f "
			+ " WHERE "
			+ " f.user_id = :userId "
			+ " ) "
			+ "ORDER BY "
			+ " t.created_at DESC", nativeQuery = true)
	public List<Tweet> findFollowTweet(@Param("userId") Integer userId);

	public List<Tweet> findByUserIdOrderByCreatedAtDesc(Integer userId);
}