package com.psl.gems.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.psl.gems.Card;
import com.psl.gems.Player;
import com.psl.gems.card.CardGameDAO;
import com.psl.gems.card.CardGameDAOImpl;

// Runs one round of the card game per request and returns the result as JSON, same shape as TestClient's output.json
@WebServlet("/playGame")
public class CardGameServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String player1Name = request.getParameter("player1");
		String player2Name = request.getParameter("player2");
		if (player1Name == null || player1Name.isBlank()) {
			player1Name = "Sunny";
		}
		if (player2Name == null || player2Name.isBlank()) {
			player2Name = "Ankita";
		}

		CardGameDAO game = new CardGameDAOImpl();
		List<Card> cards = game.cardPack();
		game.shuffle(cards);

		Player player1 = game.register(player1Name);
		Player player2 = game.register(player2Name);

		List<List<Card>> list = game.play(cards);
		List<Card> player1Cards = list.get(0);
		List<Card> player2Cards = list.get(1);

		Card player1HighestCard = game.getHighestCard(player1Cards);
		Card player2HighestCard = game.getHighestCard(player2Cards);

		int player1HighestScore = game.getHighestScore(player1Cards);
		int player2HighestScore = game.getHighestScore(player2Cards);

		String winner = game.getWinner(player1Cards, player2Cards);
		winner = winner.equals("Player1") ? player1.getName() : player2.getName();

		int highestScore = Math.max(player1HighestScore, player2HighestScore);

		Map<String, String> mp = new HashMap<>();
		mp.put("player1name", player1.getName());
		mp.put("player2name", player2.getName());

		mp.put("p1card1", player1Cards.get(0).toString());
		mp.put("p1card2", player1Cards.get(1).toString());
		mp.put("p1card3", player1Cards.get(2).toString());

		mp.put("p2card1", player2Cards.get(0).toString());
		mp.put("p2card2", player2Cards.get(1).toString());
		mp.put("p2card3", player2Cards.get(2).toString());

		mp.put("p1highestcard", player1HighestCard.toString());
		mp.put("p2highestcard", player2HighestCard.toString());

		mp.put("p1highestscore", String.valueOf(player1HighestScore));
		mp.put("p2highestscore", String.valueOf(player2HighestScore));

		mp.put("highestscore", String.valueOf(highestScore));
		mp.put("winner", winner);

		String json = "{" + mp.entrySet().stream()
				.map(e -> "\"" + e.getKey() + "\":\"" + e.getValue() + "\"")
				.collect(Collectors.joining(", ")) + "}";

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try (PrintWriter out = response.getWriter()) {
			out.write(json);
		}
	}
}
