package oakyildiz_acesnkings;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import oakyildiz_acesnkings.controllers.*;
import ks.client.gamefactory.GameWindow;
import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.MultiDeck;
import ks.common.model.Column;
import ks.common.model.MutableInteger;
import ks.common.model.Pile;
import ks.common.view.CardImages;
import ks.common.view.DeckView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.common.view.ColumnView;
import ks.launcher.Main;

public class AcesNKings extends Solitaire{
	
	MultiDeck mDeck = new MultiDeck(2);
	
	Pile wastePile;
	Pile tableau1, tableau2, tableau3, tableau4;
	Pile AFoundation1, AFoundation2, AFoundation3, AFoundation4;
	Pile KFoundation1, KFoundation2, KFoundation3, KFoundation4;
	
	ArrayList<Pile> tableaus = new ArrayList<Pile>();
	ArrayList<Pile> foundations = new ArrayList<Pile>();
	Column reserve1, reserve2;
	
	/*
	 * Views 
	 */
	DeckView mDeckV;
	
	PileView wasteV;
	PileView tv1, tv2, tv3, tv4;
	PileView afv1, afv2, afv3, afv4;
	PileView kfv1, kfv2, kfv3, kfv4;
	
	ColumnView rv1, rv2;
	
	IntegerView scoreView;
	IntegerView	cardsLeftView;
	
	int goal = 104;
	public static int score = 1;
	public static int penalty = -1; //prefer -2
	
	public static final int windowW = 1100;
	public static final int windowH = 500;
	
	@Override
	public String getName() {
		return "AcesNKings by OAkyildiz";
	}

	
	@Override
	public boolean hasWon() {
//		return AFoundation1.count()==13 && AFoundation2.count()==13 && 
//				AFoundation3.count()==13 && AFoundation4.count()==13 && 
//				KFoundation1.count()==13 && KFoundation2.count()==13 && 
//				KFoundation3.count()==13 && KFoundation4.count()==13;
		
		return (this.getScoreValue()==goal);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension (windowW, windowH);
	}
	
	@Override
	public void initialize() {
		// initialize model
		initializeModel(getSeed());
		initializeView();
		
		tableaus.add(tableau1);
		tableaus.add(tableau2);
		tableaus.add(tableau3);
		tableaus.add(tableau4);

		
		//Prepare the board
		
		for(int i=0; i<13; i++){
			reserve1.add(mDeck.get());
			reserve2.add(mDeck.get());
		}
		tableau1.add(mDeck.get());
		tableau2.add(mDeck.get());
		tableau3.add(mDeck.get());
		tableau4.add(mDeck.get());
		
		wastePile.add(mDeck.get());
		//update the number of remaining cards.
		updateNumberCardsLeft(-31);
		
		initializeControllers();
	}
	
