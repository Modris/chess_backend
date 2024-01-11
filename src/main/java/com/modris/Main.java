package com.modris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
/*
	@EventListener(ApplicationReadyEvent.class)
	public void afterStartUp() {
		var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
		var fenValidator = context.getBean(FenValidator.class);
		//fenValidator.isFenValid("rnbqkbnr/pppp1ppp/8/8/8/4Q3/PPPPPPPP/RNB1KBNR w KQkq - 0 1");
		fenValidator.isFenValid("rnbqkb1r/ppp1pppp/3p4/3nP3/3P4/5N2/PPP2PPP/RNBQKB1R b KQkq - 0 1");
		/*
		var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
	
		var stockfish = context.getBean(Stockfish.class);
		stockfish.start();
		System.out.println(stockfish.findBestMove("K7/2q5/k7/8/8/8/8/8 b - - 0 1"));
		
		stockfish.setStrength(100);
		System.out.println(stockfish.findBestMove("r1bqkbnr/pp1p1ppp/2p5/n3p3/2B1P3/8/PPPP1PPP/RNBQK1NR b KQkq - 0 1"));
		
		stockfish.setStrength(31590);
		System.out.println(stockfish.findBestMove("r1bqkbnr/pp1p1ppp/2p5/n3p3/2B1P3/8/PPPP1PPP/RNBQK1NR b KQkq - 0 1"));
		
		
		stockfish.close();
	*/
		// rnbqk2r/pppp1p1p/8/7N/4Pp2/2B5/PPPP2PP/RN1QKB1R w KQkq - 0 1
		
	
		
	
}
