import java.util.ArrayList;

public class Computer
{
	public static final int OFFENSE = 1;
	public static final int DEFENSE = -1;
	PenteGameBoard myGame;
	
	int stoneColor;
	
	ArrayList<ComputerMove> oMoves = new ArrayList<ComputerMove>();
	ArrayList<ComputerMove> dMoves = new ArrayList<ComputerMove>();
	
	public Computer(PenteGameBoard mG, int sC)
	{
		myGame = mG;
		stoneColor = sC;

	}
	public int [] getComputerMove()
	{
		int [] newMove;
		
		defMoves();
		offMoves();
		
		newMove = generateRandomMove();
		
		try
		{
			sleepForAMove();
		}
		catch(InterruptedException e)
		{
		}
		
		return newMove;
	}
	
	public void defMoves()
	{
		findOneDef();
		/*findTwoDef();
		findThreeDef();
		findFourDef();
		*/
		
	}
	public void offMoves()
	{
		/*findOneOff();
		findTwoOff();
		findThreeOff();
		findFourOff();
		*/
	}
	
	public void findOneDef()
	{
		
	}
	
	public int [] generateRandomMove()
	{
		int [] move = new int[2];
		
		boolean done = false;
		
		int newC, newR;
		do
		{
			newC = (int)(Math.random() * PenteGameBoard.NUM_SQUARES_SIDE);
			newR = (int)(Math.random() * PenteGameBoard.NUM_SQUARES_SIDE);
			if(myGame.getBoard()[newC][newR].getState() == PenteGameBoard.EMPTY)
			{
				done = true;
				move[0] = newC;
				move[1] = newR;
			}
			 
		}
		while(!done);
			
		return move;
	}
	public void sleepForAMove() throws InterruptedException
	{
		Thread.sleep(PenteGameBoard.SLEEP_TIME);
	}
}
