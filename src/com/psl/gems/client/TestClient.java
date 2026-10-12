package com.psl.gems.client;

import com.psl.gems.card.CardGameDAOImpl;
import com.psl.gems.Card;
import com.psl.gems.Player;
import com.psl.gems.card.CardGameDAO;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.io.IOException;

public class TestClient {

	public static void main(String[] args) {
		String[] names = { "Sunny", "Meeku", "Ankita", "Deepshikha", "Ramesh", "Suresh", "Ankit" };
		String player1Name = names[0];
		String player2Name = names[2];

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

		if (winner.equals("Player1")) {
			winner = player1.getName();
		} else {
			winner = player2.getName();
		}

		int highestScore = 0;
		if (player1HighestScore > player2HighestScore) {
			highestScore = player1HighestScore;
		} else {
			highestScore = player2HighestScore;
		}

		System.out.println("Player-1 Cards are -: ");
		System.out.println(player1Cards);
		System.out.println("-----------------------");
		System.out.println("Player-2 Cards are -: ");
		System.out.println(player2Cards);
		System.out.println("------------------------");
		System.out.println("Player-1 Highest Card is -: ");
		System.out.println(player1HighestCard);
		System.out.println("-------------------------");
		System.out.println("Player-2 Highest Card is -: ");
		System.out.println(player2HighestCard);
		System.out.println("--------------------------");
		System.out.println("Player-1 Highest Score is -: ");
		System.out.println(player1HighestScore);
		System.out.println("-------------------------");
		System.out.println("Player-2 Highest Score is -: ");
		System.out.println(player2HighestScore);
		System.out.println("--------------------------");
		System.out.println("Winner is -: " + winner + " with Score -: " + highestScore);
		System.out.println("---------------------------------");

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
				.map(e -> "\"" + e.getKey() + "\":\"" + String.valueOf(e.getValue()) + "\"")
				.collect(Collectors.joining(", ")) + "}";
		try {
			 FileWriter file = new FileWriter("./pages/output.json");
			file.write(json);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
