package com.cloudmusic.dao;

import com.cloudmusic.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

	public User getUserById(Integer id);

	public List<Map<String, Object>> getUserList();

}
