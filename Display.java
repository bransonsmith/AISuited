import java.awt.*;  
import javax.swing.JFrame;

import Common.Colors;
import GameObjects.Card;
import GameObjects.Suits;

import java.util.*;
import java.util.List;

import GameRunning.Seat;
import GameRunning.HEGame.HEGame;
import GameRunning.HEGame.Hands.HandStatus; 

public class Display extends Canvas implements Observer {
	
	private java.util.List<String> messages;
	private HEGame game;
	private int updateCount;
	private java.util.List<Color> theme;
	private int width;
	private int height;
	
	JFrame f;  
	public Display(HEGame _game) {  
		setGame(_game);
		JFrame f=new JFrame();  
        f.add(this);  
        width = 800;
        height = 600;
        f.setSize(width, height);  
        //f.setLayout(null);  
        f.setVisible(true);
        updateCount = 0;
        setThemeColors();
        messages = new ArrayList<String>();
	}
	
	private void setThemeColors() {
		theme = new java.util.ArrayList<Color>(); 
		theme.add(new Color(225, 25, 25));
	}

	public void paint(Graphics g) {  
		updateCount++;
		if (game == null) {
	        g.drawString("NULL GAME " + updateCount,10,10);  
		} else {
			g.setColor(theme.get(0));
	        g.drawString("AISuited!" + updateCount,10,10);  
	        
	        if (game.getSeats() != null) {
	        	int i = 0;
	        	for (Seat s: game.getSeats()) {
	        		drawSeat(g, s, getSeatCoords().get(i++));
	        	}
	        }
	        
	        drawMiddleOfTable(g, new Coord(260, 200));
	        drawMessageBoard(g, new Coord(170, 310));
		} 
          
    }  


	private List<Coord> getSeatCoords() {
		List<Coord> coords = new ArrayList<Coord>();

		int hor = 160;
		int ver = 120;
		int x = 40;
		int y = 80;
		
		coords.add(new Coord(x + (int)(hor *  .5), y));
		coords.add(new Coord(x + (int)(hor * 1.5), y));
		coords.add(new Coord(x + (int)(hor * 2.5), y));
		
		coords.add(new Coord(x + (int)(hor * 3), y + (int)(ver * 1)));
		coords.add(new Coord(x + (int)(hor * 3), y + (int)(ver * 2)));
		
		coords.add(new Coord(x + (int)(hor * 2), y + ver * 3));
		coords.add(new Coord(x + (int)(hor * 1), y + ver * 3));
		coords.add(new Coord(x , y + ver * 2));
		coords.add(new Coord(x , y + ver));
		//coords.add(new Coord(x, y + ver));
		
		return coords;
	}
	
	private void drawSeat(Graphics g, Seat s, Coord c) {
		int wd = 120;
		int ht = 90;
		boolean folded = false;
		g.setColor(Colors.SeatBackground);		
		if (game.getHand() != null) {
			if (game.getHand().getActingPosition() == s.getNumber()) {
				g.setColor(Colors.SeatActing);
			}
			if (s.getHandStatus() == HandStatus.Folded) {
				folded = true;
				g.setColor(Colors.SeatFolded);
			}
			if (s.getHandStatus() == HandStatus.AllIn) {
				g.setColor(Colors.SeatAllIn);
			}
			if (s.getHandStatus() == HandStatus.Busted) {
				g.setColor(Colors.SeatBusted);
			}
		}
		g.fillRect(c.x, c.y, wd, ht);
		
		g.setColor(Colors.SeatOutline);
		g.drawRect(c.x, c.y, wd, ht);
		if (s.getPlayer() != null) {
			g.setColor(Colors.SeatText);
			if (s.getHandStatus() == HandStatus.Busted) {
				g.setColor(Colors.SeatBustedText);
			}
			if (game.getHand() != null) {
				g.drawString("(" + game.getHand().getPosStr(s) + ")", c.x + 90, c.y + 15);
			}
			g.drawString(s.getNumber() + ". " + s.getPlayerName(), c.x + 2, c.y + 15);
			g.drawString(s.getStatusString(), c.x + 2, c.y + 45);
			g.drawString(s.getChipString(), c.x + 2, c.y + 60);
			if (s.getHoleCards() != null && s.getHoleCards().size() > 1) {
				drawCard(g, s.getHoleCards().get(0), c.x + 2, c.y + 75, folded);
				drawCard(g, s.getHoleCards().get(1), c.x + 25, c.y + 75, folded);
			}
		} else {
			g.setColor(Colors.SeatText);
			if (s.getHandStatus() == HandStatus.Busted) {
				g.setColor(Colors.SeatBustedText);
			}
			g.drawString("" + s.getNumber(), c.x + 2, c.y + 45);
		}
		
		int tokenRad = 20;
		int tokenX = c.x + wd - (tokenRad + 2 * 3);
		int tokenY = c.y + tokenRad + 3;
		if (s.getNumber() == game.getBBPosition()) {
			drawToken(g, "BB", Color.RED, new Coord(tokenX, tokenY), tokenRad);
			tokenX += tokenRad + 2;
		}
		if (s.getNumber() == game.getSBPosition()) {
			drawToken(g, "SB", Color.BLUE, new Coord(tokenX, tokenY), tokenRad);
			tokenX += tokenRad + 2;
		}
		if (s.getNumber() == game.getDPosition()) {
			drawToken(g, " D", Color.WHITE, new Coord(tokenX, tokenY), (int)(tokenRad * 1.2));
			tokenX += tokenRad + 2;
		}
		
		
	}
	
