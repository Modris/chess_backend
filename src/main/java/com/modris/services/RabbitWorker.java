package com.modris.services;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.modris.model.ClientObject;
import com.modris.model.Stockfish;

@Service

public class RabbitWorker {
	
	private Stockfish stockfish;
	private final RabbitTemplate messageTemplate;
	//private final SimpMessageSendingOperations messageTemplate;
	private final static Logger logger = LoggerFactory.getLogger(RabbitWorker.class);
	public RabbitWorker(Stockfish stockfish, RabbitTemplate messageTemplate) {
		this.stockfish = stockfish;
		this.messageTemplate = messageTemplate;
		this.stockfish.start();
		
	}

	@RabbitListener(queues = "direct_bestmove")
	public void bestmove(ClientObject payload) {
		stockfish.setStrength(payload.getChosenElo());
		String answer = stockfish.findBestMove(payload.getFen());
		logger.info(payload.toString()+", Best move: "+answer);
		messageTemplate.convertAndSend("amq.topic","bestmove" + payload.getUserId(), answer);
	}
}
