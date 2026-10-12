package com.psl.gems.card;

import java.util.*;

import com.psl.gems.Card;
import com.psl.gems.Player;

public interface CardGameDAO {
	public List<Card> cardPack();
	
	public Player register(String name);
	
	public List<List<Card>> play(List<Card> list);
	
	public String getWinner(List<Card> l1, List<Card>l2);
	
	public void shuffle(List<Card> list);
	
	public Card getHighestCard(List<Card> list);
	
	public int getHighestScore(List<Card> list);
	
	public Card getTopCard(List<Card> list);
	
	public void addCard(Card c, List<Card> list);
	
	public Card getRandomCard(List<Card> list);
	
	public int getSize(List<Card> list);
	
	public void sortAsc(List<Card> list);
	
	public void sortDesc(List<Card> list);
}
