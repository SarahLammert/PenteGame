import java.util.ArrayList;
import java.util.Collections;

public class Computer
{
	public static final int WIN = 150;
	public static final int OPP_FOUR_IN_A_ROW_GUARDED = 125;
	public static final int OPP_BLOCKING_OF_HIGH_IMPORTANCE = 100;
	public static final int OPP_THREE_IN_A_ROW_UNGUARDED = 40;
	public static final int MY_THREE_IN_A_ROW_UNGUARDED = 30;
	public static final int OPP_TWO_IN_A_ROW_UNGUARDED = 20;
	public static final int MY_CAPTURE = 10;
	public static final int MY_TWO_IN_A_ROW_GUARDED = 5;
	public static final int MY_TWO_IN_A_ROW_UNGUARDED = 4;
	public static final int MY_THREE_IN_A_ROW_GUARDED = 3;
	public static final int OPP_THREE_IN_A_ROW_GUARDED = 2;
	public static final int OPP_ONE_IN_A_ROW_UNGUARDED = 1;

	PenteGameBoard myGame;
	int myStone;

	ArrayList<ComputerMove> allMoves = new ArrayList<ComputerMove>();

	public Computer(PenteGameBoard gb, int stoneColor)
	{
		myStone = stoneColor;
		myGame = gb;
	}

	public void sortAllPriorities()
	{
		Collections.sort(allMoves);
	}

