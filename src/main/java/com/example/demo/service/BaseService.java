package com.example.demo.service;

import java.util.List;

import com.example.demo.common.DataNotFoundException;

public interface BaseService<T> {
	public List<T> findAll();

	public T findById(Integer id) throws DataNotFoundException;

	public void insert(T t);

	public void update(T t);

	public void deleteById(Integer id);
}
