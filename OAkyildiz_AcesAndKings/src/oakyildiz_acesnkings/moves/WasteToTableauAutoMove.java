package oakyildiz_acesnkings.moves;

import java.util.Collections;
import java.util.List;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;

public class WasteToTableauAutoMove extends Move{
	
	MultiDeck deck;
	Pile waste;
	Pile tableau;

	public WasteToTableauAutoMove(MultiDeck deck,Pile waste, Pile tableau) {
		this.deck = deck;
		this.waste = waste;
		this.tableau = tableau;
	}
	@Override
	public boolean doMove(Solitaire game){
		if(!valid(game)) return false;
		else{
			Card c = waste.get();
			tableau.add(c);
			System.out.println("AutoMove from Waste");
			return true;
		}
	}
	@Override
	public boolean undo(Solitaire game) {
		Card c = tableau.get();
		waste.add(c);
		System.out.println("Undo AutoMove from Waste");
		
		List<Move> moves = Collections.list(game.getMoves());
		//pop happens after undo() returns.
		Move prevMove = moves.get(moves.size()-1);
				
		if(prevMove!= null) prevMove.undo(game);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return deck.empty() &&!waste.empty()&&tableau.empty();
	}

}
