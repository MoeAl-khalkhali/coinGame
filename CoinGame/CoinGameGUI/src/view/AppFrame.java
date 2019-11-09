package view;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import model.GameEngineImpl;

@SuppressWarnings("serial")
public class AppFrame extends JFrame
{
	@SuppressWarnings("unused")
	public AppFrame() throws IOException
	{
		super("Coin Game");
		setBounds(100, 100, 740, 500);

		// instantiates variable with relevent parmeters
		final GameEngineImpl gameEngine = new GameEngineImpl();
		StatusBar statusPanel = new StatusBar(this, gameEngine);
		FileMenuBar fileMenuBar = new FileMenuBar(this);
		CoinPanel coinPanel = new CoinPanel(this);
		SummaryPanel summary = new SummaryPanel(gameEngine, this);

		PlayerToolBar toolBar = new PlayerToolBar(this, gameEngine, statusPanel, coinPanel, summary);

		// adds and instantiates Callback and CallbackGUI
		gameEngine.addGameEngineCallback(
				new GameEngineCallbackGUI(this, toolBar, coinPanel, statusPanel, gameEngine, summary));
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());

		repaint();
		validate();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					new AppFrame();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
