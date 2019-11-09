package view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import controller.AddPlayerController;
import controller.CancelBetController;
import controller.ComboBoxSelectorController;
import controller.PlaceBetController;
import controller.RemovePlayerController;
import controller.SpinPlayerController;
import model.GameEngineImpl;

@SuppressWarnings("serial")
public class PlayerToolBar extends JToolBar
{

	// Declaring all items needed for the toolbar
	JToolBar toolBar = new JToolBar();
	JButton addPlayer = new JButton("+ Player");
	JButton removePlayer = new JButton("- Player");
	JComboBox<String> playerSelectorComboBox = new JComboBox<String>();
	JButton placeBet = new JButton("Place Bet");
	JTextField betAmount = new JTextField("Amount");
	JButton spinPlayer = new JButton("Spin");
	JButton cancelBet = new JButton("Cancel Bet");
	String[] betTypes = { "No Bet", "Coin 1", "Coin 2", "Both" };
	JComboBox<?> betTypeComboBox = new JComboBox<Object>(betTypes);

	public PlayerToolBar(JFrame frame, GameEngineImpl gameEngine, StatusBar pointsBalance, CoinPanel coinPanel,
			SummaryPanel summary)
	{
		// add player controller and actionlistener added to the button
		AddPlayerController addPlayerController = new AddPlayerController(gameEngine, playerSelectorComboBox, summary);
		addPlayer.addActionListener(addPlayerController);

		// Remove player controller instantiated and added to remove player button
		RemovePlayerController RemovePlayerController = new RemovePlayerController(gameEngine, playerSelectorComboBox);
		removePlayer.addActionListener(RemovePlayerController);

		// adds controller to placebet button
		PlaceBetController placeBetController = new PlaceBetController(gameEngine, betAmount, playerSelectorComboBox,
				betTypeComboBox, summary);
		placeBet.addActionListener(placeBetController);

		// combobox controller is added to combobox
		ComboBoxSelectorController ComboBoxController = new ComboBoxSelectorController(gameEngine, pointsBalance,
				coinPanel, playerSelectorComboBox, placeBetController);
		playerSelectorComboBox.addItemListener(ComboBoxController);

		// Spin button controller added to button
		SpinPlayerController spin = new SpinPlayerController(gameEngine, summary);
		spinPlayer.addActionListener(spin);

		// Bet cancelled controller added
		CancelBetController cancelBetController = new CancelBetController(gameEngine, playerSelectorComboBox);
		cancelBet.addActionListener(cancelBetController);

		// adding default combobox variabel
		playerSelectorComboBox.addItem("Please Select");
		playerSelectorComboBox.addItem("Spinner");

		// added stuff to toolbar
		toolBar.add(addPlayer);
		toolBar.add(removePlayer);
		toolBar.add(playerSelectorComboBox);
		toolBar.add(betTypeComboBox);
		toolBar.add(betAmount);
		toolBar.add(placeBet);
		toolBar.add(cancelBet);
		toolBar.add(spinPlayer);

		// adds toolbar to frame
		frame.add(toolBar, BorderLayout.PAGE_START);

	}

}
