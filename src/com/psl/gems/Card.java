package com.psl.gems;

public class Card implements Comparable<Card>{
	private Suit suit;
	private Rank rank;
	public Card(Suit suit, Rank rank) {
		super();
		this.suit = suit;
		this.rank = rank;
	}
	public Card() {
		super();
	}
	public Suit getSuit() {
		return suit;
	}
	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	public Rank getRank() {
		return rank;
	}
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(suit);
		builder.append("-");
		builder.append(rank);
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		Card other = (Card) obj;
		return(this.suit.equals(other.suit)&& this.rank.equals(other.rank));
	}
	
	//Sorting based on weight of cards
//	@Override
//	public int compareTo(Card o) {
//		if((this.getRank().getWeight()+this.getSuit().getWeight())>(o.getRank().getWeight()+o.getSuit().getWeight())) {
//			return 1;
//		}else if((this.getRank().getWeight()+this.getSuit().getWeight())==(o.getRank().getWeight()+o.getSuit().getWeight())) {
//			return 0;
//		}else {
//			return -1;
//		}
//	}
	
	@Override
	public int compareTo(Card o) {
		if((this.getSuit().getWeight()>o.getSuit().getWeight()) || ((this.getSuit().getWeight()==o.getSuit().getWeight()) && (this.getRank().getWeight()>o.getRank().getWeight()))) {
			return 1;
		}else if((this.getSuit().getWeight()==o.getSuit().getWeight())&&(this.getRank().getWeight()==o.getRank().getWeight())) {
			return 0;
		}else {
			return -1;
		}
	}
	
	
	
	
}