	private void drawCard(Graphics g, Card c, int x, int y, boolean faded) {
		g.setColor(Color.WHITE);
		if (faded) {
			g.setColor(Color.LIGHT_GRAY);
		}
		g.fillRect(x, y - 12, 20, 30);
		
		g.setColor(Color.BLACK);
		if (faded) {
			g.setColor(Color.DARK_GRAY);
		}
		g.drawRect(x, y - 12, 20, 30);
		
		if (c.getSuit() == Suits.Diamonds || c.getSuit() == Suits.Hearts) {
			g.setColor(Color.RED.darker());
			if (faded) {
				g.setColor(Color.RED.darker().darker());
			}
		}
		
		g.drawString(c.toString(), x + 2, y + 6);
	}

	private void drawToken(Graphics g, String label, Color color, Coord coord, int size) {
		
		g.setColor(color);
		g.fillOval(coord.x, coord.y, size, size);
		g.setColor(Color.BLACK);
		g.drawOval(coord.x, coord.y, size, size);
		
		g.setColor(Color.BLACK);
		g.drawString(label, (int)(coord.x + 3), coord.y + 15);
	}
	
	private void drawMiddleOfTable(Graphics g, Coord c) {
		int wd = 180;
		int ht = 100;
		g.setColor(Colors.SeatBackground);
		g.fillRect(c.x, c.y, wd, ht);
		
		g.setColor(Colors.SeatOutline);
		g.drawRect(c.x, c.y, wd, ht);
		
		g.setColor(Colors.SeatText);
		int pot = 0;
		if (game.getHand() != null) {
			pot = game.getHand().getPot().getTotal();
			drawBoard(g, game.getHand().getBoard(), c.x + 40, c.y + 80);
		}
		g.drawString("POT (" + pot + ")", c.x + 40, c.y + 40);
		///g.drawString(s.getStatusString(), c.x + 2, c.y + 45);
		///g.drawString(s.getChipString(), c.x + 2, c.y + 60);
		//g.drawString(s.getCardString(), c.x + 2, c.y + 75);
		
	}
	
	
	private void drawBoard(Graphics g, List<Card> board, int x, int y) {
		if (board == null) {
			board = new ArrayList<Card>();
		}
		int drawnCards = 0;
		int cardX = x;
		for (Card c: board) {
			drawCard(g, c, cardX, y - 15, false);
			cardX += 23;
			drawnCards++;
		}
		g.setColor(Color.BLACK);
		while (drawnCards < 5) {
			g.drawRect(cardX, y, 20, 4);
			cardX += 23;
			drawnCards++;
		}
		
	}

	private void drawMessageBoard(Graphics g, Coord c) {
		int wd = 340;
		int ht = 120;
		g.setColor(Colors.MessageBoardBackground);
		g.fillRect(c.x, c.y, wd, ht);
		
		g.setColor(Colors.MessageBoardOutline);
		g.drawRect(c.x, c.y, wd, ht);
		
		g.setColor(Colors.MessageBoardText);
		for (int i = 0; i < game.getMessages().size(); i++) {
			g.drawString(game.getMessages().get(i), c.x + 2, c.y + 15 + (i * 15));
		}
		
	}

	private void setGame(HEGame _game) {
		game = _game;
	}

	@Override
	public void update(Observable o, Object arg) {
		setGame((HEGame) o);
		repaint();
	}
}
