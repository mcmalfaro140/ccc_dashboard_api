package com.ccc.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class AddController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(name = "/update/add")
	@PostMapping(name = "/update/add")
	public @ResponseBody String add() {
		return "2";
	}
}
