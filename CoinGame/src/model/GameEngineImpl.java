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
	private boolean isPlayer = false;
	

	@Override
	public void spinPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2) throws IllegalArgumentException
	{

		this.player = player;
		CoinPairImpl playerCoin = new CoinPairImpl();
		isPlayer = true;
		if (!(this.checkDelays(initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2,
				delayIncrement2)))
		{
			throw new IllegalArgumentException("ERROR - Invalid Delays");
		} else
		{
			if (players.contains(player))
			{	this.spin(playerCoin, initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2,
					delayIncrement2);
			}

		}
		isPlayer = false;

	}

	@Override
	public void spinSpinner(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2) throws IllegalArgumentException
	{
		// creates spinner coin to spin
		CoinPairImpl spinnerCoin = new CoinPairImpl();
		if (!(this.checkDelays(initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2,
				delayIncrement2)))
		{
			throw new IllegalArgumentException("ERROR - Invalid Delays");
		} else
		{
			this.spin(spinnerCoin, initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2,
					delayIncrement2);
		}
	}

	// checks to to see betType then calls appropriate method to apply results
	@Override
	public void applyBetResults(CoinPair spinnerResult)
	{
		for (Player player : players)
		{
			player.getBetType().applyWinLoss(player, spinnerResult);

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
		for (Player currentPlayers : players)
		{
			if (currentPlayers.getPlayerId() == id)
			{

				return currentPlayers;
			}

		}
		return null;
	}

	// loops through collections and removes player
	@Override
	public boolean removePlayer(Player player)
	{
		for (Player currentPlayers : players)
		{
			//checks to see if player already exusts.
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

	// method to spin to prevent code duplication
	private void spin(CoinPairImpl coinSpin, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2)
	{
		coinDelay1 = initialDelay1;
		coinDelay2 = initialDelay1;
		while ((coinDelay1 < finalDelay1) || (coinDelay2 < finalDelay1))
		{
			if (coinDelay1 < finalDelay1)
			{
				coinDelay1 += delayIncrement1;
				coinSpin.getCoin1().flip();
				try
				{
					// adds real life delays
					Thread.sleep(coinDelay1);
				} catch (InterruptedException e)
				{

					e.printStackTrace();
				}
				// call logger to update status of coin
				if (isPlayer == true)
				{
//					for (GameEngineCallback next : gameEngineCallback)
//					{
//						next.playerCoinUpdate(this.player, coinSpin.getCoin1(), this);
//					}
					gameEngineCallbackImpl.playerCoinUpdate(this.player, coinSpin.getCoin1(), this);
				} else
				{
					gameEngineCallbackImpl.spinnerCoinUpdate(coinSpin.getCoin1(), this);
				}
			}
			if (coinDelay2 < finalDelay1)
			{ 
				coinDelay2 += delayIncrement1;
				coinSpin.getCoin2().flip();
				try
				{
					// adds real life delayss
					Thread.sleep(coinDelay2);
				} catch (InterruptedException e)
				{

					e.printStackTrace();
				}
				// flips coin

				if (isPlayer == true)
				{
					gameEngineCallbackImpl.playerCoinUpdate(this.player, coinSpin.getCoin2(), this);
				} else
				{
					gameEngineCallbackImpl.spinnerCoinUpdate(coinSpin.getCoin2(), this);
				}
			}
		}
		if (isPlayer == true)
		{
			// sets result of the coin
			player.setResult(coinSpin);
			gameEngineCallbackImpl.playerResult(this.player, coinSpin, this);
			isPlayer = false;
		} else
		{
			this.applyBetResults(coinSpin);
			gameEngineCallbackImpl.spinnerResult(coinSpin, this);
		}

	}

	// checks that delays are appropriate
	private boolean checkDelays(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2)
	{
		if ((initialDelay1 < 0) || (finalDelay1 < 0) || (delayIncrement1 < 0) || (initialDelay2 < 0)
				|| (finalDelay2 < 0) || (delayIncrement2 < 0) || (finalDelay1 < initialDelay1)
				|| (finalDelay2 < initialDelay2) || (delayIncrement1 > (finalDelay1 - initialDelay1))
				|| (delayIncrement2 > (finalDelay2 - initialDelay2)))
		{
			return false;
		} else
		{
			return true;
		}
	}

}
