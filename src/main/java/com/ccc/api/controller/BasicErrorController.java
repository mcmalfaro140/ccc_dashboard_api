package com.ccc.api.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
//@RequestMapping("${server.error.path:${error.path:/error}}")
//public class BasicErrorController extends AbstractErrorController {
//
//	//...
//
//	@RequestMapping
//	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
//		Map<String, Object> body = getErrorAttributes(request,
//				isIncludeStackTrace(request, MediaType.ALL));
//		HttpStatus status = getStatus(request);
//		return new ResponseEntity<>(body, status);
//	}
//}