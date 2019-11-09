package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import utilities.CarryInitialPoints;
import view.SummaryPanel;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.GameEngineImpl;
import model.enumeration.BetType;
import model.interfaces.Player;

public class PlaceBetController implements ActionListener
{
	JTextField betAmount;
	JComboBox<?> playerSelectorComboBox;
	GameEngineImpl gameEngine;
	JComboBox<?> betTypeCombo;
	Collection<Player> players = new ArrayList<Player>();
	SummaryPanel summary;
	Collection<CarryInitialPoints> initialPoints = new ArrayList<CarryInitialPoints>();

	Boolean allBetsPlaced = true;

	public PlaceBetController(GameEngineImpl gameEngine, JTextField betAmount, JComboBox<?> playerSelectorComboBox,
			JComboBox<?> betTypeCombo, SummaryPanel summary)
	{
		this.summary = summary;

		this.betAmount = betAmount;
		this.playerSelectorComboBox = playerSelectorComboBox;
		this.gameEngine = gameEngine;
		this.betTypeCombo = betTypeCombo;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) throws NumberFormatException
	{
		// gets selected item from combo box to string same with bet type;
		String idToGet = (String) playerSelectorComboBox.getSelectedItem();
		String betTypeToSet = (String) betTypeCombo.getSelectedItem();

		if ((idToGet.equals("Spinner")) || (idToGet.equals("Please Select")))
		{
			JOptionPane.showMessageDialog(null, "Invalid Player");
			return;
		}

		// Converts betAmount to string
		String betAmountString = betAmount.getText();
		int betAmountInt = 0;
		
//		Turns betAmount to integer as betAmountInt
//		checks for issues with bet amount
		try
		{
			betAmountInt = Integer.parseInt(betAmountString);
			if (betAmountInt > gameEngine.getPlayer(idToGet).getPoints())
			{
				JOptionPane.showMessageDialog(null, "Not Enough Points");
				return;

			}
		} catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Invalid Bet");
			return;

		}

		initialPoints.add(new CarryInitialPoints(idToGet, gameEngine.getPlayer(idToGet).getPoints()));

		// checks for bet type and places bet
		if ((gameEngine.getPlayer(idToGet) != null) && (betTypeToSet == "Coin 1"))
		{
			gameEngine.placeBet(gameEngine.getPlayer(idToGet), betAmountInt, BetType.COIN1);

		}
		if ((gameEngine.getPlayer(idToGet) != null) && (betTypeToSet == "Coin 2"))
		{
			gameEngine.placeBet(gameEngine.getPlayer(idToGet), betAmountInt, BetType.COIN2);
		}
		if ((gameEngine.getPlayer(idToGet) != null) && (betTypeToSet == "Both"))
		{
			gameEngine.placeBet(gameEngine.getPlayer(idToGet), betAmountInt, BetType.BOTH);
		}
		if ((gameEngine.getPlayer(idToGet) != null) && (betTypeToSet == "No Bet"))
		{
			gameEngine.placeBet(gameEngine.getPlayer(idToGet), betAmountInt, BetType.NO_BET);
		}
		
//		Checks if all bets have been placed
		allBetsPlaced = true;
		this.players = gameEngine.getAllPlayers();
		for (Player next : players)
		{
			if ((next.getBet() > 0) && (next.getBetType() != BetType.NO_BET))
			{

			} else
			{
				allBetsPlaced = false;
			}

		}
		if (allBetsPlaced)
		{
			summary.allBetsPlace();
			summary.validate();
			summary.repaint();
			new Thread()
			{
				@Override
				public void run()
				{
					for (Player next : players)
					{

						gameEngine.spinPlayer(next, 100, 1000, 100, 50, 500, 50);
					}

					gameEngine.spinSpinner(100, 1000, 100, 50, 500, 50);
				}
			}.start();

		}
	}
}