package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import model.GameEngineImpl;
import model.enumeration.BetType;
import model.interfaces.Player;
import view.SummaryPanel;

public class SpinPlayerController implements ActionListener
{
	GameEngineImpl gameEngine;
	Collection<Player> players = new ArrayList<Player>();
	Boolean betHasBeenPlaced = false;
	SummaryPanel summary;

	public SpinPlayerController(GameEngineImpl gameEngine, SummaryPanel summary)
	{
		this.summary = summary;
		this.gameEngine = gameEngine;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		players = gameEngine.getAllPlayers();
		summary.allBetsPlace();
		summary.validate();
		summary.repaint();

		betHasBeenPlaced = false;

		// THread for spin player and spinner
		new Thread()
		{
			@Override
			public void run()
			{
				// spins for ppl that have placed a bet
				for (Player next : players)
				{
					if ((next.getBet() == 0) || (next.getBetType().equals(BetType.NO_BET)))
					{

					} else
					{
						betHasBeenPlaced = true;
						gameEngine.spinPlayer(next, 100, 1000, 100, 50, 500, 50);
					}
				}
				if (betHasBeenPlaced)
				{
					gameEngine.spinSpinner(100, 1000, 100, 50, 500, 50);
				} else
				{
					JOptionPane.showMessageDialog(null, "No Bet Has Been Placed");
				}

				for (Player next : players)
				{
					if (next.getPoints() == 0)
					{
						gameEngine.removePlayer(next);
					}

				}
			}
		}.start();

	}

}
