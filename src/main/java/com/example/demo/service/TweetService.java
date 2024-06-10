package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.TweetDao;
import com.example.demo.domain.TweetInfo;
import com.example.demo.entity.Tweet;

@Service
public class TweetService implements BaseService<Tweet> {
	@Autowired
	private TweetDao dao;

	@Override
	public List<Tweet> findAll() {
		return dao.findAll();
	}

	public List<Tweet> findFollowTweet(Integer userid) {
		return dao.findFollowTweet(userid);
	}

	@Override
	public Tweet findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	public List<Tweet> findByUserId(Integer userId) {
		return dao.findByUserId(userId);
	}

	@Override
	public void insert(Tweet tweet) {
		dao.insert(tweet);
	}
	
	@Override
	public void update(Tweet tweet) {
		dao.update(tweet);
	}
	

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	public List<TweetInfo> exchangeTweetInfoList(List<Tweet> tweets, Integer userId) {
		List<TweetInfo> tweetList = new ArrayList<TweetInfo>();
		for (var tweet : tweets) {
			tweetList.add(this.exchangeTweetInfo(tweet, userId));
		}
		return tweetList;
	}

	public TweetInfo exchangeTweetInfo(Tweet tweet, Integer userId) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日HH時mm分ss秒");
		Boolean isFavorite = false;
		TweetInfo tweetInfo = new TweetInfo();
		tweetInfo.setId(tweet.getId());
		tweetInfo.setUserId(tweet.getUser().getId());
		tweetInfo.setNickname(tweet.getUser().getNickname());
		tweetInfo.setBody(tweet.getBody());
		tweetInfo.setCreatedAt(dateFormat.format(tweet.getCreatedAt()));
		for (var favorite : tweet.getFavorite()) {
			if (favorite.getUser().getId() == userId) {
				isFavorite = true;
				break;
			}
		}
		tweetInfo.setIsFavorite(isFavorite);
		return tweetInfo;
	}
}
