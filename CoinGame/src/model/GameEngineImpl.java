package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import model.enumeration.BetType;
import model.interfaces.Coin;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameEngineCallbackImpl;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine
{
	Player player;
	int bet, coinDelay1, coinDelay2;
	String id;
	BetType betType;
	Collection<Player> players = new ArrayList<Player>();
	Collection<GameEngineCallback> gameEngineCallback = new ArrayList<GameEngineCallback>();
	GameEngineCallbackImpl gameEngineCallbackImpl = new GameEngineCallbackImpl();

	@Override
	public void spinPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2) throws IllegalArgumentException
	{
		this.player = player;
		coinDelay1 = initialDelay1;
		coinDelay2 = initialDelay1;
		CoinPairImpl playerCoin = new CoinPairImpl();
		// checks for delay errors, throws error if their is
		if ((delayIncrement1 > (finalDelay1 - initialDelay1)) || (delayIncrement2 > (finalDelay2 - initialDelay2))
				|| (initialDelay1 < 0) || (initialDelay2 < 0) || (delayIncrement1 <= 0) || (delayIncrement2 <= 0)
				|| (finalDelay1 < 0) || (finalDelay2 < 0))
		{
			throw new IllegalArgumentException("ERROR - Invalid Delays Inputted");
		}
		// while loop adding increment to coin
		while ((coinDelay1 < finalDelay1) || (coinDelay2 < finalDelay1))
		{
			if (coinDelay1 < finalDelay1)
			{
				coinDelay1 += delayIncrement1;
				playerCoin.getCoin1().flip();
				try
				{
					// adds real life delays
					Thread.sleep(coinDelay1);
				} catch (InterruptedException e)
				{

					e.printStackTrace();
				}
				// call logger to update status of coin
				gameEngineCallbackImpl.playerCoinUpdate(player, playerCoin.getCoin1(), this);
			}
			if (coinDelay2 < finalDelay1)
			{
				coinDelay2 += delayIncrement1;
				try
				{
					// adds real life delays
					Thread.sleep(coinDelay2);
				} catch (InterruptedException e)
				{

					e.printStackTrace();
				}
				// flips coin
				playerCoin.getCoin2().flip();
				gameEngineCallbackImpl.playerCoinUpdate(player, playerCoin.getCoin2(), this);
			}
		}

		// sets result of the coin
		player.setResult(playerCoin);
		gameEngineCallbackImpl.playerResult(player, playerCoin, this);

	}

	@Override
	public void spinSpinner(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2) throws IllegalArgumentException
	{
		// creates spinner coin to spin
		CoinPairImpl spinnerCoin = new CoinPairImpl();
		coinDelay1 = initialDelay1;
		coinDelay2 = initialDelay1;

		// checks for delay input error
		if ((delayIncrement1 > (finalDelay1 - initialDelay1)) || (delayIncrement2 > (finalDelay2 - initialDelay2))
				|| (initialDelay1 < 0) || (initialDelay2 < 0) || (delayIncrement1 < 0) || (delayIncrement2 < 0)
				|| (finalDelay1 < 0) || (finalDelay2 < 0))
		{
			throw new IllegalArgumentException("ERROR");
		}

		// while loop for delays
		while ((coinDelay1 < finalDelay1) || (coinDelay2 < finalDelay1))
		{
			if (coinDelay1 < finalDelay1)
			{
				coinDelay1 += delayIncrement1;
				try
				{
					Thread.sleep(coinDelay1);
				} catch (InterruptedException e)
				{

					e.printStackTrace();
				}
				spinnerCoin.getCoin1().flip();
				gameEngineCallbackImpl.spinnerCoinUpdate(spinnerCoin.getCoin1(), this);
			}
			if (coinDelay2 < finalDelay1)
			{
				coinDelay2 += delayIncrement1;
				try
				{
					Thread.sleep(coinDelay2);
				} catch (InterruptedException e)
				{

					e.printStackTrace();
				}
				spinnerCoin.getCoin2().flip();
				gameEngineCallbackImpl.spinnerCoinUpdate(spinnerCoin.getCoin2(), this);
			}
		}
		// applies bet results and call logger to log results
		this.applyBetResults(spinnerCoin);
		gameEngineCallbackImpl.spinnerResult(spinnerCoin, this);

	}

	// checks to to see betType then calls appropriate method to apply results
	@Override
	public void applyBetResults(CoinPair spinnerResult)
	{
		for (Player player : players)
		{
			if (player.getBetType().equals(betType.COIN1))
			{
				betType.COIN1.applyWinLoss(player, spinnerResult);
			}
			if (player.getBetType().equals(betType.COIN2))
			{
				betType.COIN2.applyWinLoss(player, spinnerResult);
			}
			if (player.getBetType().equals(betType.BOTH))
			{
				betType.BOTH.applyWinLoss(player, spinnerResult);
			}
		}
	}

	// checks if player exists and adds player, if it exists, it overides the player
	@Override
	public void addPlayer(Player player)
	{
		for (Player currentPlayers : players)
		{
			if (currentPlayers.getPlayerId().equals(player.getPlayerId()))
			{
				currentPlayers = player;
				return;
			}
		}
		players.add(player);
	}

	// returns player id
	@Override
	public Player getPlayer(String id)
	{
		if (player.getPlayerId() == id)
		{
			return player;

		}
		return null;
	}

	// loops through collections and removes player
	@Override
	public boolean removePlayer(Player player)
	{
		for (Player currentPlayers : players)
		{
			if (currentPlayers.equals(player))
			{
				players.remove(player);
				return true;
			}

		}
		return false;
	}

	// add GameEngineCallback
	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		this.gameEngineCallback.add(gameEngineCallback);

	}

	// removes GameEngineCallback
	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		if (this.gameEngineCallback.contains(gameEngineCallback))
		{
			this.gameEngineCallback.remove(gameEngineCallback);
			return true;
		} else
		{
			return false;
		}
	}

	// unmodifiable collection
	@Override
	public Collection<Player> getAllPlayers()
	{
		return Collections.unmodifiableCollection(players);
	}

	// places bet for player
	@Override
	public boolean placeBet(Player player, int bet, BetType betType)
	{
		this.player = player;
		this.bet = bet;
		this.betType = betType;
		// checks if setBet is true, if true, bettype is set
		if (player.setBet(bet))
		{
			player.setBetType(betType);
			return true;

		} else
		{
			// if false then no bet
			betType = betType.NO_BET;
			player.setBetType(betType);
			return false;
		}

	}

}
