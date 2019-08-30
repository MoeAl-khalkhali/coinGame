package model;

import java.util.Objects;
import model.enumeration.BetType;
import model.enumeration.CoinFace;
import model.interfaces.Coin;
import util.Helper;

public class CoinImpl implements Coin
{
	BetType bet;
	CoinFace coin;
	int number;

	// sets random face for each coin
	public CoinImpl(int number)
	{
		this.number = number;
		int min = 1;
		int max = 2;
		int faceChosen = (int) (Math.random() * ((max - min) + 1)) + min;

		if (number == 1)
		{
			bet = bet.COIN1;
			if (faceChosen == 1)
			{
				coin = coin.HEADS;
			} else
			{
				coin = coin.TAILS;
			}
		}

		if (number == 2)
		{
			bet = bet.COIN2;
			if (faceChosen == 1)
			{
				coin = coin.HEADS;

			} else
			{
				coin = coin.TAILS;
			}
		}

	}

	// returns if its coin 1 or coin 2 (returns just the number)
	public int getNumber()
	{
		if (bet == bet.COIN1)
		{
			return 1;
		} else
		{
			return 2;
		}

	}

	// returns coins face
	public CoinFace getFace()
	{

		return coin;
	}

	// flips the coins face
	public void flip()
	{
		if (coin == coin.HEADS)
		{

			coin = coin.TAILS;
		} else
		{
			coin = coin.HEADS;

		}

		return;

	}

	// checks to see if coins are equal by using the hashcode
	public boolean equals(Coin coin)
	{
		Object coinObj = coin;
		if (this.equals(coinObj))
		{
			return true;
		} else
		{
			return false;
		}
	}



	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coin == null) ? 0 : coin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		CoinImpl other = (CoinImpl) obj;
		if (coin != other.coin)
			return false;
		return true;
	}
	// hope
	// overidden
	// toString for the class
	@Override
	public String toString()
	{
		String oldFace = coin.name();
		String face = Helper.changeCase(oldFace);
		if (number == 1)
		{
			return "Coin 1: " + face + ",";
		} else
		{
			return "Coin 2: " + face;
		}
	}

}
