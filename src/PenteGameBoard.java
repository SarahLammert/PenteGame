//Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//Child of JPanel

public class PenteGameBoard extends JPanel implements MouseListener
{
	//Random
	private static final long serialVersionUID = 1L;
	
	//States of the squares
	public static final int EMPTY = 0;
	public static final int BLACKSTONE = 1;
	public static final int WHITESTONE = -1;
	public static final int NUM_SQUARES_SIDE = 19;
	public static final int AREA_OF_SQUARE = NUM_SQUARES_SIDE * NUM_SQUARES_SIDE;
	public static final int PLAYER1_TURN = 1;
	public static final int PLAYER2_TURN = -1;
	
	private int bWidth, bHeight;
	private int squareW, squareH;
	private JFrame myFrame;
	
	int playerTurn;
	boolean player1IsComputer = false;
	boolean player2IsComputer = false;
	String p1Name, p2Name;
	int stonesC;
	int count = 1;
	int capturedB = 0;
	int capturedW = 0;
	
	PenteBoardSquare [][] board;
	
	//Constructor with the parameters: width and height of the board
	public PenteGameBoard(int w, int h, JFrame f)
	{
		//Storing the parameters
		bWidth = w;
		bHeight = h;
		myFrame = f;
		
		//Setting the PenteGameboard
		this.setSize(bWidth, bHeight);
		this.setBackground(Color.CYAN);
		
		//Determining the size of the square width and height based on the number of squares of on the side
		squareW = bWidth/NUM_SQUARES_SIDE;
		squareH = bHeight/NUM_SQUARES_SIDE;
		
		addMouseListener(this);
		
		board = new PenteBoardSquare[NUM_SQUARES_SIDE][NUM_SQUARES_SIDE];
		
		for(int col = 0; col < NUM_SQUARES_SIDE; col++)
		{
			for(int row = 0; row < NUM_SQUARES_SIDE; row++)
			{
				board[col][row] = new PenteBoardSquare(squareW*col, squareH*row, squareW, squareH, col, row);
				if(col >= 7 && col <= 11 && row >= 7 && row <= 11)
				{
					board[col][row].setInner();
				}
			}
		}
		this.resetBoard();
	}
	
	//Method to draw the game board by overriding paintComponent
	public void paintComponent(Graphics g)
	{
		updateSizes();
		
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, bWidth, bHeight);
		
