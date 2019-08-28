package model;

import java.util.Objects;

import model.interfaces.Coin;
import model.interfaces.CoinPair;

public class CoinPairImpl implements CoinPair
{
	CoinImpl coin1 = new CoinImpl(1);
	CoinImpl coin2 = new CoinImpl(2);
	
	//getter for coin 1
	public Coin getCoin1()
	{
		return coin1;
	}

	// coin 2 getter
	public Coin getCoin2()
	{
		return coin2;
	}
	
	//tostring for class
	@Override
	public String toString()
	{
		return coin1.toString() + " " + coin2.toString();
	}
	
	//checks generated hashcode, if they are equal, return true
	public boolean equals(CoinPair coinPair)
	{
		if (this.hashCode() == coinPair.hashCode())
		{
			return true;
		} else
		{
			return false;
		}
	}

	//generates hascode for coin pair
	@Override
	public int hashCode()
	{
		return Objects.hashCode(this.toString());
	}
}
