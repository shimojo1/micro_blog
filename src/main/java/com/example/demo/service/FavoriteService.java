package com.example.demo.service;

import java.util.Collections;
import java.util.List;

import com.example.demo.dao.TweetDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.FavoriteDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.FavoriteDao;
import com.example.demo.entity.Favorite;

@Service
public class FavoriteService implements BaseService<Favorite> {
	@Autowired
	private FavoriteDao favoriteDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TweetDao tweetDao;

	@Override
	public List<Favorite> findAll() {
		return favoriteDao.findAll();
	}

	@Override
	public Favorite findById(Integer id) throws DataNotFoundException {
		return favoriteDao.findById(id);
	}

	public List<Favorite> findByUserId(Integer userId) {

        return favoriteDao.findByUserId(userId);
	}

	@Override
	public void insert(Favorite favorite) {
		System.out.println("お気に入りのService");
		favoriteDao.insert(favorite);
	}

	@Override
	public void update(Favorite favorite) {
		favoriteDao.update(favorite);
	}

	@Override
	public void deleteById(Integer id) {
		favoriteDao.deleteById(id);
	}
}
