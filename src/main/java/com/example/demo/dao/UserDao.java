package com.example.demo.dao;

import java.util.List;

import com.example.demo.dto.UserDto;
import com.example.demo.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Repository
public class UserDao implements BaseDao<User> {
	@Autowired
	UserRepository repository;
	
	@Autowired
	UsersMapper usersMapper;

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User findById(Integer id)  {
		UserDto user = usersMapper.findById(id);
		User userObj = new User();
		userObj.setId(user.getId());
		userObj.setMail(user.getMail());
		userObj.setNickname(user.getNickname());
		
		return userObj;
	}

	public List<User> findByIdNot(Integer id) {
		return this.repository.findByIdNot(id);
	}

	@Override
	public void insert(User user) {
		this.repository.save(user);
	}

	@Override
	public void update(User user) {
		this.repository.save(user);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			User user = this.findById(id);
			usersMapper.deleteById(user.getId());
		} catch (Exception e) {
			System.out.println("userDao_deleteById_error");
			throw e;
		}
	}

	public User findByEmail(String email) throws DataNotFoundException {
		User user = this.repository.findByMail(email);
		if (user == null) {
			throw new DataNotFoundException();
		}
		return user;
	}
}
