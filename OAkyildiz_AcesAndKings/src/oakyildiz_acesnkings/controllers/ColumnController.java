package oakyildiz_acesnkings.controllers;

import java.awt.event.MouseEvent;

import oakyildiz_acesnkings.AcesNKings;
import oakyildiz_acesnkings.moves.ToAceFoundationMove;
import oakyildiz_acesnkings.moves.ToKingFoundationMove;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.Container;
import ks.common.view.ColumnView;
import ks.common.view.Widget;


public class ColumnController extends SolitaireReleasedAdapter {
	AcesNKings game;
	ColumnView sourceView;
	
	public ColumnController(AcesNKings game, ColumnView sourceView) {
		super(game);
		
		this.game = game;
		this.sourceView = sourceView;
	}
	@Override
	public void mousePressed(MouseEvent me) {
		// The container manages several critical pieces of information; namely, it
		// is responsible for the draggingObject; in our case, this would be a CardView
		// Widget managing the card we are trying to drag between two piles.
		Container cont = game.getContainer();
		
		/** Return if there is no card to be chosen. */
		Column source = (Column) sourceView.getModelElement();
		if (source.count() == 0) {
			cont.releaseDraggingObject();
			return;
		}
	
		// Get a card to move from ColumnView. Note: this returns a CardView.
		// Note that this method will alter the model for BuildableColumnView if the condition is met.
		CardView cardView = sourceView.getCardViewForTopCard (me);
		
		// an invalid selection of some sort.
		if (cardView == null) {
			cont.releaseDraggingObject();
			return;
		}
		

		Widget w = cont.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("WasteColumnController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}
	
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
			Column source = (Column) sourceView.getModelElement();

			// See if we can move this one card.
			boolean moveMade = false;
			Card c = (Card) source.peek();
			Move m = null;
			Pile foundation = null;
			for (int f = 1; f <=4; f++) {
				foundation = (Pile) game.getModelElement ("AFoundation" + f);
				m = new ToAceFoundationMove(source, foundation, c, (!source.getName().contains("Foundation")));
				if (m.valid(game)) {
					
					c = source.get();
					
					m.doMove(game);
					theGame.pushMove (m);
					
					System.out.println("target:" + f);
					moveMade = true;
					
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