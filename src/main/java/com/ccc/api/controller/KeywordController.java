package com.ccc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ccc.api.repository.KeywordRepository;

@RestController
public class KeywordController {
	@Autowired
	private KeywordRepository keywordRepo;
}
