package oakyildiz_acesnkings;

import java.awt.event.MouseEvent;



import java.util.Collections;
import java.util.List;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Move;
import ks.common.model.MultiDeck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import ks.tests.model.ModelFactory;

import oakyildiz_acesnkings.moves.DrawCardMove;
import oakyildiz_acesnkings.moves.ToKingFoundationMove;
import oakyildiz_acesnkings.moves.DeckToTableauAutoMove;
import oakyildiz_acesnkings.moves.WasteToTableauAutoMove;

public class TestMouse extends KSTestCase {
	

	AcesNKings game;
	GameWindow gw;
	
	@Override
	protected void setUp() {
		game = new AcesNKings();
		gw = Main.generateWindow(game, MultiDeck.OrderBySuit);
	}
	
	@Override
	protected void tearDown() {
		gw.dispose();
	}
	
	public void testMouse() {
		//init 
		ModelFactory.init(game.mDeck, "3C 4S 5H");
		ModelFactory.init(game.wastePile, "");
		ModelFactory.init(game.tableau1, "KD");
		ModelFactory.init(game.KFoundation1, "");
		ModelFactory.init(game.AFoundation1, "");
		ModelFactory.init(game.AFoundation2, "");
		ModelFactory.init(game.reserve1, "AS");
		ModelFactory.init(game.reserve2, "AC");
		ModelFactory.init(game.tableau2, "2H");
		
		assertEquals ("5H", game.mDeck.peek().toString());
		assertTrue(game.wastePile.empty());
		assertEquals ("KD", game.tableau1.peek().toString());
		assertTrue(game.KFoundation1.empty());
		assertTrue(game.AFoundation1.empty());
		
		
		//useless click check
		MouseEvent pressKF1 = createReleased(game, game.kfv1, 0, 0);
		game.kfv1.getMouseManager().handleMouseEvent(pressKF1);
		
		MouseEvent releaseKF1 = createReleased(game, game.kfv1, 0, 0);
		game.kfv1.getMouseManager().handleMouseEvent(releaseKF1);
		
		assertTrue(game.KFoundation1.empty());
		
		// press a bit offset into the widget.
		MouseEvent pressDeck = createPressed (game, game.mDeckV, 0, 0);
		game.mDeckV.getMouseManager().handleMouseEvent(pressDeck);
	
		assertEquals ("4S", game.mDeck.peek().toString());
		assertEquals ("5H", game.wastePile.peek().toString());
		
		//Tableau to found.
		MouseEvent pressTableu1 = createPressed (game, game.tv1, 0, 0);
		game.tv1.getMouseManager().handleMouseEvent(pressTableu1);
		
		assertEquals ("KD",game.getContainer().getActiveDraggingObject().getModelElement().getName());
		
		//
		
		game.kfv1.getMouseManager().handleMouseEvent(releaseKF1);
		
		assertEquals ("KD", game.KFoundation1.peek().toString());
		assertEquals ("4S", game.tableau1.peek().toString());
		assertEquals ("3C", game.mDeck.peek().toString());
		
		//
		List<Move> moves =  Collections.list(game.getMoves());
		int length = moves.size();
		assertTrue(moves.get(length-1) instanceof DeckToTableauAutoMove);
		assertTrue(moves.get(length-2) instanceof ToKingFoundationMove);
		
		//
		//DeckToTableauAutoMove automove1 =(DeckToTableauAutoMove) moves.get(length-1);
		
		//undo with right click
		MouseEvent rightClick = createRightClick(game, game.kfv1, 0, 0);
		System.out.println("RMB");
		game.kfv1.getMouseManager().handleMouseEvent(rightClick);
		
		assertTrue(game.getMoves().nextElement() instanceof DrawCardMove);
		assertTrue(game.KFoundation1.empty());
		//assertEquals ("4S", game.mDeck.peek().toString());
		//assertEquals ("KD", game.tableau1.peek().toString());
		//two clicks on deck
		game.mDeckV.getMouseManager().handleMouseEvent(pressDeck);
		game.mDeckV.getMouseManager().handleMouseEvent(pressDeck);
		
		
		assertTrue(game.mDeck.empty());
		assertEquals("3C", game.wastePile.peek().toString());
		
		game.tv1.getMouseManager().handleMouseEvent(pressTableu1);
		game.kfv1.getMouseManager().handleMouseEvent(releaseKF1);
		
		assertEquals ("KD", game.KFoundation1.peek().toString());
		assertEquals ("3C", game.tableau1.peek().toString());
		//assertEquals ("4S", game.wastePile.peek().toString());
		assertTrue(game.mDeck.empty());
		
		moves=  Collections.list(game.getMoves());
		assertTrue(moves.get(moves.size()-1) instanceof WasteToTableauAutoMove);
		
		//reserve1
		MouseEvent pressRes1 = createPressed(game, game.rv1, 0, 0);
		game.rv1.getMouseManager().handleMouseEvent(pressRes1);
		
		MouseEvent releaseAF1 = createReleased(game, game.afv1, 0, 0);
		game.afv1.getMouseManager().handleMouseEvent(releaseAF1);
		
		assertTrue(game.reserve1.empty());
		assertEquals("AS",game.AFoundation1.peek().toString());
		
		//doubleclick reserve
		MouseEvent doubleRes2 = createDoubleClicked(game, game.rv2, 0, 0);
		game.rv2.getMouseManager().handleMouseEvent(doubleRes2);

		assertTrue(game.reserve2.empty());
		assertEquals("AC",game.AFoundation2.peek().toString());
		
		//doubleclick tableau
		
		MouseEvent doubleTab2 = createDoubleClicked(game, game.tv2, 0, 0);
		game.tv2.getMouseManager().handleMouseEvent(doubleTab2);
		
		assertFalse(game.tableau2.empty());
		assertEquals("2H",game.AFoundation1.peek().toString());
		
		
	}
}
