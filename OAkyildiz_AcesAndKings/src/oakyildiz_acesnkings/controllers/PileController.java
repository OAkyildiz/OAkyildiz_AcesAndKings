package oakyildiz_acesnkings.controllers;



import java.awt.event.MouseEvent;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

import oakyildiz_acesnkings.AcesNKings;
import oakyildiz_acesnkings.moves.ToAceFoundationMove;
import oakyildiz_acesnkings.moves.ToKingFoundationMove;

public class PileController extends SolitaireReleasedAdapter {
	protected AcesNKings game;
	protected PileView sourceView;
	
	public PileController(AcesNKings game, PileView sourceView) {
		super(game);
		
		this.game = game;
		this.sourceView = sourceView;
	}
	
	@Override
	public void mousePressed(MouseEvent me) {
		// The container manages several critical pieces of information; namely, it
		// is responsible for the draggingObject; in our case, this would be a CardView
		// Widget managing the card we are trying to drag between two piles.
		System.out.println("LMBDown");
		Container cont = game.getContainer();
		/** Return if there is no card to be chosen. */
		Pile source = (Pile) sourceView.getModelElement();
		if (source.count() == 0) {
			cont.releaseDraggingObject();
			return;
		}
	
		// Get a card to move from PileView. Note: this returns a CardView.
		// Note that this method will alter the model for BuildablePileView if the condition is met.
		CardView cardView = sourceView.getCardViewForTopCard (me);
		
		// an invalid selection of some sort.
		if (cardView == null) {
			cont.releaseDraggingObject();
			return;
		}

		Widget w = cont.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("PileController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}
		System.out.println("card picked up");
		// Tell container which object is being dragged, and where in that widget the user clicked.
		cont.setActiveDraggingObject (cardView, me);
		
		// Tell container which source widget initiated the drag
		cont.setDragSource (sourceView);
	
	
		sourceView.redraw();
	}
	@Override
	public void mouseClicked(MouseEvent me) {

		if(me.getClickCount() > 1) {

			// Point to our underlying model element.
			Pile source = (Pile) sourceView.getModelElement();

			// See if we can move this one card.
			boolean moveMade = false;
			Card c = (Card) source.peek();
			Move m = null;
			Pile foundation = null;
			for (int f = 1; f <=4; f++) {
				foundation = (Pile) game.getModelElement ("AFoundation" + f);
				System.out.println(foundation.getName());
				m = new ToAceFoundationMove(source, foundation, c, (!source.getName().contains("Foundation")));
				if (m.valid(theGame)) {
					c = source.get();
					
					m.doMove(game);
					theGame.pushMove (m);
					
					System.out.println("target:" + f);
					moveMade = true;
					System.out.println(foundation.peek().toString());
					theGame.refreshWidgets();
					break;
				}
			}

			if (!moveMade) {
				for (int g = 1; g <=4; g++) {
					foundation = (Pile) game.getModelElement ("KFoundation" + g);
					m = new ToKingFoundationMove(source, foundation, c, (!source.getName().contains("Foundation")));
					if (m.valid(game)) {
						c = source.get();
						
						m.doMove(game);
						theGame.pushMove (m);
						
						System.out.println("target:" + g);
						moveMade = true;
						
						theGame.refreshWidgets();
						break;
					}
				}
					if (!moveMade){
						java.awt.Toolkit.getDefaultToolkit().beep();
						return; // announce our displeasure	
					}
			}
		}
	}
	
}