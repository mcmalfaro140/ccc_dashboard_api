package com.ccc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ccc.api.repository.SNSTopicRepository;

@RestController
public class SNSTopicController {
	@Autowired
	private SNSTopicRepository snsTopicRepo;
}
