package oakyildiz_acesnkings.moves;

import oakyildiz_acesnkings.AcesNKings;
import ks.common.games.Solitaire;
import ks.common.model.Stack;
import ks.common.model.Pile;
import ks.common.model.Card;
import ks.common.model.Move;

//Move subclass for moving a card from anywhere to an Ace foundation.
public class ToKingFoundationMove extends Move{
	
	Stack source;
	Pile target;
	Card cardBeingDragged;
	boolean point;	
	public ToKingFoundationMove(Stack source, Pile target, Card cardBeingDragged, boolean point) {
		this.source = source;
		this.target = target;
		this.cardBeingDragged= cardBeingDragged;
		this.point = point;
	}
	
	public boolean doMove(Solitaire game){
		System.out.println("Attempting Move: ToKingFoundationMove");
		if(!valid(game)) return false;
		else{
			
			if(cardBeingDragged== null) cardBeingDragged=source.get();
			target.add(cardBeingDragged);
			
			if(point) game.updateScore(AcesNKings.score);
			
			return true;
		}
	}
	public boolean undo(Solitaire game){
		
		source.add(target.get());
		
		if(point) game.updateScore(AcesNKings.penalty);
		System.out.println("Undo ToFoundation move");
		
		return true;
	}
	public boolean valid(Solitaire game){

		return (((Stack)source!=(Stack)target)&&(cardBeingDragged.compareTo(target.peek()) == -1 ||(target.empty() && cardBeingDragged.getRank() == Card.KING )));
		
	}
				

	
}
