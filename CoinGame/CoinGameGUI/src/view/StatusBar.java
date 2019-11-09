package view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.GameEngineImpl;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class StatusBar extends JPanel
{
//	Creats JLabel and collection for status bar
	JLabel pointsBalance = new JLabel(), resultOfSpin = new JLabel("Result: N/A");
	Collection<Player> players = new ArrayList<Player>();

	public StatusBar(JFrame frame, GameEngineImpl gameEngine)
		{
			add(pointsBalance);
			add(resultOfSpin);
			frame.add(this, BorderLayout.SOUTH);

		}

	public void setPointsBalance(String points)
		{
//		Set points balance
			pointsBalance.setText("Balance  " + points + "  ||  ");
		}



	public void setWinLabel(int points)
		{
			resultOfSpin.setText("Result: You Won " + points + " Points");
		}

	public void setlossLabel(int points)
		{
			resultOfSpin.setText("Result: You Lost " + points + " Points");

		}

	public void winOrLoses(int initialPoints, int finalPoints, Player player)
		{

			if (!(players.add(player)))
				{
					players.remove(player);
					players.add(player);
				} else
				{
					players.add(player);
				}

		}
	public void setSpinnerResultLabel() {
		resultOfSpin.setText("Result: N/A");
		pointsBalance.setText("Spinner  ||  ");


	}

}
