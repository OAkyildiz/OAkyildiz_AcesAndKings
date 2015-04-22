package oakyildiz_acesnkings;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.common.model.MultiDeck;
import ks.launcher.Main;

public class TestHasWon extends TestCase {

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
	
	public void testHasWon(){
		
		MultiDeck testDeck = new MultiDeck("test",2);
		testDeck.create(Deck.OrderByRank);
		for(int c=1;c <= 13;c++){
			game.AFoundation1.add(testDeck.get());
			game.AFoundation2.add(testDeck.get());
			game.AFoundation3.add(testDeck.get());
			game.AFoundation4.add(testDeck.get());
			
			game.KFoundation1.add(testDeck.get());
			game.KFoundation2.add(testDeck.get());
			game.KFoundation3.add(testDeck.get());
			game.KFoundation4.add(testDeck.get());
		}
		
		assertTrue(game.hasWon());
	}

}