	public int[] getComputerMove()
	{
		int[] newMove = new int[2];

		newMove[0] = -1;
		newMove[1] = -1;

		allMoves.clear();

		findAllMoves();
		sortAllPriorities();
		//printPriorities();

		if(allMoves.size() > 0)
		{
			ComputerMove ourMove = allMoves.get(0);
			if(ourMove.getPriority() == 1)
			{
				ourMove = allMoves.get((int)(allMoves.size()*(Math.random())));
			}
			System.out.println(ourMove.getPriority());
			newMove[0] = ourMove.getCol();
			newMove[1] = ourMove.getRow();
		}
		else
		{
			newMove = generateRandomMove();
			if(myGame.getTurn() == 3)
			{
				while(myGame.getBoard()[newMove[0]][newMove[1]].getIsInner())
				{
					newMove = generateRandomMove();
				}
			}
		}

		try
		{
			sleepForAMove();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		System.out.println(newMove[0] + " " + newMove[1]);
		return newMove;
	}
	public void findAllMoves()
	{
		for(int col = 0; col < PenteGameBoard.NUM_SQUARES_SIDE; col++ )
		{ 
			for(int row = 0; row < PenteGameBoard.NUM_SQUARES_SIDE; row++)
			{
				for(int rL = -1; rL <= 1; rL++)
				{
					for(int uD = -1; uD <= 1; uD++)
					{
						try
						{
							if(myGame.getBoard()[col][row].getState() == PenteGameBoard.EMPTY)
							{
								if(myGame.getBoard()[col + rL][row + uD].getState() == myStone * -1)
								{
									if(myGame.getBoard()[col + (rL * 2)][row + (uD * 2)].getState() == myStone * -1)
									{
										if(myGame.getBoard()[col + (rL * 3)][row + (uD * 3)].getState() == PenteGameBoard.EMPTY)
										{
											setAnyMove(col + (rL * 3), row + (uD * 3), OPP_TWO_IN_A_ROW_UNGUARDED);
										}
										else if(myGame.getBoard()[col + (rL * 3)][row + (uD * 3)].getState() == myStone * -1)
										{
											if(myGame.getBoard()[col + (rL * 4)][row + (uD * 4)].getState() == PenteGameBoard.EMPTY)
											{
												setAnyMove(col + (rL * 4), row + (uD * 4), OPP_THREE_IN_A_ROW_UNGUARDED);
											}
										}
									}
								}
								else if(myGame.getBoard()[col + rL][row + uD].getState() == myStone)
								{
									if(myGame.getBoard()[col + (rL * 2)][row + (uD * 2)].getState() == myStone)
									{
										if(myGame.getBoard()[col + (rL * 3)][row + (uD * 3)].getState() == PenteGameBoard.EMPTY)
										{
											setAnyMove(col + (rL * 3), row + (uD * 3), MY_TWO_IN_A_ROW_UNGUARDED);
										}
										else if(myGame.getBoard()[col + (rL * 3)][row + (uD * 3)].getState() == myStone)
										{
											if(myGame.getBoard()[col + (rL * 4)][row + (uD * 4)].getState() == PenteGameBoard.EMPTY)
											{
												setAnyMove(col + (rL * 4), row + (uD * 4), MY_THREE_IN_A_ROW_UNGUARDED);
											}
										}
									}
								}
							}
							else if(myGame.getBoard()[col][row].getState() == myStone)
							{
								if(myGame.getBoard()[col + rL][row + uD].getState() == myStone * -1)
								{
									if(myGame.getBoard()[col + (rL * 2)][row + (uD * 2)].getState() == myStone * -1)
									{
										if(myGame.getBoard()[col + (rL * 3)][row + (uD * 3)].getState() == PenteGameBoard.EMPTY)
										{
											setAnyMove(col + (rL * 3), row + (uD * 3), MY_CAPTURE);
										}
										else if(myGame.getBoard()[col + (rL * 3)][row + (uD * 3)].getState() == myStone * -1)
										{
											if(myGame.getBoard()[col + (rL * 4)][row + (uD * 4)].getState() == PenteGameBoard.EMPTY)
											{
												setAnyMove(col + (rL * 4), row + (uD * 4), OPP_THREE_IN_A_ROW_GUARDED);
											}
											else if(myGame.getBoard()[col + (rL * 4)][row + (uD * 4)].getState() == myStone * -1)
											{
												if(myGame.getBoard()[col + (rL * 5)][row + (uD * 5)].getState() == PenteGameBoard.EMPTY)
												{
													setAnyMove(col + (rL * 5), row + (uD * 5), OPP_FOUR_IN_A_ROW_GUARDED);
												}
											}
										}
									}
								}
								else if(myGame.getBoard()[col + rL][row + uD].getState() == myStone)
								{
									if(myGame.getBoard()[col + (rL * 2)][row + (uD * 2)].getState() == myStone)
									{
										if(myGame.getBoard()[col + (rL * 3)][row + (uD * 3)].getState() == myStone)
										{
											if(myGame.getBoard()[col + (rL * 4)][row + (uD * 4)].getState() == PenteGameBoard.EMPTY)
											{
												setAnyMove(col + (rL * 4), row + (uD * 4), WIN);
											}
										}
									}
								}
								else if(myGame.getBoard()[col + rL][row + uD].getState() == PenteGameBoard.EMPTY)
								{
									if(myGame.getBoard()[col + (rL * 2)][row + (uD * 2)].getState() == myStone)
									{
										if(myGame.getBoard()[col + (rL * 3)][row + (uD * 3)].getState() == myStone)
										{
											if(myGame.getBoard()[col + (rL * 4)][row + (uD * 4)].getState() == myStone)
											{
												setAnyMove(col + rL, row + uD, WIN);
											}
										}
									}
								}
							}
							else if(myGame.getBoard()[col][row].getState() == myStone * -1)
							{
								if(myGame.getBoard()[col + rL][row + uD].getState() == PenteGameBoard.EMPTY)
								{
									if(myGame.getBoard()[col + (rL * 2)][row + (uD * 2)].getState() == PenteGameBoard.EMPTY)
									{
										setAnyMove(col + rL, row + uD, OPP_ONE_IN_A_ROW_UNGUARDED);
									}
								}
								else if(myGame.getBoard()[col + rL][row + uD].getState() == myStone * -1)
								{
									if(myGame.getBoard()[col + (rL * 2)][row + (uD * 2)].getState() == PenteGameBoard.EMPTY)
									{
										if(myGame.getBoard()[col + (rL * 3)][row + (uD * 3)].getState() == myStone * -1)
										{
											if(myGame.getBoard()[col + (rL * 4)][row + (uD * 4)].getState() == myStone * -1)
											{
												setAnyMove(col + (rL * 2), row + (uD * 2), OPP_BLOCKING_OF_HIGH_IMPORTANCE);
											}
										}
									}
								}
								else if(myGame.getBoard()[col + rL][row + uD].getState() == myStone)
								{
									if(myGame.getBoard()[col + (rL * 2)][row + (uD * 2)].getState() == myStone)
									{
										if(myGame.getBoard()[col + (rL * 3)][row + (uD * 3)].getState() == PenteGameBoard.EMPTY)
										{
											setAnyMove(col + (rL * 3), row + (uD * 3), MY_TWO_IN_A_ROW_GUARDED);
										}
										else if(myGame.getBoard()[col + (rL * 3)][row + (uD * 3)].getState() == myStone)
										{
											if(myGame.getBoard()[col + (rL * 4)][row + (uD * 4)].getState() == PenteGameBoard.EMPTY)
											{
												setAnyMove(col + (rL * 4), row + (uD * 4), MY_THREE_IN_A_ROW_GUARDED);
											}
										}
									}
								}
								
							}
						}
						catch (ArrayIndexOutOfBoundsException e)
						{	
						}
					}
				}
			}
		}
	}
	public void setAnyMove(int c, int r, int p)
	{
		if(myGame.getTurn() != 3)
		{
			ComputerMove nM = new ComputerMove();
			nM.setCol(c);
			nM.setRow(r);
			nM.setPriority(p);
			if(myGame.getCaptures(myStone) == 8 && p == MY_CAPTURE)
			{
				nM.setPriority(WIN);
			}
			if(myGame.getCaptures(myStone) == 6 && p == MY_CAPTURE)
			{
				nM.setPriority(p + 20);
			}
			if(myGame.getCaptures(myStone * -1) == 6 && p == MY_TWO_IN_A_ROW_GUARDED)
			{
				nM.setPriority(p + 20);
			}
			if(myGame.getCaptures(myStone * -1) == 8 && p == MY_TWO_IN_A_ROW_GUARDED)
			{
				nM.setPriority(p + 40);
			}
			allMoves.add(nM);
		}
		else
		{
			if(myGame.getBoardSquare(c, r).getIsInner() == false)
			{
				ComputerMove nM = new ComputerMove();
				nM.setCol(c);
				nM.setRow(r);
				nM.setPriority(p);
				if(myGame.getCaptures(myStone) == 8 && p == MY_CAPTURE)
				{
					nM.setPriority(WIN);
				}
				if(myGame.getCaptures(myStone) == 6 && p == MY_CAPTURE)
				{
					nM.setPriority(p + 20);
				}
				if(myGame.getCaptures(myStone * -1) == 6 && p == MY_TWO_IN_A_ROW_GUARDED)
				{
					nM.setPriority(p + 20);
				}
				if(myGame.getCaptures(myStone * -1) == 8 && p == MY_TWO_IN_A_ROW_GUARDED)
				{
					nM.setPriority(p + 20);
				}
				allMoves.add(nM);
			}
		}
	}
	public int[] generateRandomMove()
	{
		int[] move = new int[2];

		boolean done = false;

		int newC, newR;
		do
		{
			newC = (int)(Math.random() * PenteGameBoard.NUM_SQUARES_SIDE);
			newR = (int)(Math.random() * PenteGameBoard.NUM_SQUARES_SIDE);
			move[0] = newC;
			move[1] = newR;
			done = true;
		}
		while(!done);
		return move;
	}
	public void printPriorities()
	{
		for(ComputerMove m: allMoves)
		{
			System.out.println(m);
		}
		System.out.println("");
	}
	public void sleepForAMove() throws InterruptedException
	{
		Thread currThread = Thread.currentThread();
		Thread.sleep(PenteGameBoard.SLEEP_TIME);
	}
}