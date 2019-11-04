package com.ccc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModifyController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(name = "/update/add")
	@PostMapping(name = "/update/add")
	public @ResponseBody String modify() {
		return "4";
	}
}
