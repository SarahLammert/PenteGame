import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PenteGameBoard extends JPanel implements MouseListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	
	public static final int EMPTY = 0;
	public static final int BLACKSTONE = 1;
	public static final int WHITESTONE = -1;
	public static final int PLAYER1_TURN = 1;
	public static final int PLAYER2_TURN = -1;
	public static final int NUM_SQUARES_SIDE = 19;
	public static final int MAX_CAPTURES = 10;
	public static final int SLEEP_TIME = 250;
	
	private int bWidth, bHeight;
	private int squareW, squareH;
	private int playerTurn;
	private int tCount;
	private int p1Captures, p2Captures;
	
	private boolean player1IsComputer;
	private boolean player2IsComputer;
	private boolean gameOver;
	
	private String p1Name = null;
	private String p2Name = null;
	
	private PenteBoardSquare [][] board;
	private PenteScore myScoreBoard;
	
	private Computer c1 = null;
	private Computer c2 = null;
	
	//Constructor with the parameters: width and height of the board
	public PenteGameBoard(int w, int h, PenteScore sb)
	{
		//Storing the parameters
		bWidth = w;
		bHeight = h;
		myScoreBoard = sb;
		tCount = 1;
		player1IsComputer = false;
		player2IsComputer = false;
		gameOver = false;
		
		//Setting the PenteGameboard
		this.setSize(bWidth, bHeight);
		this.setBackground(Color.CYAN);
		
		//Determining the size of the square width and height based on the number of squares of on the side
		squareW = bWidth/NUM_SQUARES_SIDE;
		squareH = bHeight/NUM_SQUARES_SIDE;
		
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
		this.initialDisplay();
		this.repaint();
		addMouseListener(this);
		this.setFocusable(true);
		myScoreBoard.getResetButton().addActionListener(this);
		
	}
	//Method to draw the game board by overriding paintComponent
	public void paintComponent(Graphics g)
	{
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
				board[col][row].setWinning(false);
			}
		}
		this.paintASAP();
	}
	public void startNewGame(boolean firstGame)
	{
		tCount = 1;
		p1Captures = 0;
		p2Captures = 0;
		myScoreBoard.resetWhoseTurn();
		myScoreBoard.repaint();
		myScoreBoard.setCaptures(p1Captures, BLACKSTONE);
		myScoreBoard.setCaptures(p2Captures, WHITESTONE);
		gameOver = false;
		playerTurn = PLAYER1_TURN;
		
		if(firstGame)
		{
			myScoreBoard.setName("", BLACKSTONE);
			myScoreBoard.setName("", WHITESTONE);
			p1Name = JOptionPane.showInputDialog("Name of player 1 (or type 'Computer' for Computer)");
			if(p1Name != null && p1Name.toLowerCase().equals("computer") || p1Name.toLowerCase().equals("c"))
			{
				player1IsComputer = true;
				c1 = new Computer(this, PenteGameBoard.BLACKSTONE);
			}
		
			myScoreBoard.setName(p1Name, BLACKSTONE);
		
			p2Name = JOptionPane.showInputDialog("Name of player 2 (or type 'Computer' for Computer)");
			if(p2Name != null &&  p2Name.toLowerCase().equals("computer") || p2Name.toLowerCase().equals("c"))
			{
				player2IsComputer = true;
				c2 = new Computer(this, PenteGameBoard.WHITESTONE);
			}
		
			myScoreBoard.setName(p2Name, WHITESTONE);
		}
		
		this.resetBoard();
		this.board[NUM_SQUARES_SIDE/2][NUM_SQUARES_SIDE/2].setState(BLACKSTONE);
		if(firstGame)
		{
			this.repaint();
		}
		else
		{
			this.paintASAP();
		}
		this.changePlayerTurn();
		this.checkForComputerTurn();
		
	}
	public void changePlayerTurn()
	{
		playerTurn *= -1;
		tCount += 1;
		myScoreBoard.setPlayerTurn(playerTurn);
		myScoreBoard.repaint();
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
		if(p.getState() == PenteGameBoard.EMPTY)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void checkRemove(PenteBoardSquare p)
	{
		int s  = 0;
		for(int i = 1, x = 1; i < 3; i++, x *= -1)
		{
			s += removeStones(p, 3, 2, 1, 3, 2, 1, x, x)
			  +  removeStones(p, 3, 2, 1, 3, 2, 1, x, -x)
			  +  removeStones(p, 3, 2, 1, 0, 0, 0, x, 0)
			  +  removeStones(p, 0, 0, 0, 3, 2, 1, 0, x);
		}

		if(playerTurn == PenteGameBoard.BLACKSTONE)
		{
			p1Captures += s;
			myScoreBoard.setCaptures(p1Captures, playerTurn);
		}
		else
		{
			p2Captures += s;
			myScoreBoard.setCaptures(p2Captures, playerTurn);
		}
	}
	public int removeStones(PenteBoardSquare p, int a, int b, int c, int x, int y, int z, int l, int s)
	{
		int count = 0;
		try
		{
			if(board[p.getsCol()+(a*l)][p.getsRow()+(x*s)].getState() == playerTurn &&
			   board[p.getsCol()+(b*l)][p.getsRow()+(y*s)].getState() == playerTurn * -1 &&
			   board[p.getsCol()+(c*l)][p.getsRow()+(z*s)].getState() == playerTurn * -1)
			{
			   count += 2;
			   board[p.getsCol()+(b*l)][p.getsRow()+(y*s)].setState(PenteGameBoard.EMPTY);
			   board[p.getsCol()+(c*l)][p.getsRow()+(z*s)].setState(PenteGameBoard.EMPTY);
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
		return count;
	}
	public boolean checkForCaptureWin()
	{
		boolean a = false;
		if(p1Captures >= PenteGameBoard.MAX_CAPTURES || p2Captures >= PenteGameBoard.MAX_CAPTURES)
		{
			a = true;
		}
		return a;
	}
	public boolean check5InWholeBoard()
	{
		boolean f = false;
		
		for(int col = 0; col < NUM_SQUARES_SIDE; col++)
		{
			for(int row = 0; row < NUM_SQUARES_SIDE; row++)
			{
				if(check5InARowForASquare(board[col][row]))
				{
					f = true;
				}
			}
		}
		
		return f;
	}
	public boolean check5InARowForASquare(PenteBoardSquare p)
	{
		boolean a = false;
		if(check1Way(p, -2, -1, 1, 2, 0, 0, 0, 0) ||
		   check1Way(p, 0, 0, 0, 0, 2, 1, -1, -2) ||
		   check1Way(p, -2, -1, 1, 2, -2, -1, 1, 2) ||
		   check1Way(p, -2, -1, 1, 2, 2, 1, -1, -2)
		)
		{
			a = true;
		}
		if(a == false)
		{
			for(int x = 0, y = 1; x < 2; x++, y *= -1)
			{
				if(check1Way(p, 4*y, 3*y, 2*y, 1*y, 0, 0, 0, 0) ||
				   check1Way(p, 3*y, 2*y, 1*y, -1*y, 0, 0, 0, 0) ||
				   check1Way(p, 0, 0, 0, 0, 4*y, 3*y, 2*y, 1*y) ||
				   check1Way(p, 0, 0, 0, 0, 3*y, 2*y, 1*y, -1*y) ||
				   check1Way(p, 4*y, 3*y, 2*y, 1*y, 4*y, 3*y, 2*y, 1*y) ||
				   check1Way(p, 3*y, 2*y, 1*y, -1*y, 3*y, 2*y, 1*y, -1*y) ||
				   check1Way(p, 4*y, 3*y, 2*y, 1*y, -4*y, -3*y, -2*y, -1*y) ||
				   check1Way(p, 3*y, 2*y, 1*y, -1*y, -3*y, -2*y, -1*y, 1*y))
				{
					a = true;
					break;
				}
			}
		}
		return a;
	}
	public boolean check1Way(PenteBoardSquare p, int a, int b, int c, int d, int e, int f, int g, int h)
	{
		boolean x = false;
		try
		{
			if(p.getState() == playerTurn &&
			   board[p.getsCol()+a][p.getsRow()+e].getState() == playerTurn &&
			   board[p.getsCol()+b][p.getsRow()+f].getState() == playerTurn &&
			   board[p.getsCol()+c][p.getsRow()+g].getState() == playerTurn &&
			   board[p.getsCol()+d][p.getsRow()+h].getState() == playerTurn)
			{
				p.setWinning(true);
				board[p.getsCol()+a][p.getsRow()+e].setWinning(true);
				board[p.getsCol()+b][p.getsRow()+f].setWinning(true);
				board[p.getsCol()+c][p.getsRow()+g].setWinning(true);
				board[p.getsCol()+d][p.getsRow()+h].setWinning(true);
				
				this.repaint();
				
				x = true;
			}
		}
		catch(ArrayIndexOutOfBoundsException z)
		{
		}
		
		return x;
	}
	public boolean checkSIsOpen(PenteBoardSquare p)
	{
		if(!open(p) || (tCount == 3 && p.getsRow() >= 7 && p.getsRow() <= 11 && p.getsCol() >= 7 && p.getsCol() <= 11))
		{
			System.out.println("Can't play in this square");
			return false;
		}
		else
		{
			return true;
		}
	}
	public void checkEverything(PenteBoardSquare p)
	{
		p.setState(playerTurn);
		this.paintASAP();
		this.checkRemove(p);
		if(!winGame())
		{
			this.changePlayerTurn();
			this.checkForComputerTurn();
		}
	}
	public boolean winGame()
	{
		if(checkForCaptureWin() || check5InWholeBoard())
		{
			gameOver = true;
			JOptionPane.showMessageDialog(null, "The winner of the game was: " + myScoreBoard.getName(playerTurn));
		}
		return gameOver;
	}
	
	private void checkForComputerTurn()
	{
		if(playerTurn == PLAYER1_TURN && player1IsComputer)
		{
			int [] nextMove = c1.getComputerMove();
			int newC = nextMove[0];
			int newR = nextMove[1];
			checkEverything(board[newC][newR]);
		}
		else if(playerTurn == PLAYER2_TURN && player2IsComputer)
		{
			int [] nextMove = c2.getComputerMove();
			int newC = nextMove[0];
			int newR = nextMove[1];
			checkEverything(board[newC][newR]);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(gameOver == false)
		{
			int xMouse = e.getX();
			int yMouse = e.getY();
		
			PenteBoardSquare p = findLoc(board, xMouse, yMouse);
			
			if(checkSIsOpen(p))
			{
				this.checkEverything(p);
			}
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
	public void initialDisplay()
	{
		this.board[0][0].setState(BLACKSTONE);
		this.board[0][1].setState(WHITESTONE);
		this.board[0][2].setState(BLACKSTONE);
		this.board[0][3].setState(WHITESTONE);
		this.board[0][4].setState(BLACKSTONE);
		this.board[1][0].setState(WHITESTONE);
		this.board[1][2].setState(WHITESTONE);
		this.board[2][0].setState(BLACKSTONE);
		this.board[2][1].setState(WHITESTONE);
		this.board[2][2].setState(BLACKSTONE);
		this.board[3][5].setState(WHITESTONE);
		this.board[3][6].setState(BLACKSTONE);
		this.board[3][7].setState(WHITESTONE);
		this.board[3][8].setState(BLACKSTONE);
		this.board[3][9].setState(WHITESTONE);
		this.board[4][5].setState(BLACKSTONE);
		this.board[4][7].setState(BLACKSTONE);
		this.board[4][9].setState(BLACKSTONE);
		this.board[5][7].setState(WHITESTONE);
		this.board[5][5].setState(WHITESTONE);
		this.board[5][9].setState(WHITESTONE);
		this.board[8][11].setState(BLACKSTONE);
		this.board[8][12].setState(WHITESTONE);
		this.board[8][13].setState(BLACKSTONE);
		this.board[8][14].setState(WHITESTONE);
		this.board[9][11].setState(WHITESTONE);
		this.board[10][11].setState(BLACKSTONE);
		this.board[10][12].setState(WHITESTONE);
		this.board[10][13].setState(BLACKSTONE);
		this.board[10][14].setState(WHITESTONE);
		this.board[12][5].setState(WHITESTONE);
		this.board[13][5].setState(BLACKSTONE);
		this.board[13][6].setState(WHITESTONE);
		this.board[13][7].setState(BLACKSTONE);
		this.board[13][8].setState(WHITESTONE);
		this.board[13][9].setState(BLACKSTONE);
		this.board[14][5].setState(WHITESTONE);
		this.board[16][0].setState(BLACKSTONE);
		this.board[16][1].setState(WHITESTONE);
		this.board[16][2].setState(BLACKSTONE);
		this.board[16][3].setState(WHITESTONE);
		this.board[16][4].setState(BLACKSTONE);
		this.board[17][0].setState(WHITESTONE);
		this.board[17][2].setState(WHITESTONE);
		this.board[17][4].setState(WHITESTONE);
		this.board[18][2].setState(BLACKSTONE);
		this.board[18][0].setState(BLACKSTONE);
		this.board[18][4].setState(BLACKSTONE);

	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JOptionPane.showMessageDialog(null, "Starting new game!");
		myScoreBoard.setFirstGame(false);
		this.startNewGame(false);
	}
	public PenteBoardSquare[][] getBoard()
	{
		return board;
	}
	public void paintASAP()
	{
		this.paintImmediately(0, 0, bWidth, bHeight);
	}
}