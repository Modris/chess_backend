package com.modris.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modris.model.Stockfish;

@RestController
public class MainController {

	private final Stockfish stockfish;
	
	public MainController(Stockfish stockfish) {

		this.stockfish = stockfish;
		this.stockfish.start();
	}
	
	@GetMapping("/")
	public String findBestMove(	@RequestParam String fen) {
		// sanitize and check fen?
		//return engineLocation;
		return stockfish.findBestMove(fen);
	}

	
}
