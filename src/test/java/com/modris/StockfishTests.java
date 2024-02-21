package com.modris;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.modris.model.Stockfish;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class StockfishTests {

/*
 THESE TESTS REQUIRE A RUNNING STOCKFISH ENGINE ON YOUR MACHINE.
 */
	
	@Autowired
	private Stockfish stockfish;
	
	
	@BeforeAll
	 void beforeAll() {
		stockfish.start();
	}
	@AfterAll
	void afterAll() {
		stockfish.close();
	}
	
	@Test
	@DisplayName("4 FEN calls for the best move to the chess engine after startup.")
	public void FourCallTest() {
		stockfish.setStrength(1800);
		for(int i=0; i<4; i++) {
			stockfish.findBestMove("r1bqkbnr/pp1p1ppp/2p5/n3p3/2B1P3/8/PPPP1PPP/RNBQK1NR b KQkq - 0 1");
		}
		
	}
	@Test
	@DisplayName("20 FEN calls for the best move to the chess engine after startup.")
	public void TwentyCallTest() { // 5 seconds at the fastest
		stockfish.setStrength(1800); // total with 2 tests: 6.641s
		for(int i=0; i<20; i++) {
			stockfish.findBestMove("r1bqkbnr/pp1p1ppp/2p5/n3p3/2B1P3/8/PPPP1PPP/RNBQK1NR b KQkq - 0 1");
		}
		
	}
	/*
	@Test
	@DisplayName("100 FEN calls for the best move to the chess engine after startup.")
	public void HundredCallTest() { // 20 seconds at the fastest
		stockfish.setStrength(1800); // total with 3 tests: 31.851s
		for(int i=0; i<100; i++) {
			stockfish.findBestMove("r1bqkbnr/pp1p1ppp/2p5/n3p3/2B1P3/8/PPPP1PPP/RNBQK1NR b KQkq - 0 1");
		}
		
	}
	*/
	// Conclusion: Go bestmove search time is 250ms. The response time is about 252 ms.
	
}
