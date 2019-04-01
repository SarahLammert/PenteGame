//Imports
import javax.swing.JFrame;

//Class for running the game: Main stored here
public class PenteGameRunner
{
	//Main
	public static void main(String [] args)
	{
		//Width and height of the board in pixels
		int gWidth = 19*38;
		int gHeight = 19*38;
		
		JFrame theGame = new JFrame("Pente!");
		
		theGame.setSize(gWidth, gHeight+20);
		theGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Creating a JPanel to put on top of the JFrame
		PenteGameBoard gb = new PenteGameBoard(gWidth, gHeight, theGame);
		
		//Adding the JPanel to the JFrame
		theGame.add(gb);
	
		//Showing the JPanel and JFrame
		theGame.setVisible(true);
		
		gb.startNewGame();
	
		
	}
}
