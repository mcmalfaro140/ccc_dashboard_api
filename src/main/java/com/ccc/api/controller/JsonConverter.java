package com.ccc.api.controller;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import javax.persistence.AttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter implements AttributeConverter<String, Map<String, Object>> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonConverter.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> convertToDatabaseColumn(String attribute) {
		if (attribute == null) {
			return new HashMap<String, Object>();
		}
		else {
			try {
				ObjectMapper mapper = new ObjectMapper();
				
				return mapper.readValue(attribute, HashMap.class);
			} catch (IOException ex) {
				LOGGER.error("Convert error while trying to convert string(JSON) to map data structure");
				
				throw new IllegalArgumentException("Attribute cannot be converted to a map");
			}
		}
	}
	
	@Override
	public String convertToEntityAttribute(Map<String, Object> dbData) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			return mapper.writeValueAsString(dbData);
		} catch (JsonProcessingException ex) {
			LOGGER.error("Could not convert map to json string");
			
			throw new IllegalArgumentException("Map cannot be converted to string");
		}
	}
}
