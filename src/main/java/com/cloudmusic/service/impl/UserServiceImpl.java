package com.cloudmusic.service.impl;

import com.cloudmusic.dao.UserMapper;
import com.cloudmusic.entity.User;
import com.cloudmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserMapper usermapper;

	public void add(User e) {
	}

	public List<Map<String, Object>> getUserList() {
		return usermapper.getUserList();
	}

	public User getUserById(Integer id) {
		return usermapper.getUserById(id);
	}

}
