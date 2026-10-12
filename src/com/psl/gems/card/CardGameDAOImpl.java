package com.psl.gems.card;

import java.util.*;

import com.psl.gems.Card;
import com.psl.gems.Player;
import com.psl.gems.Rank;
import com.psl.gems.Suit;

public class CardGameDAOImpl implements CardGameDAO{

	@Override
	public List<Card> cardPack() {
		List<Card> cards = new LinkedList<>();
		for(Suit suit:Suit.values()) {
			for(Rank rank:Rank.values()) {
				Card c = new Card(suit,rank);
				cards.add(c);
			}
		}
		return cards;
	}

	@Override
	public Player register(String name) {
		Player p = new Player(name);
		return p;
	}

	@Override
	public List<List<Card>> play(List<Card> list) {
		List<List<Card>> cards = new LinkedList<>();
		List<Card>p1Cards = new LinkedList<>();
		List<Card>p2Cards = new LinkedList<>();
		for(int i=0;i<3;i++) {
			int max=list.size()-1;
			int min=0;
			int range = max-min+1;
			int rand = (int)(Math.random()*range)+min;
			Card c1 = list.get(rand);
			p1Cards.add(c1);
			list.remove(rand);
			max=list.size()-1;
			min=0;
			range = max-min+1;
			rand = (int)(Math.random()*range)+min;
			Card c2 = list.get(rand);
			p2Cards.add(c2);
			list.remove(rand);
			
		}
		cards.add(p1Cards);
		cards.add(p2Cards);
		return cards;
	}

	@Override
	public String getWinner(List<Card> l1, List<Card> l2) {
		int sum1 = calculateSum(l1);
		int sum2 = calculateSum(l2);
		if(sum1>sum2) {
			return "Player1";
		}else {
			return "Player2";
		}
	}
	
	@Override
	public Card getHighestCard(List<Card> list) {
		int maxSum=0;
		Card maxCard = list.get(0);
		for(Card c:list) {
			int sum=0;
			sum = (c.getRank().getWeight()*4)+c.getSuit().getWeight();
			if(sum>maxSum) {
				maxSum=sum;
				maxCard=c;
			}
		}
		return maxCard;
	}
	
	@Override
	public int getHighestScore(List<Card> list) {
		int maxSum=0;
		for(Card c:list) {
			int sum=0;
			sum = (c.getRank().getWeight()*4)+c.getSuit().getWeight();
			if(sum>maxSum) {
				maxSum=sum;
			}
		}
		return maxSum;
	}


	@Override
	public void shuffle(List<Card> list) {
		int max=list.size()-1;
		int min=0;
		int range = max-min+1;
		for(int i=0;i<100;i++) {
			int rand = (int)(Math.random()*range)+min;
			Card c = list.get(rand);
			list.remove(rand);
			rand = (int)(Math.random()*range)+min;
			list.add(rand,c);
		}
	}

	@Override
	public Card getTopCard(List<Card> list) {
		Card c = list.get(0);
		return c;
	}

	@Override
	public void addCard(Card c, List<Card> list) {
		if(list.contains(c)) {
			return;
		}
		list.add(c);
	}

	@Override
	public Card getRandomCard(List<Card> list) {
		int max=list.size()-1;
		int min=0;
		int range = max-min+1;
		int rand = (int)(Math.random()*range)+min;
		Card c = list.get(rand);
		return c;
	}

	@Override
	public int getSize(List<Card> list) {
		return list.size();
	}

	@Override
	public void sortAsc(List<Card> list) {
		Collections.sort(list);
	}

	@Override
	public void sortDesc(List<Card> list) {
		Collections.sort(list,Collections.reverseOrder());
	}
	
	
	private static int calculateSum(List<Card> l) {
		int maxSum=0;
		for(Card c:l) {
			int sum=0;
			sum = (c.getRank().getWeight()*4)+c.getSuit().getWeight();
			if(sum>maxSum) {
				maxSum=sum;
			}
		}
		return maxSum;
	}

}
