package com.modris.services;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.modris.model.ClientObject;
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
	public String bestmove(ClientObject obj) {
		stockfish.setStrength(obj.getChosenElo());
		String answer = stockfish.findBestMove(obj.getFen());
		return answer;
	}
}
