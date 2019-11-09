package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import model.enumeration.BetType;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameEngineCallbackImpl;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine
{
	Player player;
	Boolean done1 = false, done2 = false;
	public CoinPairImpl spinnerCoin;
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
			{

				GameEngineImpl.this.spin(playerCoin, initialDelay1, finalDelay1, delayIncrement1, initialDelay2,
						finalDelay2, delayIncrement2);

			}
			isPlayer = false;
		}

	}

	@Override
	public void spinSpinner(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2) throws IllegalArgumentException
	{
		// creates spinner coin to spin
		this.spinnerCoin = new CoinPairImpl();
		if (!(this.checkDelays(initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2,
				delayIncrement2)))
		{
			throw new IllegalArgumentException("ERROR - Invalid Delays");
		} else
		{

			GameEngineImpl.this.spin(spinnerCoin, initialDelay1, finalDelay1, delayIncrement1, initialDelay2,
					finalDelay2, delayIncrement2);
			;

		}
	}

	// checks to to see betType then calls appropriate method to apply results
	@Override
	public void applyBetResults(CoinPair spinnerResult)
	{
		for (Player player : players)
		{
			if (player.getBetType() != null)
			{
				player.getBetType().applyWinLoss(player, spinnerResult);
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
		for (Player currentPlayers : players)
		{
			if (currentPlayers.getPlayerId().equals(id))
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
			// checks to see if player already exusts.
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
			betType = BetType.NO_BET;
			player.setBetType(betType);
			return false;
		}

	}

	// method to spin to prevent code duplication
	private void spin(CoinPairImpl coinSpin, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2)
	{
		int timeForThreads1 = (initialDelay1);
		int aggTime1 = 0;
		int timeForThreads2 = (initialDelay2);
		int aggTime2 = 0;
		coinDelay1 = initialDelay1;
		coinDelay2 = initialDelay2;
		new Thread()
		{
			@Override
			public void run()
			{
				// call long running GameEngine methods on separate thread

				while ((coinDelay1 < finalDelay1))
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
							for (GameEngineCallback next : gameEngineCallback)
							{
								next.playerCoinUpdate(player, coinSpin.getCoin1(), GameEngineImpl.this);
							}
						} else
						{
							for (GameEngineCallback next : gameEngineCallback)
							{
								next.spinnerCoinUpdate(coinSpin.getCoin1(), GameEngineImpl.this);
							}
						}
					}

				}
				done1 = true;
				try
				{
					this.join();
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

		new Thread()
		{
			@Override
			public void run()
			{
				// call long running GameEngine methods on separate thread

				while ((coinDelay2 < finalDelay2))
				{
					if (coinDelay2 < finalDelay2)
					{
						coinDelay2 += delayIncrement2;
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
							for (GameEngineCallback next : gameEngineCallback)
							{
								next.playerCoinUpdate(player, coinSpin.getCoin2(), GameEngineImpl.this);
							}
						} else
						{
							for (GameEngineCallback next : gameEngineCallback)
							{
								next.spinnerCoinUpdate(coinSpin.getCoin2(), GameEngineImpl.this);
							}

						}

					}
				}
				done2 = true;
				try
				{
					this.join();
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
		while (timeForThreads1 < finalDelay1)
		{
			timeForThreads1 += delayIncrement1;
			aggTime1 += timeForThreads1;
		}
		while (timeForThreads2 < finalDelay2)
		{
			timeForThreads2 += delayIncrement2;
			aggTime2 += timeForThreads1;
		}

		if (aggTime2 >= aggTime1)
		{
			try
			{
				Thread.sleep(aggTime2);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else
		{
			try
			{
				Thread.sleep(aggTime1);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (isPlayer == true)
		{
			// sets result of the coin
			player.setResult(coinSpin);
			for (GameEngineCallback next : gameEngineCallback)
			{
				next.playerResult(this.player, coinSpin, this);
			}
			isPlayer = false;
		} else
		{
			this.applyBetResults(coinSpin);
			for (GameEngineCallback next : gameEngineCallback)
			{
				next.spinnerResult(coinSpin, this);
			}
		}
		done1 = false;
		done2 = false;

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
