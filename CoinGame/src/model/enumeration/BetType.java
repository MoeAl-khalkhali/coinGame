package model.enumeration;

import model.interfaces.CoinPair;
import model.interfaces.Player;

/**
 * Provided enum type for Further Programming representing a type of Bet<br>
 * See inline comments for where you need to provide additional code
 * 
 * @author Caspar Ryan
 * 
 */
public enum BetType
{
	COIN1
	{
		//checks for winner and applies win/loss
		@Override
		public void applyWinLoss(Player player, CoinPair spinnerResult)
		{
			if (player.getResult().getCoin1().equals(spinnerResult.getCoin1()))
			{
				player.setPoints(player.getPoints() + player.getBet());
			} else
			{
				player.setPoints(player.getPoints() - player.getBet());

			}
		}
	},
	COIN2
	{
		//checks for winner and applies win/loss
		@Override
		public void applyWinLoss(Player player, CoinPair spinnerResult)
		{
			if (player.getResult().getCoin2().equals(spinnerResult.getCoin2()))
			{
				player.setPoints(player.getPoints() + player.getBet());
			} else
			{
				player.setPoints(player.getPoints() - player.getBet());

			}
		}

	},BOTH
	{
		//checks for winner and applies win/loss
		@Override
		public void applyWinLoss(Player player, CoinPair spinnerResult)
		{
			if (player.getResult().equals(spinnerResult))
			{
				player.setPoints(player.getPoints() + 2*(player.getBet()));
			} else
			{
<<<<<<< HEAD
				player.setPoints(player.getPoints() - player.getBet());
=======
				player.setPoints(player.getPoints() - (player.getBet()));
>>>>>>> refs/remotes/origin/master

			}

		}

	},
	//No bet so nothing occurs
	NO_BET
	{
		@Override
		public void applyWinLoss(Player player, CoinPair spinnerResult)
		{
			

		}
	};
	
	public abstract void applyWinLoss(Player player, CoinPair spinnerResult);
}