package oakyildiz_acesnkings.controllers;

import oakyildiz_acesnkings.AcesNKings;
import oakyildiz_acesnkings.moves.DeckToTableauAutoMove;
import oakyildiz_acesnkings.moves.WasteToTableauAutoMove;
import ks.common.model.Move;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;

public class FillTableau {

	
	public static void fillTableu(AcesNKings game, MultiDeck deck, Pile waste, Pile tableau){
		if(tableau.empty()){
			Move m = new DeckToTableauAutoMove(deck, tableau);
			if (m.doMove(game)) {
				game.pushMove (m);     // Successful DeckToTableauAutoMove
				game.refreshWidgets(); // refresh updated widgets.
			} else {
				Move n = new WasteToTableauAutoMove(deck,waste, tableau);
				if (n.doMove(game)) {
					game.pushMove (n);     // Successful WasteToTableauAutoMove
					game.refreshWidgets(); // refresh updated widgets.
				}
			}
		}
	}
}
