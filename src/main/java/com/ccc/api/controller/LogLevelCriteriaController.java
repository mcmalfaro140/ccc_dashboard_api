package com.ccc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ccc.api.repository.LogLevelCriteriaRepository;

@RestController
public class LogLevelCriteriaController {
	@Autowired
	private LogLevelCriteriaRepository logLevelCriteriaRepo;
}
