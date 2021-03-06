package com.example.restfulwebservice.user.service;

import com.example.restfulwebservice.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
	private static List<User> users = new ArrayList<>();

	private static int usersCount = 3;

	static {
		users.add(new User(1, "minji", new Date(), "pass1", "701010-1111111"));
		users.add(new User(2, "nitro", new Date(), "pass2", "801010-1111111"));
		users.add(new User(3, "ewha", new Date(), "pass3", "901010-1111111"));
	}

	// 전체 조회
	public List<User> findAll(){
		return users;
	}

	// 저장장
	public User save(User user){
		if (user.getId() == null){
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}

	// 일부 조회
	public User findOne(int id){
		for (User user : users){
			if (user.getId() == id) return user;
		}
		return null;
	}

	public User deleteById(int id){
		Iterator<User> iterator = users.iterator();

		while(iterator.hasNext()){
			User user = iterator.next();
			if (user.getId() == id){
				iterator.remove();
				return user;
			}
		}
		return null;
	}

	public User updateById(int id, User user){
		for (User storedUser : users){
			if (storedUser.getId() == id){
				storedUser.setName(user.getName());
				storedUser.setPassword(user.getPassword());

				return storedUser;
			}
		}
		return null;
	}
}
