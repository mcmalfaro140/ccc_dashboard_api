package com.ccc.api.util;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;

public class GlobalVariables {
	public static final AWSCredentialsProvider AWS_CREDENTIALS = new EnvironmentVariableCredentialsProvider();
	public static final String AWS_REGION = System.getenv("AWS_REGION");
}
