package utilities;

public class CarryInitialPoints
{
	int points;
	String id;
	
	
//	This class just holds inital points of each player before bet result. Decided this would be better then a hashmap
	public CarryInitialPoints(String id, int points)
	{
		this.points = points;
		this.id = id;
	}

	public String getPlayerId()
	{
		return id;
	}

	public int getPlayerPoints()
	{
		return points;
	}

	public void setPlayerPoints(int points)
	{
		this.points = points;
	}

}
