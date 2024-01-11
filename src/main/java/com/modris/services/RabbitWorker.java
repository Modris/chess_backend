package com.modris.services;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.modris.model.Stockfish;

@Service
@RabbitListener(queues = "direct_bestmove")
public class RabbitWorker {

	private Stockfish stockfish;

	public RabbitWorker(Stockfish stockfish) {
		this.stockfish = stockfish;
		this.stockfish.start();
	}

	@RabbitHandler
	//@SendTo("/websocket/client")
	public String bestmove(String fen) {
		System.out.println(fen + " Received");
		String answer = stockfish.findBestMove(fen);
		System.out.println("Sending back bestmove: "+answer);
		return stockfish.findBestMove(fen);
	}
}
