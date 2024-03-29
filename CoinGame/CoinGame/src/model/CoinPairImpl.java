
package model;


import model.interfaces.Coin;
import model.interfaces.CoinPair;

public class CoinPairImpl implements CoinPair
{
	CoinImpl coin1 = new CoinImpl(1);
	CoinImpl coin2 = new CoinImpl(2);

	// getter for coin 1
	public Coin getCoin1()
	{
		return coin1;
	}

	// coin 2 getter
	public Coin getCoin2()
	{
		return coin2;
	}

	// tostring for class
	@Override
	public String toString()
	{
		return coin1.toString() + " " + coin2.toString();
	}

	// checks generated hashcode, if they are equal, return true
	public boolean equals(CoinPair coinPair)
	{
		
		if ((this.getCoin1().equals(coinPair.getCoin1()))&&(this.getCoin2().equals(coinPair.getCoin2())))
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
		result = prime * result + ((coin1 == null) ? 0 : coin1.hashCode());
		result = prime * result + ((coin2 == null) ? 0 : coin2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		CoinPairImpl other = (CoinPairImpl) obj;
		if ((coin1.equals(other.coin1)) && (coin2.equals(other.coin2)))
		{
			return true;

		} else
		{
			return false;
		}
	}

}