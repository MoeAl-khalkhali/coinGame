package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import utilities.CarryInitialPoints;
import model.GameEngineImpl;
import model.interfaces.Player;
import view.CoinPanel;
import view.StatusBar;

public class ComboBoxSelectorController implements ItemListener
{
	StatusBar statusBar;
	GameEngineImpl gameEngine;
	CoinPanel coinPanel;
	Player player;
	String item, pointsToString;
	JComboBox<String> playerSelectorComboBox;
	PlaceBetController placeBetController;
	int points;

	public ComboBoxSelectorController(GameEngineImpl gameEngine, StatusBar statusPanel, CoinPanel coinPanel,
			JComboBox<String> playerSelectorComboBox, PlaceBetController placeBetController)
	{
		this.gameEngine = gameEngine;
		this.statusBar = statusPanel;
		this.coinPanel = coinPanel;
		this.playerSelectorComboBox = playerSelectorComboBox;
		this.placeBetController = placeBetController;

	}

	@Override
	public void itemStateChanged(ItemEvent e) throws NullPointerException
	{
		// adds cast to getItem

		// When selector is changed, checks if its the spinner
		item = (String) playerSelectorComboBox.getSelectedItem();

		points = 0;

		// Gets points to set Label

//		if the selected item is a player then the appropriate message will show up, if not then the spinner message will.
		if (gameEngine.getPlayer(item) != null)
		{
			points = gameEngine.getPlayer(item).getPoints();
			pointsToString = Integer.toString(points);
			statusBar.setPointsBalance(pointsToString);
			coinPanel.repaint();
			player = gameEngine.getPlayer(item);

			for (CarryInitialPoints next : placeBetController.initialPoints)
			{
				if (next.getPlayerId().equals(item))
				{
					if (next.getPlayerPoints() < gameEngine.getPlayer(item).getPoints())
					{
						int result = gameEngine.getPlayer(item).getPoints() - next.getPlayerPoints();
						statusBar.setWinLabel(result);
					} else
					{
						int result = next.getPlayerPoints() - gameEngine.getPlayer(item).getPoints();
						statusBar.setlossLabel(result);

					}
				}
			}
		} else
		{
			statusBar.setSpinnerResultLabel();
		}

		try
		{
			// Cehcks if spinner coin exists, if so it gets its coints
			if ((gameEngine.spinnerCoin != null) && (item.equals("Spinner")))
			{
				coinPanel.repaint2(gameEngine.spinnerCoin.getCoin1(), gameEngine.spinnerCoin.getCoin2());
			} else
			{

				if (player.getResult() != null)
				{
					coinPanel.repaint2(player.getResult().getCoin1(), player.getResult().getCoin2());

				}
			}

		} catch (NullPointerException a)
		{
			return;
		}

	}
}
