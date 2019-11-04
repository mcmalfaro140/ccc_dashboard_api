package com.ccc.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class UpdateController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path = "/update")
	@PostMapping(path = "/update")
	public @ResponseBody String update() {
		return this.userRepository.findAll().toString();
	}
}
