# LinesOfAction

Lines of Action (or LOA) is an abstract strategy board game for two players. You can read the rules [here](http://www.boardspace.net/loa/english/index.html#howto-play).

This project was developed as part of academic course work in CSE318: Artificial Intelligence Sessional. You can read the full description of the specification [here](https://docs.google.com/document/d/1rnYGn6uUWajSWruG9aTIiEOKyS6L6UdyzBSqtt8LSho/edit).

You can play in multiplayer mode or single player mode or even watch two AI playing!

The AI uses iterative deepening search on minimax algorithm with alpha-beta pruning to make decisions. When the minimax algorithm reaches a leaf where the game has not already ended, it has to evaluate how "favorable" current state is for the AI agent. The measurement of favor, otherwise known as evaluation heuritics, is returned from the leaf. The [evaluation heuristics](https://github.com/solaimanope/LinesOfAction/blob/master/evaluation_function.txt) used in this project were given to us as part of specification.

<img src="https://i.imgur.com/4SvBa3D.gif" height="500">
