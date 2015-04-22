package oakyildiz_acesnkings.controllers;


import oakyildiz_acesnkings.AcesNKings;
import oakyildiz_acesnkings.moves.DrawCardMove;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;
import ks.common.model.Move;



public class DeckController extends SolitaireReleasedAdapter{
	
	/** The game. */
	protected AcesNKings game;
	/** The Deck of interest. */
	protected MultiDeck deck;
	/** The WastePile of interest. */
	protected Pile waste;
	
	public DeckController(AcesNKings game, MultiDeck deck, Pile waste){
		super(game);
		
		this.game = game;
		this.deck = deck;
		this.waste = waste;		
	}

	public void mousePressed (java.awt.event.MouseEvent me) {

		// Attempting a DealFourCardMove
		Move m = new DrawCardMove(deck, waste);
		if (m.doMove(game)) {
			game.pushMove (m);     // Successful DealFour Move
			game.refreshWidgets(); // refresh updated widgets.
		}
	}
	
}
