package com.ccc.api.controller;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.slf4j.MDC;


@RestController
public class HomeController {

//    Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/")
    public String hello() {
  /*
    	MDC.put("testKey", "testValue");
        logger.error("An error message");
        logger.warn("A warning message");
        logger.info("An information message");
        logger.info("Try this");
        logger.debug("A debugging message");
        logger.trace("A message for more detailed debugging");
        MDC.remove("testKey");
        */
        return "Hello World in Spring Boot";
    }
}
