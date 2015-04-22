package oakyildiz_acesnkings.controllers;


import java.awt.event.MouseEvent;
import java.util.ArrayList;

import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;
import ks.common.model.Stack;
import ks.common.view.CardView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;
import oakyildiz_acesnkings.AcesNKings;
import oakyildiz_acesnkings.moves.ToAceFoundationMove;

public class AFController extends PileController {
	/** Aces 'N Kings game*/
	protected AcesNKings game;
	/**Specific Ace foundation being controlled*/
	protected PileView aceFoundationView;

	protected MultiDeck deck;

	protected Pile waste;

	protected ArrayList<Pile> tableaus;

	public AFController(AcesNKings game, PileView aceFoundationView, MultiDeck deck, Pile waste, ArrayList<Pile> tableaus){
		super(game, aceFoundationView);

		this.game= game;
		this.aceFoundationView = aceFoundationView;
		this.deck = deck;
		this.waste = waste;
		this.tableaus = tableaus;

	}
	@Override
	public void mouseReleased(MouseEvent me){
		Container cont= game.getContainer();

		/** Return if there is no card being dragged chosen. */
		Widget draggingWidget = cont.getActiveDraggingObject();
		if (draggingWidget == Container.getNothingBeingDragged()) {
			System.err.println ("FoundationController::mouseReleased() unexpectedly found nothing being dragged.");
			cont.releaseDraggingObject();		
			return;
		}

		/** Recover the source Stack (reserve Column, foundation, waste or tableau Pile) */
		Widget fromWidget = cont.getDragSource();
		if (fromWidget == null) {
			System.err.println ("FoundationController::mouseReleased(): somehow no dragSource in container.");
			cont.releaseDraggingObject();
			return;
		}
		Stack source = (Stack) fromWidget.getModelElement();
		/** Determine the target Pile */
		Pile aceFoundation = (Pile) aceFoundationView.getModelElement();


		/** the CardView widget being dragged. */
		CardView cardView = (CardView) draggingWidget;
		Card card = (Card) cardView.getModelElement();

		Move m;
		boolean moveDone = false;
		if(source.getName().contains("Foundation")){
			m = new ToAceFoundationMove(source, aceFoundation, card,false);
		} else
			m = new ToAceFoundationMove(source, aceFoundation, card,true);
		if (m.doMove (game)) {
			// Success
			game.pushMove (m);
			moveDone = true;
			System.out.println("Card moved to Foundation");

		} else {
			fromWidget.returnWidget (draggingWidget);
		}
		// release the dragging object, (this will reset dragSource)
		cont.releaseDraggingObject();

		// finally repaint
		cont.repaint();

		if(tableaus.contains(source) && moveDone){

			FillTableau.fillTableu(game, deck, waste, (Pile) source);
		}
	}
}
