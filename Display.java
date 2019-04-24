import java.awt.*;  
import javax.swing.JFrame;

import Common.Colors;

import java.util.*;
import java.util.List;

import GameRunning.Seat;
import GameRunning.HEGame.HEGame; 

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

		g.setColor(Colors.SeatBackground);
		g.fillRect(c.x, c.y, wd, ht);
		
		if (!s.isActive()) {
			g.setColor(Colors.SeatBackground.darker());
			g.fillRect(c.x, c.y, wd, ht);
		}
		
		g.setColor(Colors.SeatOutline);
		g.drawRect(c.x, c.y, wd, ht);
		
		g.setColor(Colors.SeatText);
		g.drawString("(" + game.getHand().getPosStr(s) + ")", c.x + 90, c.y + 15);
		g.drawString(s.getNumber() + ". " + s.getPlayerName(), c.x + 2, c.y + 15);
		g.drawString(s.getStatusString(), c.x + 2, c.y + 45);
		g.drawString(s.getChipString(), c.x + 2, c.y + 60);
		if (s.getHoleCards() != null) {
			g.drawString(s.getCardString(), c.x + 2, c.y + 75);
		}
		
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
			g.drawString(game.getHand().boardStr(), c.x + 40, c.y + 80);
		}
		g.drawString("POT (" + pot + ")", c.x + 40, c.y + 40);
		///g.drawString(s.getStatusString(), c.x + 2, c.y + 45);
		///g.drawString(s.getChipString(), c.x + 2, c.y + 60);
		//g.drawString(s.getCardString(), c.x + 2, c.y + 75);
		
	}
	
	
	private void drawMessageBoard(Graphics g, Coord c) {
		int wd = 340;
		int ht = 100;
		g.setColor(Colors.MessageBoardBackground);
		g.fillRect(c.x, c.y, wd, ht);
		
		g.setColor(Colors.MessageBoardOutline);
		g.drawRect(c.x, c.y, wd, ht);
		
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
