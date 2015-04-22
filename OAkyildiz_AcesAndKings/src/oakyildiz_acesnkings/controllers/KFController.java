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
import oakyildiz_acesnkings.moves.ToKingFoundationMove;
import oakyildiz_acesnkings.controllers.FillTableau;

public class KFController extends PileController{
	/** Kings 'N Kings game*/
	protected AcesNKings game;
	/**Specific King foundation being controlled*/
	protected PileView kingFoundationView;

	protected MultiDeck deck;

	protected Pile waste;

	protected ArrayList<Pile> tableaus;

	public KFController(AcesNKings game, PileView kingFoundationView,  MultiDeck deck, Pile waste, ArrayList<Pile> tableaus){
		super(game,kingFoundationView);

		this.game = game;
		this.kingFoundationView = kingFoundationView;
		this.deck = deck;
		this.waste = waste;
		this.tableaus = tableaus;
	}

	@Override
	public void mouseReleased(MouseEvent me){
		System.out.println("Mouse released on KF");
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
		Pile kingFoundation = (Pile) kingFoundationView.getModelElement();


		/** the CardView widget being dragged. */
		CardView cardView = (CardView) draggingWidget;
		Card card = (Card) cardView.getModelElement();

		Move m;
		boolean moveDone = false;
		if(source.getName().contains("Foundation")){
			m = new ToKingFoundationMove(source, kingFoundation, card,false);
		} else
			m = new ToKingFoundationMove(source, kingFoundation, card,true);

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