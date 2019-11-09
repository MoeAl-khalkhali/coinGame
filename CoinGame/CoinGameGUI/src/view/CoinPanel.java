package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.interfaces.Coin;

@SuppressWarnings("serial")
public class CoinPanel extends JPanel
{

	private ImageIcon heads, tails;

	JFrame frame;
	Image heads1, tails1;
	int initialWidth = 1, initalHeight = 1, coinNumber = 1, ratio, ratioX;
	Graphics g;
	Coin coin1, coin2;
	float currentHeight, currentWidth;
	Boolean headsCheck1 = false, headsCheck2 = false, paintFirstComponent = true;

	public CoinPanel(JFrame frame)
	{
		// get width when coinPanel is instantiated
		initialWidth = frame.getWidth();
		initalHeight = frame.getHeight();
		this.frame = frame;
		
		//Create image icons
		heads = new ImageIcon("img/heads.png");
		tails = new ImageIcon("img/tails.png");
		heads1 = heads.getImage();
		tails1 = tails.getImage();
		
		//set size of panel
		setPreferredSize(new Dimension(400, 400));
		frame.add(this);
		repaint();

	}

	public void repaint(Coin coin, int coinNumber)
	{
		if (coinNumber == 1)
		{
			this.coin1 = coin;
		}
		if (coinNumber == 2)
		{
			this.coin2 = coin;
		}
		this.coinNumber = coinNumber;
		super.repaint();
	}

	public void repaint2(Coin coin1, Coin coin2)
	{
		this.coin1 = coin1;
		this.coin2 = coin2;
		super.repaint();
	}

	@Override
	public void paintComponent(Graphics g)
	{

		// get current width/height and new width/height to work out ratio
		super.paintComponent(g);
		float getWidthRatio = 1, getHeightRatio = 1;
		 currentHeight = frame.getHeight();
		 currentWidth = frame.getWidth();

		// check if window is resized, if so work out wdith ratio and height

		getWidthRatio = currentWidth / initialWidth;

		getHeightRatio = currentHeight / initalHeight;

		// find out if width or height has been resized more to
		if (getWidthRatio < getHeightRatio)
		{
			ratio = (int) (320 * getWidthRatio);
			ratioX = (int) (300 * getWidthRatio);
		} else
		{
			ratio = (int) (320 * getHeightRatio);
			ratioX = (int) (300 * getHeightRatio);

		}

		// draw the initial coins before its flipped
		if (paintFirstComponent)
		{
			g.drawImage(heads1, 0, 0, ratio, ratio, this);
			g.drawImage(heads1, ratioX, 0, ratio, ratio, this);
		}

		if ((coin1 != null))
		{
			paintFirstComponent = false;

			if (coin1.getFace().toString().equals("HEADS"))
			{
				headsCheck1 = true;

				if (headsCheck2)
				{
					g.drawImage(heads1, 0, 0, ratio, ratio, this);
					g.drawImage(heads1, ratioX, 0, ratio, ratio, this);
				} else
				{
					g.drawImage(heads1, 0, 0, ratio, ratio, this);
					g.drawImage(tails1, ratioX, 0, ratio, ratio, this);
				}

			} else
			{
				headsCheck1 = false;
				if (headsCheck2)
				{
					g.drawImage(tails1, 0, 0, ratio, ratio, this);

					g.drawImage(heads1, ratioX, 0, ratio, ratio, this);
				} else
				{
					g.drawImage(tails1, 0, 0, ratio, ratio, this);

					g.drawImage(tails1, ratioX, 0, ratio, ratio, this);
				}
			}

		}

		if ((coin2 != null))
		{
			if (coin2.getFace().toString().equals("HEADS"))
			{
				headsCheck2 = true;
				if (headsCheck1)
				{
					g.drawImage(heads1, 0, 0, ratio, ratio, this);
					g.drawImage(heads1, ratioX, 0, ratio, ratio, this);

				} else
				{
					g.drawImage(tails1, 0, 0, ratio, ratio, this);
					g.drawImage(heads1, ratioX, 0, ratio, ratio, this);
				}

			} else
			{
				headsCheck2 = false;

				if (headsCheck1)
				{
					g.drawImage(heads1, 0, 0, ratio, ratio, this);
					g.drawImage(tails1, ratioX, 0, ratio, ratio, this);

				} else
				{
					g.drawImage(tails1, 0, 0, ratio, ratio, this);
					g.drawImage(tails1, ratioX, 0, ratio, ratio, this);
				}
			}

		}

	}
}
