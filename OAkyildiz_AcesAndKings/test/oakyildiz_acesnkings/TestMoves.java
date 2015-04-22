/**
 * 
 */
package oakyildiz_acesnkings;


import oakyildiz_acesnkings.moves.*;
import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.MultiDeck;
import ks.launcher.Main;

/**
 * @author OAkyildiz
 *
 */
public class TestMoves extends TestCase {
	
	AcesNKings game;
	GameWindow gw;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp(){
			game = new AcesNKings();
			gw = Main.generateWindow(game, MultiDeck.OrderBySuit);
	}

	protected void tearDown() throws Exception {
		gw.dispose();
	}
	
	public void testDrawCardMove() {
		Card topCard = game.mDeck.peek();
		
		DrawCardMove dcm = new DrawCardMove(game.mDeck, game.wastePile);
		Card fromColumn = game.reserve2.get();
		ToAceFoundationMove colToAF1 = new ToAceFoundationMove(game.reserve2, game.AFoundation1, fromColumn, true);
		ToKingFoundationMove invMove1 = new ToKingFoundationMove(game.reserve2, game.KFoundation1, fromColumn, true);
		
		assertTrue(dcm.valid(game));
		assertTrue(colToAF1.valid(game));
		assertFalse(invMove1.valid(game));
		
		////
		dcm.doMove(game);
		int value = game.getNumLeft().getValue();
		
		assertEquals(104-31-1, game.mDeck.count());
		assertEquals(topCard, game.wastePile.peek())	;
		assertEquals(104-31-1, value);
		
		////
		invMove1.doMove(game);
		assertTrue(game.KFoundation1.empty());
		
		////
		colToAF1.doMove(game);
		
		assertEquals(fromColumn, game.AFoundation1.peek());
		assertFalse(fromColumn.equals(game.reserve2.peek()));
		//
		colToAF1.undo(game);
		
		assertEquals(fromColumn, game.reserve2.peek());
		assertTrue(game.AFoundation1.empty());
		
		//
		
		dcm.undo(game);
		
		assertEquals(104-31, game.mDeck.count());
		
		//automove check
		DeckToTableauAutoMove dta = new DeckToTableauAutoMove(game.mDeck, game.tableau1);
		WasteToTableauAutoMove wta = new WasteToTableauAutoMove(game.mDeck,game.wastePile, game.tableau1);
		Card king = game.tableau1.get();
		ToKingFoundationMove tab1KF1 = new ToKingFoundationMove(game.tableau1, game.KFoundation1, king, true);
		
		assertTrue(tab1KF1.valid(game));
		assertTrue(dta.valid(game));
		assertFalse(wta.valid(game));
		
		//tableau to KF
		tab1KF1.doMove(game);
		
		assertTrue(game.tableau1.empty());
		assertEquals("KD", game.KFoundation1.peek().toString());
		
		//deck auto move
		dta.doMove(game);
		
		assertEquals(104-31-1, game.mDeck.count());
		assertFalse(game.tableau1.empty());
//		
//		dta.undo(game);
//		
//		assertEquals(104-31, game.mDeck.count());
//		assertEquals("KD", game.tableau1.peek().toString());
//		assertTrue(game.KFoundation1.empty());
		
//		//waste auto move
//		for(int j=1; j<=104-31;j++) game.mDeck.get();
//		
//		assertTrue(wta.valid(game));
//		wta.doMove(game);
//		
//		assertEquals(0, game.mDeck.count());
//		assertFalse(game.tableau1.empty());
//		
		

	}
}


