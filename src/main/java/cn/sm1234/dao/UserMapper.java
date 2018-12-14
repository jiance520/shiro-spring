package cn.sm1234.dao;

import cn.sm1234.domain.User;

public interface UserMapper{
	public User findByName(String name);
}
