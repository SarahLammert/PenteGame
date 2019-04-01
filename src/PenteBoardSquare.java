//Imports
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

//Class for creating the squares
public class PenteBoardSquare
{
	//Data fields
	private int xLoc, yLoc; //Location
	private int sWidth, sHeight; //Dimensions of square
	private int rFactor = 14; //Sizing of square
	private int sState; //Whether it is filled or not
	private int sCol;
	private int sRow;
	
	//Colors
	private Color sColor; //Square 
	private Color lColor; //Line 
	private Color bColor; //Board of the square
	private Color innerColor; //Inner 5x5 
	private Color stoneColorW; //Stone base
	private Color stoneColorWT; //Stone top
	private Color stoneColorB; //Stone base
	private Color stoneColorBT; //Stone top
	private Color shadowGrey; //Shadow
	private Color highLightColor; //Reflection
	
	boolean isInner = false; //If in the inner 5x5
	
	//Constructor with the parameters: location of square (x, y) and size of square (w, h)
	public PenteBoardSquare(int x, int y, int w, int h, int c, int r)
	{
		//Storing the parameters
		xLoc = x;
		yLoc = y;
		sWidth = w;
		sHeight = h;
	    setsRow(r);
		setsCol(c);
		
		
		sColor = new Color(2, 195, 154);
		lColor = new Color(2, 128, 144);
		bColor = new Color(5, 102, 141);
		innerColor = new Color(174, 221, 252);
		
		stoneColorB = new Color(70, 70, 70);
		stoneColorBT = new Color(0, 0, 0);
		stoneColorW = new Color(235, 235, 235);
		stoneColorWT = new Color(255, 255, 255);
		shadowGrey = new Color(160, 159, 159);
		highLightColor = new Color(255, 255, 255);
		
		
		//Setting the state of the squares
		sState = PenteGameBoard.EMPTY;
	}
	
	public void setInner()
	{
		isInner = true;
	}
	
	//Drawing the square
	public void drawMe(Graphics g)
	{
		int e = xLoc+(rFactor/2);
		int r = yLoc+(rFactor/2);
		int w = sWidth-rFactor;
		int x = sHeight-rFactor;
		
		if(isInner)
		{
			g.setColor(innerColor);
		}
		else
		{
			g.setColor(sColor);
		}
		
		//The square
		g.fillRect(xLoc, yLoc, sWidth, sHeight);
		
		//The boarder color
		g.setColor(bColor);
		
		//The boarder
		g.drawRect(xLoc, yLoc, sWidth, sHeight);
		
		if(sState != PenteGameBoard.EMPTY)
		{
			g.setColor(shadowGrey);
			g.fillOval(e-3, r+3, w, x);
		}
		//The horizontal line color
		g.setColor(lColor);
	
		//The horizontal line
		g.drawLine(xLoc, (yLoc+(sHeight/2)), xLoc+sWidth, yLoc+(sHeight/2));
		
		//The vertical line
		g.drawLine(xLoc+(sWidth/2), yLoc, xLoc+(sWidth/2), yLoc+sHeight);
		
		Graphics2D g2 = (Graphics2D)g;
		
		if(sState == PenteGameBoard.BLACKSTONE)
		{
			g2.setColor(stoneColorB);
			g2.fillOval(e, r, w, x);
			
			g2.setColor(stoneColorBT);
			g2.fillOval(e+3, r+3, w-4, x-4);
			
			g2.setStroke(new BasicStroke(2));
			g2.setColor(highLightColor);
			g2.drawArc(xLoc+(int)(sWidth*0.45), yLoc+12, (int)(sWidth*0.3), (int)(sHeight*0.4), 0, 90);
			g2.setStroke(new BasicStroke(1));
			
		}
		
		if(sState == PenteGameBoard.WHITESTONE)
		{
			g2.setColor(shadowGrey);
			g2.fillOval(e-3, r+3, w, x);
			
			
			g2.setColor(stoneColorW);
			g2.fillOval(e, r, w, x);
			
			g2.setColor(stoneColorWT);
			g2.fillOval(e+3, r+3, w-4, x-4);
		}
	}
	
	public void setState(int newState)
	{
		if(newState < -1 || newState > 1)
		{
			System.out.println(newState  + "is not a possible state");
		}
		else
		{
			sState = newState;
		}
	}

	public int getxLoc()
	{
		return xLoc;
	}
	
	public void setxLoc(int x)
	{
		xLoc = x;
	}
	
	public int getyLoc()
	{
		return yLoc;
	}
	
	public void setyLoc(int y)
	{
		yLoc = y;
	}
	
	public int getsWidth()
	{
		return sWidth;
	}
	
	public void setsWidth(int w)
	{
		sWidth = w;
	}
	
	public int getsHeight()
	{
		return sHeight;
	}
	
	public void setsHeight(int h)
	{
		sHeight = h;
	}
	
	public int getsState()
	{
		return sState;
	}
	
	public void setsState(int sS)
	{
		sState = sS;
	}

	public int getsRow() {
		return sRow;
	}

	public void setsRow(int r) {
		sRow = r;
	}

	public int getsCol() {
		return sCol;
	}

	public void setsCol(int c) {
		sCol = c;
	}
}
