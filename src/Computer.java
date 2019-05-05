import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Computer
{
	public static final int OFFENSE = 1;
	public static final int DEFENSE = -1;
	public static final int ONE_IN_ROW_DEF = 1;
	public static final int TWO_IN_ROW_DEF = 2;
	public static final int TWO_IN_ROW_OPEN = 3;
	public static final int TWO_IN_ROW_CAP= 6;
	
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
		int [] newMove = new int[2];
		newMove[0] = -1;
		newMove[1] = -1;
		dMoves.clear();
		oMoves.clear();
		
		findDefMoves();
		sortDefPriorities();
		findOffMoves();
		
		if(dMoves.size() > 0)
		{
			int whichOne = (int)(Math.random() * dMoves.size());
			ComputerMove ourMove = dMoves.get(whichOne);
			newMove[0] = ourMove.getCol();
			newMove[1] = ourMove.getRow();
		}
		else
		{
			newMove = generateRandomMove();
		}
		
		try
		{
			sleepForAMove();
		}
		catch(InterruptedException e)
		{
		}
		
		return newMove;
	}
	
	public void findDefMoves()
	{
		for(int col = 0; col < PenteGameBoard.NUM_SQUARES_SIDE; col++)
		{
			for(int row = 0; row < PenteGameBoard.NUM_SQUARES_SIDE; row++)
			{
				if(myGame.getBoard()[col][row].getState() == stoneColor*-1)
				{
					findOneDef(col, row);
					findTwoDef(col, row);
					findThreeDef(col, row);
					findFourDef(col, row);
				}
			}
		}
	}
	public void setDefMove(int c, int r, int p)
	{
		ComputerMove newMove = new ComputerMove();
		newMove.setCol(c);
		newMove.setRow(r);
		newMove.setPriority(p);
		newMove.setMoveType(DEFENSE);
		dMoves.add(newMove);
	}
	
	public void sortDefPriorities()
	{
		Comparator<ComputerMove> compareByPriority = (ComputerMove o1, ComputerMove o2) -> o1.getPriorityInt().compareTo(o2.getPriorityInt());
	}
	public void findOneDef(int c, int r)
	{
		for(int rL = -1; rL <= 1; rL++)
		{
			for(int uD = -1; uD <=1; uD++)
			{	
				try
				{
					if(myGame.getBoard()[c+rL][r+uD].getState() == PenteGameBoard.EMPTY)
					{
						setDefMove(c+rL, r+uD, ONE_IN_ROW_DEF);
					}
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Off the board at col: " + c + " row : " + r);
				}
				
			}
		}
	}
	
	public void findTwoDef(int c, int r)
	{
		for(int rL = -1; rL <= 1; rL++)
		{
			for(int uD = -1; uD <=1; uD++)
			{	
				try
				{
					if(myGame.getBoard()[c+rL][r+uD].getState() == stoneColor*-1)
					{
						if(myGame.getBoard()[c+(rL*2)][r+(uD*2)].getState() == PenteGameBoard.EMPTY)
						{
							if(isOnBoard(c+rL, r+uD))
							{
								setDefMove(c + (rL*2), r + (uD*2), TWO_IN_ROW_DEF);
							}
							else if(myGame.getBoard()[c-rL][r-uD].getState() == PenteGameBoard.EMPTY)
							{
								setDefMove(c + (rL*2), r + (uD*2), TWO_IN_ROW_OPEN);
							}
							else if(myGame.getBoard()[c-rL][r-uD].getState() == stoneColor)
							{
								setDefMove(c + (rL*2), r + (uD*2), TWO_IN_ROW_CAP);
							}
						}
					}
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Off the board at col: " + c + " row : " + r);
				}
				
			}
		}
	}
	
	public void findThreeDef(int c, int r)
	{
		
	}
	
	public void findFourDef(int c, int r)
	{
		
	}
	
	public void findOffMoves()
	{
		
	}
	
	public boolean isOnBoard(int c, int r)
	{
		boolean isOn = false;
		if(c >= 0 && c < PenteGameBoard.NUM_SQUARES_SIDE)
		{
			if(r >= 0 && r < PenteGameBoard.NUM_SQUARES_SIDE)
			{
				isOn = true;
			}
		}
		return isOn;
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
