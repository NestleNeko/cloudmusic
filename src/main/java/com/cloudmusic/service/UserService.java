package com.cloudmusic.service;

import java.util.List;
import java.util.Map;

import com.cloudmusic.entity.User;

public interface UserService extends BaseService<User> {
	
	public User getUserById(Integer id);
	
	public List<Map<String, Object>> getUserList();

}