	private void initializeControllers() {
		
		mDeckV.setMouseAdapter(new DeckController (this, mDeck, wastePile));
		mDeckV.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		mDeckV.setUndoAdapter (new SolitaireUndoAdapter(this));
	//
		
		wasteV.setMouseAdapter(new PileController(this, wasteV));
		wasteV.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		wasteV.setUndoAdapter (new SolitaireUndoAdapter(this));
	
	//
		afv1.setMouseAdapter(new AFController(this, afv1, mDeck, wastePile, tableaus));
		afv1.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		afv1.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		
		afv2.setMouseAdapter(new AFController(this, afv2,  mDeck, wastePile, tableaus));
		afv2.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		afv2.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		afv3.setMouseAdapter(new AFController(this, afv3,  mDeck, wastePile, tableaus));
		afv3.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		afv3.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		afv4.setMouseAdapter(new AFController(this, afv4,  mDeck, wastePile, tableaus));
		afv4.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		afv4.setUndoAdapter (new SolitaireUndoAdapter(this));
	//
		kfv1.setMouseAdapter(new KFController(this, kfv1,  mDeck, wastePile, tableaus));
		kfv1.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		kfv1.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		kfv2.setMouseAdapter(new KFController(this, kfv2,  mDeck, wastePile, tableaus));
		kfv2.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		kfv2.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		kfv3.setMouseAdapter(new KFController(this, kfv3,  mDeck, wastePile, tableaus));
		kfv3.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		kfv3.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		kfv4.setMouseAdapter(new KFController(this, kfv4,  mDeck, wastePile, tableaus));
		kfv4.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		kfv4.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		tv1.setMouseAdapter(new TableauController(this, tv1, mDeck, wastePile));
		tv1.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		tv1.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		tv2.setMouseAdapter(new TableauController(this, tv2, mDeck, wastePile));
		tv2.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		tv2.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		tv3.setMouseAdapter(new TableauController(this, tv3, mDeck, wastePile));
		tv3.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		tv3.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		tv4.setMouseAdapter(new TableauController(this, tv4, mDeck, wastePile));
		tv4.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		tv4.setUndoAdapter (new SolitaireUndoAdapter(this));
		
	//		
		rv1.setMouseAdapter(new ColumnController(this, rv1));
		rv1.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		rv1.setUndoAdapter (new SolitaireUndoAdapter(this));;
		
		rv2.setMouseAdapter(new ColumnController(this, rv2));
		rv2.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		rv2.setUndoAdapter (new SolitaireUndoAdapter(this));;
		
	}

	
	private void initializeModel(int seed) {
		
		//Trunk
		mDeck = new MultiDeck("deck",2);
		mDeck.create(seed);
		model.addElement (mDeck);   // add to our model (as defined within our superclass).
		
		//Waste
		wastePile = new Pile("waste");
		model.addElement(wastePile);
		
		//Tableau
		tableau1 = new Pile("tableau1");
		model.addElement(tableau1);
		
		
		tableau2 = new Pile("tableau2");
		model.addElement(tableau2);
		
		
		tableau3 = new Pile("tableau3");
		model.addElement(tableau3);
		
		
		tableau4 = new Pile("tableau4");
		model.addElement(tableau4);
		
		
	
		//Ace Foundation
		AFoundation1 = new Pile("AFoundation1");
		model.addElement(AFoundation1);
		
		
		AFoundation2 = new Pile("AFoundation2");
		model.addElement(AFoundation2);
		
		AFoundation3 = new Pile("AFoundation3");
		model.addElement(AFoundation3);
		
		AFoundation4 = new Pile("AFoundation4");
		model.addElement(AFoundation4);
		
		//King Foundation
		KFoundation1 = new Pile("KFoundation1");
		model.addElement(KFoundation1);
		
		KFoundation2 = new Pile("KFoundation2");
		model.addElement(KFoundation2);
		
		KFoundation3 = new Pile("KFoundation3");
		model.addElement(KFoundation3);
		
		KFoundation4 = new Pile("KFoundation4");
		model.addElement(KFoundation4);
	
		//Reserve
		reserve1 = new Column("reserve1");
		model.addElement(reserve1);
		
		reserve2 = new Column("reserve2");
		model.addElement(reserve2);

		
		updateNumberCardsLeft(mDeck.count());
		updateScore(0);
	}
	
