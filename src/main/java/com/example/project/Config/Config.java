package com.example.project.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class Config {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
