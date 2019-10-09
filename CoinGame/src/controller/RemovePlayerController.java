package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.Player;

public class RemovePlayerController implements ActionListener{
	private GameEngineImpl gameEngine;

	public RemovePlayerController(GameEngineImpl gameEngine) {
		this.gameEngine = gameEngine;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String id = JOptionPane.showInputDialog("ID of player to be removed");
		Player player;
		player = gameEngine.getPlayer(id);
		if (gameEngine.removePlayer(player)) {
		int input = JOptionPane.showConfirmDialog(null, "Player Removed");
		}else {
			int input = JOptionPane.showConfirmDialog(null, "Incorrect ID");
		}
		
		// TODO Auto-generated method stub
		
	}

}
