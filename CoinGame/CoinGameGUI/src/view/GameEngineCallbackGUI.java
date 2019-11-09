package view;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;

import utilities.CarryInitialPoints;
import model.GameEngineImpl;
import model.interfaces.Coin;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback
{
	CoinPanel coinPanel;
	PlayerToolBar toolBar;
	String playerId, spinnerId;
	StatusBar statusPanel;
	Player player;
	int pointsBefore;
	Collection<Player> playerCollection = new ArrayList<Player>();
	GameEngineImpl gameEngine;
	Collection<CarryInitialPoints> initialPoints = new ArrayList<CarryInitialPoints>();
	SummaryPanel summary;
	JFrame frame;

	public GameEngineCallbackGUI(JFrame frame, PlayerToolBar toolBar, CoinPanel coinPanel, StatusBar statusPanel,
			GameEngineImpl gameEngine, SummaryPanel summary)
	{
		this.toolBar = toolBar;
		this.coinPanel = coinPanel;
		this.statusPanel = statusPanel;
		this.gameEngine = gameEngine;
		this.summary = summary;
		this.frame = frame;

	}

	@Override
	public void playerCoinUpdate(Player player, Coin coin, GameEngine engine)
	{

		// checks if this player is selected, if yes then repaints his coin.
		playerId = (String) toolBar.playerSelectorComboBox.getSelectedItem();
		if (playerId != "Spinner")
		{
			if (player.getPlayerId().equals(playerId))
			{
				coinPanel.repaint(coin, coin.getNumber());
			}
		}
		// disable buttons
		toolBar.spinPlayer.setEnabled(false);
		toolBar.placeBet.setEnabled(false);
		toolBar.cancelBet.setEnabled(false);
		toolBar.removePlayer.setEnabled(false);

	}

	@Override
	public void spinnerCoinUpdate(Coin coin, GameEngine engine)
	{

		// checks if this spinner is selected, if yes then repaints his coin.
		spinnerId = (String) toolBar.playerSelectorComboBox.getSelectedItem();
		if (spinnerId.equals("Spinner"))
		{
			coinPanel.repaint(coin, coin.getNumber());
		}

	}

	@Override
	public void playerResult(Player player, CoinPair coinPair, GameEngine engine)
	{
		// TODO Auto-generated method stub
		this.player = player;
		boolean playerExists = false;

		// Each Player that spins, has this method called, hence we add
		// the player to the initial points class, to tell us he has spun and what his
		// points were before he got the result
		for (CarryInitialPoints next : initialPoints)
		{
			if (next.getPlayerId().equals(player.getPlayerId()))
			{
				playerExists = true;
				next.setPlayerPoints(player.getPoints());
			}
		}
		if (playerExists)
		{

		} else
		{
			initialPoints.add(new CarryInitialPoints(player.getPlayerId(), player.getPoints()));

		}

	}

	@Override
	public void spinnerResult(CoinPair coinPair, GameEngine engine)
	{
//		checks the selected box for menu bar, adds collection of all players natively.
		playerCollection = gameEngine.getAllPlayers();
		playerId = (String) toolBar.playerSelectorComboBox.getSelectedItem();
		
//		creates a reference to selected player
		this.player = gameEngine.getPlayer(playerId);
		String pointsOfSelectedPlayer = null;

		if (player != null)
		{
			pointsOfSelectedPlayer = Integer.toString(player.getPoints());
		}
		if ((playerId.equals("Spinner") || playerId.equals("Please Select")))
		{
			statusPanel.setSpinnerResultLabel();

		} else
		{
			for (CarryInitialPoints next : initialPoints)
			{
				if (next.getPlayerId().equals(playerId))
				{
					if (next.getPlayerPoints() < player.getPoints())
					{
						int result = player.getPoints() - next.getPlayerPoints();
						statusPanel.setWinLabel(result);
						statusPanel.setPointsBalance(pointsOfSelectedPlayer);
					} else
					{

						int result = next.getPlayerPoints() - player.getPoints();
						statusPanel.setlossLabel(result);
						statusPanel.setPointsBalance(pointsOfSelectedPlayer);

					}
				}
			}
		}
		
//		repaints the summary panel
		summary.updatePlayer(initialPoints);

		// remves player that has 0 points
		for (Player next : playerCollection)
		{
			if (next.getPoints() == 0)
			{
				toolBar.playerSelectorComboBox.removeItem(next.getPlayerId());

			}
		}

		for (Player next : playerCollection)
		{
			next.resetBet();
		}
		
//		Enables the buttons
		toolBar.spinPlayer.setEnabled(true);
		toolBar.placeBet.setEnabled(true);
		toolBar.cancelBet.setEnabled(true);
		toolBar.removePlayer.setEnabled(true);

		coinPanel.repaint();
		summary.repaint();
		frame.repaint();
	}

}
