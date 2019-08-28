package model;

import java.util.Objects;

import model.enumeration.BetType;
import model.enumeration.CoinFace;
import model.interfaces.Coin;

public class CoinImpl implements Coin
{
	BetType bet;
	CoinFace coin;
	int number;

	//sets random face for each coin
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

	//returns if its coin 1 or coin 2 (returns just the number)
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

	//returns coins face
	public CoinFace getFace()
	{

		return coin;
	}

	//flips the coins face
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

	//checks to see if coins are equal by using the hascode
	public boolean equals(Coin coin)
	{
		if (this.hashCode() == coin.hashCode())
		{
			return true;
		} else
		{
			return false;
		}
	}

	// generates hash code for the coin
	@Override
	public int hashCode()
	{

		return Objects.hashCode(this.toString());
	}

	//toString for the class
	@Override
	public String toString()
	{
		if (number == 1)
		{
			return "Coin 1: " + this.coin + ",";
		} else
		{
			return "Coin 2: " + this.coin;
		}
	}

}
