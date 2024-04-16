package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Follow;
import com.example.demo.repository.FollowRepository;

@Repository
public class FollowDao implements BaseDao<Follow> {
	@Autowired
	FollowRepository repository;

	@Override
	public List<Follow> findAll() {
		return repository.findAll();
	}

	@Override
	public Follow findById(Integer id) throws DataNotFoundException {
		return this.repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(Follow follow) {
		this.repository.save(follow);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			Follow follow = this.findById(id);
			this.repository.deleteById(follow.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}
}
