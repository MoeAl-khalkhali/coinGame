package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import model.GameEngineImpl;
import model.interfaces.Player;

public class CancelBetController implements ActionListener
{
	Collection<Player> players = new ArrayList<Player>();
	GameEngineImpl gameEngine;
	JComboBox<?> playerSelectorComboBox;

	public CancelBetController(GameEngineImpl gameEngine, JComboBox<?> playerSelectorComboBox)
	{
		this.gameEngine = gameEngine;
		this.playerSelectorComboBox = playerSelectorComboBox;

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String playerToCancel = (String) playerSelectorComboBox.getSelectedItem();
		if (gameEngine.getPlayer(playerToCancel) != null)
		{
			gameEngine.getPlayer(playerToCancel).resetBet();
			JOptionPane.showMessageDialog(null, "Bet Has Been Cancelled");
		}

		// TODO Auto-generated method stub

	}

}
