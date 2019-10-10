package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JOptionPane;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.Player;

public class AddPlayerController implements ActionListener
{
	private GameEngineImpl gameEngine;

	public AddPlayerController(GameEngineImpl gameEngine) {
		this.gameEngine = gameEngine;
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String firstName = JOptionPane.showInputDialog("Name");
		String pointsBalance = JOptionPane.showInputDialog("How Many Points Do you Have");
		int balance = Integer.parseInt(pointsBalance);
		Player players =  new SimplePlayer(firstName, firstName, balance);
		gameEngine.addPlayer(players);
		System.out.println(gameEngine.getAllPlayers());
		players = null;
		
		
	}


}
