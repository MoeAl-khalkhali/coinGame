package view;

import util.Helper;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.SimplePlayer;
import model.interfaces.Coin;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

/**
 * 
 * Skeleton implementation of GameEngineCallback showing Java logging behaviour
 * 
 * @author Caspar Ryan
 * @see view.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback
{
	Helper helper = new Helper();
	private static final Logger logger = Logger.getLogger(GameEngineCallback.class.getName());

	public GameEngineCallbackImpl()
	{

		// NOTE need to also set the console to FINE in
		// %JRE_HOME%\lib\logging.properties
		logger.setLevel(Level.FINE);
	}

	public void playerCoinUpdate(Player player, Coin coin, GameEngine engine)
	{
		String oldFace = coin.getFace().toString();
		String face = helper.changeCase(oldFace);
		//logs coin update whilst spinning
		logger.log(Level.FINE, player.getPlayerName() + " Coin " + coin.getNumber() + " flipped to " + face);
	}

	public void playerResult(Player player, CoinPair coinPair, GameEngine engine)
	{
		
		logger.log(Level.INFO, player.getPlayerName() + " final result=" + coinPair.toString());
		
	}

	@Override
	public void spinnerCoinUpdate(Coin coin, GameEngine engine)
	{
		//logs coin update whilst spinning
		String oldFace = coin.getFace().toString();
		String face = helper.changeCase(oldFace);
		logger.log(Level.FINE, "Spinner " + " coin " + coin.getNumber() + " flipped to " + face);

	}

	@Override
	public void spinnerResult(CoinPair coinPair, GameEngine engine)
	{
		//gives final results
		logger.log(Level.INFO, "Spinner, " + "final result=" + coinPair.toString());
		logger.log(Level.INFO, "Final Player Results");
		//iterates over players and gets their results
		for (Player player : engine.getAllPlayers())
		{
			logger.log(Level.INFO, player.toString() + "RESULT .. " + player.getResult().toString());
		}

	}
}
