package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import model.GameEngineImpl;
import model.SimplePlayer;
import model.enumeration.BetType;
import model.interfaces.Player;
import utilities.CarryInitialPoints;
import view.SummaryPanel;

public class AddPlayerController implements ActionListener
{
	private GameEngineImpl gameEngine;
	JComboBox<String> selectPlayer;
	SummaryPanel summary;
	CarryInitialPoints carry;
	int balance = 0;
	public AddPlayerController(GameEngineImpl gameEngine, JComboBox<String> selectPlayer, SummaryPanel summary)
		{
			this.gameEngine = gameEngine;
			this.selectPlayer = selectPlayer;
			this.summary = summary;
		}

	@Override
	public void actionPerformed(ActionEvent e) throws NumberFormatException
		{
			// Input dialogue for each player
			String id = JOptionPane.showInputDialog("Username (ID)");
			if (id == null)
				{
					return;
				}
			String firstName = JOptionPane.showInputDialog("Name");
			if (firstName == null)
				{
					return;
				}
			String pointsBalance = JOptionPane.showInputDialog("How Many Points Do you Have");
			if (pointsBalance == null)
				{
					return;
				}

//			Convert balance to integer
//			checks if balance is valid
			try
				{
				balance = Integer.parseInt(pointsBalance);
				if (balance<= 0) {
					JOptionPane.showMessageDialog(null, "Invalid points");
					return;
				}
				} catch (NumberFormatException a)
				{
					JOptionPane.showMessageDialog(null, "Invalid points");
					return;
				}
			
//			Adds player to gaemEngine, resets bets and adds to summary panel
			Player players = new SimplePlayer(id, firstName, balance);
			players.resetBet();
			players.setBetType(BetType.NO_BET);
			if(gameEngine.getPlayer(id) == null) {
				gameEngine.addPlayer(players);
				summary.addPlayer(players);
			}


			Boolean exist = false;
			
			// adds ID to comboBox. Ensure no duplicates are present
			int i = 0;
			for (i = 0; i < selectPlayer.getItemCount(); i++)
				{
					if (selectPlayer.getItemAt(i).equals(id))
						{
							exist = true;
							break;
						}
						 
				}
			if (exist)
				{
					selectPlayer.remove(i);
					selectPlayer.addItem(id);
				} else
				{
					selectPlayer.addItem(id);

				}

		}

}
