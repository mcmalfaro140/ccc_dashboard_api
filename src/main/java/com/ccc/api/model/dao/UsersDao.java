package com.ccc.api.model.dao;

import java.util.List;

import com.ccc.api.model.Users;

public interface UsersDao {
	Users getUsers(Integer userId);

    List<Users> getUsers();

    Users saveUsers(Users user);
}
