package oakyildiz_acesnkings.controllers;

import java.awt.event.MouseEvent;

import ks.common.model.MultiDeck;
import ks.common.model.Pile;
import ks.common.view.PileView;
import oakyildiz_acesnkings.AcesNKings;
import oakyildiz_acesnkings.controllers.FillTableau;

public class TableauController extends PileController{
	protected AcesNKings game;
	protected PileView tableauView;
	protected MultiDeck deck;
	protected Pile waste;

	public TableauController(AcesNKings game, PileView tableauView, MultiDeck deck, Pile waste) {
		super(game, tableauView);

		this.game = game;
		this.tableauView = tableauView;
		this.deck = deck;
		this.waste = waste;
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		
		super.mouseClicked(me);
		FillTableau.fillTableu(game, deck, waste, (Pile) tableauView.getModelElement());

	}
}