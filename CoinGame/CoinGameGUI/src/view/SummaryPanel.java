package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.GameEngineImpl;
import model.interfaces.Player;
import utilities.CarryInitialPoints;

@SuppressWarnings("serial")
public class SummaryPanel extends JPanel
{
	JLabel summary = new JLabel(" ---Summary---");
	GameEngineImpl gameEngine;
	JFrame frame;
	Collection<Player> players = new ArrayList<Player>();
	int initialWidth, initialHeight;
	JScrollPane scroll = new JScrollPane(this);
	float getWidthRatio = 1;
	float getHeightRatio = 1;
	int ratio;

	public SummaryPanel(GameEngineImpl gameEngine, JFrame frame)
	{
		initialWidth = frame.getWidth();
		initialHeight = frame.getHeight();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.gameEngine = gameEngine;
		this.frame = frame;
		add(summary);
		scroll.setPreferredSize(new Dimension(130, 300));
		this.setPreferredSize(new Dimension(400, 300));
		frame.add(scroll, BorderLayout.WEST);
		validate();

	}

	@Override
	public void repaint()
	{

		// resizing frame functions defined here
		float currentWidth = 740, currentHeight = 500;
		if (frame != null)
		{
			currentHeight = frame.getHeight();
			currentWidth = frame.getWidth();
		}
		// check if window is resized, if so work out wdith ratio and height

		getWidthRatio = currentWidth / initialWidth;

		getHeightRatio = currentHeight / initialHeight;

		// find out if width or height has been resized more to
		if (getWidthRatio <= getHeightRatio)
		{
			ratio = (int) (130 * getWidthRatio);
		} else
		{
			ratio = (int) (130 * getHeightRatio);

		}
		if (scroll != null)
		{
			scroll.setPreferredSize(new Dimension(ratio, ratio));
			validate();
		}

	}

	Boolean playerExists;

	public void updatePlayer(Collection<CarryInitialPoints> initialPoints)
	{
		playerExists = false;
		this.removeAll();
		super.repaint();
		add(new JLabel("---Summary---"));
		
//		iterates of game engine players, and then iterates over initial players. 
//		Initial players is an indication that the player has bet and has a result, so if it finds a 
//		match of game engine players and intial players, it prints out a result label stating win/loss and points. 
//		Then the player is added to the collection players, meaning has placed a bet at one Point. the players that
//		are not in the players class but in the gameengine, indicate they haven't placed a bet, hence get a standard label.
		for (Player next : gameEngine.getAllPlayers())
		{
			for (CarryInitialPoints next2 : initialPoints)
			{
				if (next.getPlayerId().equals(next2.getPlayerId()))
				{
					players.add(next);
					int result = next.getPoints() - next2.getPlayerPoints();
					if (result > 0)
					{
						add(new JLabel(next.getPlayerId() + "|| Balance: " + next.getPoints()));
						add(new JLabel("You Won " + result + " Points"));
						add(new JLabel(" "));

					} else
					{
						String pointsToString = Integer.toString(result);
						add(new JLabel(next.getPlayerId() + " || Balance: " + next.getPoints()));
						add(new JLabel("You Lost " + pointsToString.substring(1) + " Points"));
						add(new JLabel(" "));

					}
				}
			}
			this.setVisible(true);
			validate();

		}
		for (Player next : gameEngine.getAllPlayers())
		{
			if (!(players.contains(next)))
			{
				add(new JLabel(next.getPlayerId() + " || Balance: " + next.getPoints()));

			}
		}

	}

	public void addPlayer(Player player)
	{
//		adds a label when a player is added
		add(new JLabel(" " + player.getPlayerId() + " || Balance: " + player.getPoints()));

	}

	public void allBetsPlace()
	{
//		removes all players from the bar, repaints it with their bet type
		this.removeAll();
		super.repaint();String betType = null;
		for (Player next : gameEngine.getAllPlayers())
		{
			if (next.getBetType().toString().equals("NO_BET")) {
				betType ="No Bet";
			}
			if (next.getBetType().toString().equals("COIN1")) {
				betType ="Coin 1";
			}
			if (next.getBetType().toString().equals("COIN2")) {
				betType ="Coin 2";
			}
			if (next.getBetType().toString().equals("BOTH")) {
				betType ="Both";
			}
			add(new JLabel(next.getPlayerId() + " || Balance: " + next.getPoints()));
			add(new JLabel("Bet: " + betType + "|| Bet Amount: " + next.getBet() + " Points"));
			add(new JLabel(" "));
			
		}
		this.setVisible(true);
		super.repaint();

		
	}

}
