package model;

import model.enumeration.BetType;
import model.interfaces.CoinPair;
import model.interfaces.Player;

public class SimplePlayer implements Player
{
	BetType betType;
	String playerName, playerID;
	int bet, points;
	CoinPair coinPair;

	public SimplePlayer(String playerID, String playerName, int initialPoints)
	{
		this.playerID = playerID;
		this.playerName = playerName;
		this.points = initialPoints;
	}

	@Override
	public String getPlayerName()
	{
		
		return playerName;
	}
	
	//sets player name
	@Override
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;

	}

	@Override
	public int getPoints()
	{
		//returns points
		return points;
	}
	
	//sets points
	@Override
	public void setPoints(int points)
	{
		this.points = points;

	}
	
	//returns player id
	@Override
	public String getPlayerId()
	{
		
		return playerID;
	}
	
	//sets bet following given parameters.
	@Override
	public boolean setBet(int bet)
	{
		this.bet = bet;
		if ((points >= bet) && (bet > 0))
		{
			return true;
		} else
		{
			this.resetBet();
			return false;
		}
	}
	
	//returns bet value
	@Override
	public int getBet()
	{
		return bet;
	}
	
	//sets the bet type
	@Override
	public void setBetType(BetType betType)
	{
		this.betType = betType;

	}
	
	//gets bet type
	@Override
	public BetType getBetType()
	{
		return betType;
	}
	
	//resets bet to 0
	@Override
	public void resetBet()
	{
		bet = 0;

	}

	//gets results of coin pair
	@Override
	public CoinPair getResult()
	{
		
		return coinPair;
	}
	//sets results of coin pair
	@Override
	public void setResult(CoinPair coinPair)
	{
		this.coinPair = coinPair;

	}
	
	//to string for the class
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Player: id=");
		builder.append(playerID);
		builder.append(", name=");
		builder.append(playerName);
		builder.append(", bet=");
		builder.append(bet);
		builder.append(", betType=");
		builder.append(betType);
		builder.append(", points=");
		builder.append(points);
		builder.append("\n");
		return builder.toString();
	}
}
