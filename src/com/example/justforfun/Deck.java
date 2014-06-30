package com.example.justforfun;

import java.util.ArrayList;



public class Deck {
 public ArrayList<Card>deck;


 
public void generateDeck(){
	ArrayList<Card> ar = new ArrayList<Card>();
	ValidSuiteAndRank vsr = new ValidSuiteAndRank();
	
	for(int i=0;i<vsr.rankArray.length;i++){
		for (int j=0;j<vsr.suiteArray.length;j++){
			PokerCard card = new PokerCard();
			card.rank = vsr.rankArray[i];
			card.suite = vsr.suiteArray[j];
			ar.add(card);
		}
	}
	this.deck = ar;
}

}