		for(int col = 0; col < NUM_SQUARES_SIDE; col++)
		{
			for(int row = 0; row < NUM_SQUARES_SIDE; row++)
			{
				board[col][row].drawMe(g);
			}
		}
	} 
	
	public void resetBoard()
	{
		for(int col = 0; col < NUM_SQUARES_SIDE; col++)
		{
			for(int row = 0; row < NUM_SQUARES_SIDE; row++)
			{
				board[col][row].setState(EMPTY);
			}
		}
	}
	
	public void startNewGame()
	{
		resetBoard();
		
		p1Name = JOptionPane.showInputDialog("Name of player 1 (or type 'c' for computer");
		if(p1Name.equals('c') || p1Name.equals("computer"))
		{
			player1IsComputer = true;
		}
		p2Name = JOptionPane.showInputDialog("Name of player 2 (or type 'c' for computer");
		if(p2Name.equals('c'))
		{
			player2IsComputer = true;
		}
		
		playerTurn = PLAYER1_TURN;
		this.board[NUM_SQUARES_SIDE/2][NUM_SQUARES_SIDE/2].setState(BLACKSTONE);
		
		changePlayerTurn();
		
		this.repaint();
	}
	
	public void changePlayerTurn()
	{
		playerTurn *= -1;
		count += 1;
	}
	
	
	
	private void updateSizes()
	{
		if(myFrame.getWidth() != bWidth || myFrame.getHeight() != bHeight + 20)
		{
			bWidth = myFrame.getWidth();
			bHeight = myFrame.getHeight()-20;
			
			squareW = bWidth/NUM_SQUARES_SIDE;
			squareH = bHeight/NUM_SQUARES_SIDE;
			
		}
	}
	
	public PenteBoardSquare findLoc(PenteBoardSquare[][] b, int xM, int yM)
	{
		int xLoc, yLoc;
		int j = 0;
		int i = 0;
		
		for(int col = 0; col < NUM_SQUARES_SIDE; col++)
		{
			for(int row = 0; row < NUM_SQUARES_SIDE; row++)
			{
				xLoc = b[col][row].getxLoc();
				yLoc = b[col][row].getyLoc();
				
				int sSquareWidth = b[col][row].getsWidth();
				int sSquareHeight = b[col][row].getsHeight();
				
				if(xM >= xLoc && xM <= (xLoc+sSquareWidth) && yM >= yLoc && yM <= (yLoc + sSquareHeight))
				{
					i = col;
					j = row;
				}
			}
		}
		
		return board[i][j];
		
	}
	
	public boolean open(PenteBoardSquare p)
	{
		if(p.getsState() == PenteGameBoard.EMPTY)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void checkForRemoval(PenteBoardSquare p)
	{
		stonesC = 0;
		int a = p.getsCol();
		int b = p.getsRow();
		int c = playerTurn;
		int d = playerTurn*-1;
		
		try
		{
			if(board[a-3][b-3].getsState() == c &&
			   board[a-2][b-2].getsState() == d &&
			   board[a-1][b-1].getsState() == d)
			{
			   stonesC += 2;
			   board[a-2][b-2].setsState(PenteGameBoard.EMPTY);
			   board[a-1][b-1].setsState(PenteGameBoard.EMPTY);
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a][b-3].getsState() == c &&
			   board[a][b-2].getsState() == d &&
			   board[a][b-1].getsState() == d)
			{
			   stonesC += 2;
			   board[a][b-2].setsState(PenteGameBoard.EMPTY);
			   board[a][b-1].setsState(PenteGameBoard.EMPTY);
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a+3][b-3].getsState() == c &&
			   board[a+2][b-2].getsState() == d &&
			   board[a+1][b-1].getsState() == d)
			{
			   stonesC += 2;
			   board[a+2][b-2].setsState(PenteGameBoard.EMPTY);
			   board[a+1][b-1].setsState(PenteGameBoard.EMPTY);
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a+3][b].getsState() == c &&
			   board[a+2][b].getsState() == d &&
			   board[a+1][b].getsState() == d)
			{
			   stonesC += 2;
			   board[a+2][b].setsState(PenteGameBoard.EMPTY);
		       board[a+1][b].setsState(PenteGameBoard.EMPTY);
			}	
		}
		catch(Exception e)
		{
			
		}
		
		try
		{

			if(board[a+3][b+3].getsState() == c &&
			   board[a+2][b+2].getsState() == d &&
			   board[a+1][b+1].getsState() == d)
			{
				stonesC += 2;
				board[a+2][b+2].setsState(PenteGameBoard.EMPTY);
				board[a+1][b+1].setsState(PenteGameBoard.EMPTY);
			}
		}
		catch(Exception e)
		{
			
		}
		
		try
		{
			if(board[a][b+3].getsState() == c &&
			   board[a][b+2].getsState() == d &&
			   board[a][b+1].getsState() == d)
			{
			   stonesC += 2;
			   board[a][b+2].setsState(PenteGameBoard.EMPTY);
			   board[a][b+1].setsState(PenteGameBoard.EMPTY);
			}
		}
		catch(Exception e)
		{
			
		}
		
		try
		{
			if(board[a-3][b+3].getsState() == c &&
			   board[a-3][b+2].getsState() == d &&
			   board[a-3][b+1].getsState() == d)
			{
			   stonesC += 2;
			   board[a-3][b+2].setsState(PenteGameBoard.EMPTY);
			   board[a-3][b+1].setsState(PenteGameBoard.EMPTY);
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a-3][b].getsState() == c &&
			   board[a-2][b].getsState() == d &&
			   board[a-1][b].getsState() == d)
			{
			   stonesC += 2;
			   board[a-2][b].setsState(PenteGameBoard.EMPTY);
			   board[a-1][b].setsState(PenteGameBoard.EMPTY);
			}	
		}
		catch(Exception e)
		{
		}
		
		if(playerTurn == PenteGameBoard.BLACKSTONE)
		{
			capturedW += stonesC;
		}
		else
		{
			capturedB += stonesC;
		}
	}
	
	public boolean checkForCaptureWin()
	{
		boolean a = false;
		if(capturedB == 10 || capturedW == 10)
		{
			a = true;
		}
		return a;
	}
	public boolean checkFor5(PenteBoardSquare p)
	{
		boolean x = false;
		int a = p.getsCol();
		int b = p.getsRow();
		try
		{
			if(board[a+1][b].getsState() == playerTurn &&
			   board[a+2][b].getsState() == playerTurn &&
			   board[a+3][b].getsState() == playerTurn &&
			   board[a+4][b].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try 
		{
			if(board[a-1][b].getsState() == playerTurn &&
			   board[a+1][b].getsState() == playerTurn &&
			   board[a+2][b].getsState() == playerTurn &&
			   board[a+3][b].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a-2][b].getsState() == playerTurn &&
			   board[a-1][b].getsState() == playerTurn &&
			   board[a+1][b].getsState() == playerTurn &&
			   board[a+2][b].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a-3][b].getsState() == playerTurn &&
			   board[a-2][b].getsState() == playerTurn &&
			   board[a-1][b].getsState() == playerTurn &&
			   board[a+1][b].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a-4][b].getsState() == playerTurn &&
			   board[a-3][b].getsState() == playerTurn &&
			   board[a-2][b].getsState() == playerTurn &&
			   board[a-1][b].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a][b+1].getsState() == playerTurn &&
			   board[a][b+2].getsState() == playerTurn &&
			   board[a][b+3].getsState() == playerTurn &&
			   board[a][b+4].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a][b-1].getsState() == playerTurn &&
			   board[a][b+1].getsState() == playerTurn &&
			   board[a][b+2].getsState() == playerTurn &&
			   board[a][b+3].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a][b-2].getsState() == playerTurn &&
			   board[a][b-1].getsState() == playerTurn &&
			   board[a][b+1].getsState() == playerTurn &&
			   board[a][b+2].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a][b-3].getsState() == playerTurn &&
			   board[a][b-2].getsState() == playerTurn &&
			   board[a][b-1].getsState() == playerTurn &&
			   board[a][b+1].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a][b-4].getsState() == playerTurn &&
			   board[a][b-3].getsState() == playerTurn &&
			   board[a][b-2].getsState() == playerTurn &&
			   board[a][b-1].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a+1][b+1].getsState() == playerTurn &&
			   board[a+2][b+2].getsState() == playerTurn &&
			   board[a+3][b+3].getsState() == playerTurn &&
			   board[a+4][b+4].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a-1][b-1].getsState() == playerTurn &&
			   board[a+1][b+1].getsState() == playerTurn &&
			   board[a+2][b+2].getsState() == playerTurn &&
			   board[a+3][b+3].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a-2][b-2].getsState() == playerTurn &&
			   board[a-1][b-1].getsState() == playerTurn &&
			   board[a+1][b+1].getsState() == playerTurn &&
			   board[a+2][b+2].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a-3][b-3].getsState() == playerTurn &&
			   board[a-2][b-2].getsState() == playerTurn &&
			   board[a-1][b-1].getsState() == playerTurn &&
			   board[a+1][b+1].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a-4][b-4].getsState() == playerTurn &&
			   board[a-3][b-3].getsState() == playerTurn &&
			   board[a-2][b-2].getsState() == playerTurn &&
			   board[a-1][b-1].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a-1][b+1].getsState() == playerTurn &&
			   board[a-2][b+2].getsState() == playerTurn &&
			   board[a-3][b+3].getsState() == playerTurn &&
			   board[a-4][b+4].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a+1][b-1].getsState() == playerTurn &&
			   board[a-1][b+1].getsState() == playerTurn &&
			   board[a-2][b+2].getsState() == playerTurn &&
			   board[a-3][b+3].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a+2][b-2].getsState() == playerTurn &&
			   board[a+1][b-1].getsState() == playerTurn &&
			   board[a-1][b+1].getsState() == playerTurn &&
			   board[a-2][b+2].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a+3][b-3].getsState() == playerTurn &&
			   board[a+2][b-2].getsState() == playerTurn &&
			   board[a+1][b-1].getsState() == playerTurn &&
			   board[a-1][b+1].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		try
		{
			if(board[a+4][b-4].getsState() == playerTurn &&
			   board[a+3][b-3].getsState() == playerTurn &&
			   board[a+2][b-2].getsState() == playerTurn &&
			   board[a+1][b-1].getsState() == playerTurn)
			{
				x = true;
			}
		}
		catch(Exception e)
		{
		}
		
		
		return x;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		int xMouse = e.getX();
		int yMouse = e.getY();
		
		PenteBoardSquare p = findLoc(board, xMouse, yMouse);
		
		if(!open(p) || (count == 3 && p.getsRow() >= 7 && p.getsRow() <= 11 && p.getsCol() >= 7 && p.getsCol() <= 11))
		{
			System.out.println("Can't play in the square");
		}
		else
		{
			p.setState(playerTurn);
			checkForRemoval(p);
			if(checkForCaptureWin() || checkFor5(p))
			{
				this.setVisible(false);
				this.startNewGame();
				this.setVisible(true);
			}
			else
			{
				changePlayerTurn();
			}
			this.repaint();
		}
		
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}
}
