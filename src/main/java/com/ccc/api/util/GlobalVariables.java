package com.ccc.api.util;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;

// this uses global variables
public class GlobalVariables {
	public static final AWSCredentialsProvider AWS_CREDENTIALS = new EnvironmentVariableCredentialsProvider();
	public static final String AWS_REGION = System.getenv("AWS_REGION");
}

//use this for deploy
// replace values of BasicAWSCredentials with your AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY 

//public class GlobalVariables {
//	public static final AWSCredentials my_keys = new BasicAWSCredentials("ASDSASHDGHGA6","CJBbhFhasguhasdhjHDGDBjsdhdasd/");
//	
//	public static final AWSCredentialsProvider AWS_CREDENTIALS = new AWSStaticCredentialsProvider(my_keys);
//	public static final String AWS_REGION = "us-west-1";
//}
