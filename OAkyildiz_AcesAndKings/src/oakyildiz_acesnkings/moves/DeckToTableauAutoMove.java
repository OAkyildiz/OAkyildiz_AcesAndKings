package oakyildiz_acesnkings.moves;

import java.util.Collections;
import java.util.List;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;

public class DeckToTableauAutoMove extends Move{

	MultiDeck deck;
	Pile tableau;

	public DeckToTableauAutoMove(MultiDeck deck, Pile tableau) {
		this.deck = deck;
		this.tableau = tableau;
	}
	@Override
	public boolean doMove(Solitaire game){
		if(!valid(game)) return false;
		else{
			Card c = deck.get();
			tableau.add(c);
			game.updateNumberCardsLeft(-1);
			System.out.println("AutoMove from Deck");
			return true;
		}
	}
	@Override
	public boolean undo(Solitaire game) {
		
		Card c = tableau.get();
		System.out.println(c);
		deck.add(c);
		game.updateNumberCardsLeft(+1);
		System.out.println("Undo AutoMove from Deck");
		
		List<Move> moves = Collections.list(game.getMoves());
		//pop happens after undo() returns.

		Move prevMove = moves.get(moves.size()-1);
				
		if(prevMove!= null) prevMove.undo(game);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return !deck.empty()&&tableau.empty();
	}

}
