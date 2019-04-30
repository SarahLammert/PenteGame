import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class PenteGameRunner
{
	public static void main(String [] args)
	{
		int gWidth = 19*38;
		int gHeight = 19*38;
		int sbWidth = gWidth/2;
		int iWidth = 300;
		
		JFrame theGame = new JFrame("Pente!");
		
		theGame.setLayout(new BorderLayout());
		theGame.setSize(gWidth+sbWidth+iWidth, gHeight+20);
		theGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theGame.setResizable(false);
		
		PenteScore sb = new PenteScore(sbWidth, gHeight);
		sb.setPreferredSize(new Dimension(sbWidth, gHeight));
		
		PenteGameBoard gb = new PenteGameBoard(gWidth, gHeight, sb);
		gb.setPreferredSize(new Dimension(gWidth, gHeight));
		
		Instructions i = new Instructions(iWidth, gHeight);
		i.setPreferredSize(new Dimension(iWidth, gHeight));

		theGame.add(sb, BorderLayout.CENTER);
		theGame.add(gb, BorderLayout.WEST);
		theGame.add(i, BorderLayout.EAST);
		
		theGame.setVisible(true);
		
		gb.startNewGame(true);
	}
}
