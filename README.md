# Chess_Backend. A Puzzle piece for SlyChess project.

<div align="center">
  <img src="/project_images/chess_backend_highlight.png" alt="test">
</div>

* Connected to RabbitMQ Message Broker queue as a Listener.

* Calculates the best moves with Stockfish engine and sends them back to STOMP subscriber.

* Stockfish engine is directly installed on Linux in Dockerfile to solve cross architectural problems such as AMD64 not working for Linux ARM64.

* Horizontally scalable by deploying more containers (with different port mappings) because it listens to the same queue.

# Main Dependencies

* 	Spring Boot Stater AMQP. Eases the ussage of RabbitMQ in Java. 

* 	Spring Boot Starter Web. It's so SlyChess NGINX can delegate to e.g. localhost:9191. Another reason for future possibility of adding some 
REST endpoints for health, metrics and logging. 

# Stockfish engine and ProcessBuilder

The main idea in the beginning was to download Stockfish engine and leave it in the project folder. You can still see Stockfish.exe. It runs on Windows. 
However even if i downloaded Linux ARM64 Stockfish engine - it somehow became AMD86 architecture and it wouldn't run in Linux environment when deployed in my VPS Linux. So i had to
change my Dockerfile and let it download Stockfish Engine in the virtual machine and operating system that the Docker image is built in.

In Stockfish.java file: afterwards the idea was to open the Stockfish engine and execute the neccessary commands to achieve the best move output. I used Java ProcessBuilder to
achieve this. Afterwards with BufferedReader i could read what the engine responds. With OutputStreamWriter i can write the neccessary commands. It requires additional
"\n" for the command to execute though. Command sequence explanation:

* "ucinewgame\n". This is required to flush the Stockfish engine so there aren't any problems with calculating best move for a new position.

* "setoption name UCI_LimitStrength value true\n". We then enable for the option to limit Stockfish engine strength of play.

* "setoption name UCI_Elo value " + this.strength + "\n". We set the ELO value. Min = 1320. Max = 3190. The Validation is done at Chess_Manager side before the payload hits the queue.

* "position fen " + fen + "\n. Similarly the FEN syntax validation is done at Chess_Manager. Upon the unlikely scenario that we would receive a bad FEN
(currently FEN is taken directly from a Chess Board from frontend so a bad actor would have to send specifically incorrect FEN's) the program would close the ProcessBuilder, BufferedReader and OutputStreamWriter and restart Stockfish engine while returning an empty message for the bad FEN or "poison pill" message.

* "go movetime 100\n". Calculates the bestmove. I gave it 100 ms for each calculation. I played around with these time values. 1000 ms, 2000 ms or even 200, 250 ms. The outcome of the moves was nearly identical. I ended up sticking with 100 ms because the Stockfish engine is already strong as it is and some high elo blunders are actually from lackof depth and ponder option. It's a known limitation of Stockfish engine and perhaps knowing this i would have used a different chess engine for this project.

# RabbitMQ Config

In Chess_Manager i already defined an Exchange and "direct_bestmove" queue. Why am i doing it again in the Backend? In the case that the Manager is not up and running
1st then the Chess_Backend would cry about there not being a queue to Listen towards. 

RabbitMQ is also smart enough to realize how to send back the bestmove towards the STOMP subscriber. We just have to send our message to "amqp.topic" and then specify the queue 
towards which the User is subscribed to with the payload.

# End
 