	private void initializeView() {
		CardImages ci = getCardImages();
		
		int cardX = ci.getWidth();
		int cardY = ci.getHeight();
		
		int fullSpc = 30;
		int halfSpc = 20;
		int qtSpc =15;
		//int lngSpc=100;
		
		mDeckV = new DeckView(mDeck);
		mDeckV.setBounds (2*fullSpc+cardX, windowH-fullSpc-2*cardY,cardX,cardY);
		container.addWidget(mDeckV);

		// create PileViews, and 2 ColumnViews
		
		//Waste
		wasteV = new PileView(wastePile);
		wasteV.setBounds(halfSpc+2*fullSpc+2*cardX, windowH-fullSpc-2*cardY, cardX, cardY);
		container.addWidget(wasteV);
		
		//Tableau1
		tv1 = new PileView(tableau1);
		tv1.setBounds(windowW-3*fullSpc-2*cardX, windowH-fullSpc-2*cardY, cardX, cardY);
		container.addWidget(tv1);
		
		tv2 = new PileView (tableau2);
		tv2.setBounds(windowW-halfSpc-3*fullSpc-3*cardX, windowH-fullSpc-2*cardY, cardX, cardY);
		container.addWidget(tv2);
		
		tv3 = new PileView (tableau3);
		tv3.setBounds (windowW-2*halfSpc-3*fullSpc-4*cardX, windowH-fullSpc-2*cardY, cardX, cardY);
		container.addWidget(tv3);
		
		tv4 = new PileView (tableau4);
		tv4.setBounds(windowW-3*halfSpc-3*fullSpc-5*cardX, windowH-fullSpc-2*cardY, cardX, cardY);
		container.addWidget(tv4);
		
		//Ace Foundations
		afv1 = new PileView(AFoundation1);
		afv1.setBounds(2*fullSpc+cardX, fullSpc, cardX, cardY);
		container.addWidget (afv1);

		afv2 = new PileView(AFoundation2);
		afv2.setBounds(2*fullSpc+qtSpc+2*cardX, fullSpc, cardX, cardY);
		container.addWidget(afv2);

		afv3 = new PileView(AFoundation3);
		afv3.setBounds(2*fullSpc+2*qtSpc+ 3*cardX, fullSpc, cardX, cardY);
		container.addWidget(afv3);

		afv4 = new PileView(AFoundation4);
		afv4.setBounds(2*fullSpc+3*qtSpc+4*cardX, fullSpc, cardX, cardY);
		container.addWidget(afv4);

		//King Foundations
		kfv1 = new PileView(KFoundation1);
		kfv1.setBounds(windowW-3*fullSpc-2*cardX, fullSpc, cardX, cardY);
		container.addWidget(kfv1);

		kfv2 = new PileView(KFoundation2);
		kfv2.setBounds(windowW-qtSpc-3*fullSpc-3*cardX, fullSpc, cardX, cardY);
		container.addWidget(kfv2);

		kfv3 = new PileView(KFoundation3);
		kfv3.setBounds(windowW-2*qtSpc-3*fullSpc-4*cardX, fullSpc, cardX, cardY);
		container.addWidget(kfv3);

		kfv4 = new PileView(KFoundation4);
		kfv4.setBounds(windowW-3*qtSpc-3*fullSpc-5*cardX, fullSpc, cardX, cardY);
		container.addWidget(kfv4);

		//Reserve Columns
		rv1 = new ColumnView(reserve1);
		rv1.setBounds (fullSpc, fullSpc, cardX, 4*cardY);
		container.addWidget (rv1);

		rv2 = new ColumnView(reserve2);
		rv2.setBounds (windowW-2*fullSpc-cardX, fullSpc, cardX, 4*cardY);
		container.addWidget (rv2);
		
		scoreView = new IntegerView (getScore());
		Font scoreFont=new Font(Font.SANS_SERIF,Font.BOLD,20);
		scoreView.setFont (scoreFont);
		scoreView.setBounds (3*fullSpc+3*qtSpc+5*cardX, qtSpc+fullSpc, 60,60 );
		container.addWidget (scoreView);

		cardsLeftView = new IntegerView (getNumLeft());
		cardsLeftView.setFontSize (14);
		cardsLeftView.setBounds (2*fullSpc+cardX, windowH-halfSpc-qtSpc-2*cardY-60, 80, 60);
		container.addWidget (cardsLeftView);
		
	}
		
	
	
	/** Code to launch solitaire variation. */
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
	
		GameWindow gw= Main.generateWindow(new AcesNKings(),MultiDeck.OrderBySuit );
		gw.setVisible(true);
	}

}
