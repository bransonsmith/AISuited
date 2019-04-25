import java.awt.*;  
import javax.swing.JFrame;

import Common.Colors;
import Common.Fonts;
import GameObjects.Card;
import GameObjects.Suits;

import java.util.*;
import java.util.List;

import GameRunning.Seat;
import GameRunning.HEGame.HEGame;
import GameRunning.HEGame.Hands.HandStatus;
import WinPercentage.WinPercent; 

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
        width = 1000;
        height = 1000;
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
		g.setFont(Fonts.pot);
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
	        
	        drawMiddleOfTable(g, new Coord(190, 200));
	        drawMessageBoard(g, new Coord(200, 480));
		} 
          
    }  


	private List<Coord> getSeatCoords() {
		List<Coord> coords = new ArrayList<Coord>();

		int hor = 190;
		int ver = 180;
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
		int wd = 140;
		int ht = 105;
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
				g.setFont(Fonts.playerText);
				g.drawString("(" + game.getHand().getPosStr(s) + ")", c.x + 90, c.y + 15);
			}
			g.setFont(Fonts.playerName);
			g.drawString(s.getNumber() + ". " + s.getPlayerName(), c.x + 2, c.y + 15);
			g.setFont(Fonts.playerText);
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
			g.setFont(Fonts.playerText);
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
			drawToken(g, "  D", Color.WHITE, new Coord(tokenX, tokenY), (int)(tokenRad * 1.2));
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
		
		g.setFont(Fonts.card);
		g.drawString(c.toString(), x + 2, y + 6);
	}

	private void drawToken(Graphics g, String label, Color color, Coord coord, int size) {
		
		g.setColor(color);
		g.fillOval(coord.x, coord.y, size, size);
		g.setColor(Color.BLACK);
		g.drawOval(coord.x, coord.y, size, size);
		
		g.setColor(Color.BLACK);
		g.setFont(Fonts.token);
		g.drawString(label, (int)(coord.x + 3), coord.y + 15);
	}
	
	private void drawMiddleOfTable(Graphics g, Coord c) {
		int wd = 410;
		int ht = 265;
		g.setColor(Colors.MiddleBackground);
		g.fillRect(c.x, c.y, wd, ht);
		
		g.setColor(Colors.MiddleOutline);
		g.drawRect(c.x, c.y, wd, ht);
		
		g.setColor(Colors.MiddleText);
		int pot = 0;
		if (game.getHand() != null) {
			pot = game.getHand().getPot().getTotal();
			drawBoard(g, game.getHand().getBoard(), c.x + 40, c.y + 35);
		}
		g.setFont(Fonts.playerName);
		g.setColor(Colors.MiddleText);
		g.drawString("POT ", c.x + 40, c.y + 55);
		g.setFont(Fonts.pot);
		g.setColor(Colors.Pot);
		g.drawString("" + pot, c.x + 70, c.y + 60);
		///g.drawString(s.getStatusString(), c.x + 2, c.y + 45);
		///g.drawString(s.getChipString(), c.x + 2, c.y + 60);
		//g.drawString(s.getCardString(), c.x + 2, c.y + 75);
		if (game.getHand() != null) {
			int outX = c.x + 2; 
			int outY = c.y + 75;
			
			for (Seat s: game.getActiveSeats()) {
				WinPercent winPercent = game.getHand().getWinPercentForSeat(s);
				int cardWidth = 20;
				if (winPercent != null) {
					int nameX = c.x + 2;
					drawCard(g, s.getHoleCards().get(0), nameX, outY, false);
					drawCard(g, s.getHoleCards().get(1), nameX + 22, outY, false);

					int outStartX = c.x + 160 + 4;
					int outStartY = outY;
					outX = outStartX; 

					if (winPercent.getPercent() == 100) {
						g.setColor(Colors.MiddleWinner);
						g.setFont(Fonts.percentWinner);
					}
					else if (winPercent.getPercent() == 0) {
						g.setColor(Colors.MiddleLoser);
						g.setFont(Fonts.percentage);
					}
					else {
						g.setColor(Colors.MiddleText);
						g.setFont(Fonts.percentage);
					}
					g.drawString(String.format("%-10s", s.getPlayerName()) + " " + String.format("%-5.2f", winPercent.getPercent()) + "%", nameX + 43, outY);	
					
					if (winPercent.getPercent() <= 50) {
						if (winPercent.getOuts().size() > 0) {
							g.setColor(Colors.MiddleText);
							g.setFont(Fonts.percentage);
							g.drawString("OUTS->", nameX + 119, outStartY + 12);
						}
						for (Card o: winPercent.getOuts()) {
							if (outX > c.x + wd - cardWidth) {
								outY += 35;
								outX = outStartX;
							}
							drawCard(g, o, outX, outY, false);
							outX += cardWidth + 2;
							
						}
					}
					outY += 50;
				}
			}
		}
	}
	
	
	private void drawBoard(Graphics g, List<Card> board, int x, int y) {
		if (board == null) {
			board = new ArrayList<Card>();
		}
		
		int outLineWD = 140;
		int outLineHT = 40;
		int outLineX = x - 10;
		int outLineY = y - 32;
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(outLineX, outLineY, outLineWD, outLineHT);
		
		g.setColor(Color.BLACK);
		g.drawRect(outLineX, outLineY, outLineWD, outLineHT);
		
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
		int wd = 385;
		int ht = 120;
		g.setColor(Colors.MessageBoardBackground);
		g.fillRect(c.x, c.y, wd, ht);
		
		g.setColor(Colors.MessageBoardOutline);
		g.drawRect(c.x, c.y, wd, ht);
		
		g.setFont(Fonts.message);
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
