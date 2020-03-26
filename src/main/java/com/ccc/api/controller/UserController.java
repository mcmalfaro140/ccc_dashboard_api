package com.ccc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ccc.api.repository.UserRepository;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepo;
}
