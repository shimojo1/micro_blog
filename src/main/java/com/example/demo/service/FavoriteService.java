package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.FavoriteDao;
import com.example.demo.entity.Favorite;

@Service
public class FavoriteService implements BaseService<Favorite> {
	@Autowired
	private FavoriteDao dao;

	@Override
	public List<Favorite> findAll() {
		return dao.findAll();
	}

	@Override
	public Favorite findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	public List<Favorite> findByUserId(Integer userId) {
		return dao.findByUserId(userId);
	}

	@Override
	public void save(Favorite favorite) {
		dao.save(favorite);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}
}