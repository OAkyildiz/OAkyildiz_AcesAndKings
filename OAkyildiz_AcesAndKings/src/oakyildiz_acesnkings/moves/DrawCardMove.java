package oakyildiz_acesnkings.moves;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;
import ks.common.model.Move;


public class DrawCardMove extends Move{
	
	MultiDeck deck;
	Pile waste;
	
	public DrawCardMove(MultiDeck deck, Pile waste) {
		this.deck = deck;
		this.waste = waste;
	}
	@Override
	public boolean doMove(Solitaire game){
		if(!valid(game)) return false;
		else{
			Card c = deck.get();
			waste.add(c);
			game.updateNumberCardsLeft(-1);
			return true;
		}
	}
	@Override
	public boolean undo(Solitaire game) {
		Card c = waste.get();
		deck.add(c);
		game.updateNumberCardsLeft(+1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return !deck.empty();
	}

}
