package com.example.justforfun;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class UpTo24 extends Activity {
	private Button Deal,answer;
	private ProgressBar pb;
	private Deck deck;
	private TextView tv1,tv2,tv3,tv4,tv5;
	private StopWatch s;
	private int i=0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upto24);
		pb = (ProgressBar)this.findViewById(R.id.progressBar1);
		pb.setVisibility(ProgressBar.INVISIBLE);
		tv1 = (TextView)this.findViewById(R.id.textView1);
		tv2 = (TextView)this.findViewById(R.id.textView2);
		tv3 = (TextView)this.findViewById(R.id.textView3);
		tv4 = (TextView)this.findViewById(R.id.textView4);
		tv5 = (TextView)this.findViewById(R.id.textView5);
		
		dealCards();
		
		s = new StopWatch();
		answer = (Button)this.findViewById(R.id.button1);
		answer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (i==0){
					s.start();
					pb.setVisibility(ProgressBar.VISIBLE);
					
					i=1;
				}else{
					s.stop();
					pb.setVisibility(ProgressBar.INVISIBLE);
					String time = Long.toString(s.getElapsedTimeSecs());
					tv5.setText("Time Elapsed: "+time);
					i=0;
				}
				
			}
		});
		Deal = (Button)this.findViewById(R.id.deal);
   	    Deal.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv5.setText("Time Elapsed: ");
				if(s.running){
					s.stop();
					String time = Long.toString(s.getElapsedTimeSecs());
					tv5.setText("Time Elapsed: "+time);
					i=0;
				}
				
				pb.setVisibility(ProgressBar.INVISIBLE);
				dealCards();
				
			    
				
			}

			
		});
		
		
	}
	private void dealCards()
	{     deck =new Deck();
	     deck.generateDeck();
		Card card = new Card();
	    int temp1 = (int)(Math.random()*deck.deck.size());
	    card = deck.deck.get(temp1);
	    deck.deck.remove(temp1);
	    tv1.setText(card.suite+card.rank);
	    int temp2 = (int)(Math.random()*deck.deck.size());
	    card = deck.deck.get(temp2);
	    deck.deck.remove(temp2);
	    tv2.setText(card.suite+card.rank);
	    int temp3 = (int)(Math.random()*deck.deck.size());
	    card = deck.deck.get(temp3);
	    deck.deck.remove(temp3);
	    tv3.setText(card.suite+card.rank);
	    int temp4 = (int)(Math.random()*deck.deck.size());
	    card = deck.deck.get(temp4);
	    deck.deck.remove(temp4);
	    tv4.setText(card.suite+card.rank);
		
    }
	
}
