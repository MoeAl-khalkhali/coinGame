package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import model.GameEngineImpl;
import model.interfaces.Player;

public class RemovePlayerController implements ActionListener
{
	private GameEngineImpl gameEngine;
	JComboBox<?> selectPlayer;

	public RemovePlayerController(GameEngineImpl gameEngine, JComboBox<?> selectPlayer)
	{
		this.gameEngine = gameEngine;
		this.selectPlayer = selectPlayer;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String id = JOptionPane.showInputDialog("ID of player to be removed");
		Player player;
		player = gameEngine.getPlayer(id);
		if (id == null)
		{
			return;
		}
		if (gameEngine.removePlayer(player))
		{

			JOptionPane.showMessageDialog(null, "Player Removed");
		} else
		{
			JOptionPane.showMessageDialog(null, "Incorrect ID");
		}
		selectPlayer.removeItem(id);

		// TODO Auto-generated method stub

	}

}
