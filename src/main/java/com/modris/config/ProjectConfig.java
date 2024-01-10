package com.modris.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.modris.model.FenValidator;
import com.modris.model.Stockfish;

@Configuration
public class ProjectConfig {

	@Value("${stockfish}")
	private String engineLocation;

	@Bean
	public Stockfish stockfish() {
		return new Stockfish(engineLocation);
	}
	
	@Bean
	public FenValidator fenValidator() {
		return new FenValidator();
	}
}
