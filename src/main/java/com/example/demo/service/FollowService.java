package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.BaseDao;
import com.example.demo.entity.Follow;

@Service
public class FollowService implements BaseService<Follow> {
	@Autowired
	private BaseDao<Follow> dao;

	@Override
	public List<Follow> findAll() {
		return dao.findAll();
	}

	@Override
	public Follow findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void save(Follow follow) {
		dao.save(follow);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}
}