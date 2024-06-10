package com.example.demo.dao;

import java.util.List;

import com.example.demo.dto.FavoriteDto;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import com.example.demo.mapper.FavoriteMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Favorite;
import com.example.demo.repository.FavoriteRepository;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Repository
public class FavoriteDao implements BaseDao<Favorite> {
//	@Autowired
//	FavoriteRepository repository;
	
	@Autowired
	FavoriteMapper favoriteMapper;

	@Override
	public List<Favorite> findAll() {
		return favoriteMapper.findAll();
	}

	@Override
	public Favorite findById(Integer id) throws DataNotFoundException {
		return favoriteMapper.findById(id);
//		return this.repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}
	@Override
	public void insert(Favorite favorite) {
		System.out.println("お気に入りのDao");
		
		System.out.println(favorite.getModifiedAt());
		favoriteMapper.insert(
				favorite.getUser().getId(),
				favorite.getTweet().getId()
		);
	}
	@Override
	public void update(Favorite favorite) {
		favoriteMapper.update(favorite);
	}
	
	public List<Favorite> findByUserId(Integer userId) {
		return favoriteMapper.findByUserId(userId).stream().map(it -> {
			Favorite favorite = new Favorite();
			favorite.setId(it.getId());
			favorite.setUser(it.getUser());
			favorite.setTweet(it.getTweet());
			System.out.println("favorite: " + ToStringBuilder.reflectionToString(favorite, MULTI_LINE_STYLE));
			System.out.println("tweet: " + ToStringBuilder.reflectionToString(favorite.getTweet(), MULTI_LINE_STYLE));
			
			return favorite;
		}
		).toList();
	}

	@Override
	public void deleteById(Integer id) {
		try {
			Favorite favorite = this.findById(id);
			favoriteMapper.deleteById(favorite.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}
}
