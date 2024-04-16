package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Follow;

import jakarta.transaction.Transactional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
	@Transactional
	public void deleteByUserIdAndFollowUserId(Integer userId, Integer followUserId);
}