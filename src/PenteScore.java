import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class PenteScore extends JPanel
{
	private static final long serialVersionUID = 2888242485663112310L;
	private JLabel p1Name, p2Name;
	private JTextField p1Captures, p2Captures;
	private JButton resetButton;
	private JTextField whoseTurnField;

	private Color backColor, darkColor, lightColor;
	private int sbWidth, sbHeight;
	private Font myFont = new Font("Arial", Font.BOLD, 24);
	private boolean firstGame = true;
	
	public PenteScore(int w, int h)
	{
		backColor = new Color(0, 195, 255);
		darkColor = new Color(0, 97, 224);
		lightColor = new Color(114, 156, 255);
		sbWidth = w;
		sbHeight = h;
		
		this.setSize(sbWidth, sbHeight);
		this.setBackground(backColor);
		this.setVisible(true);
		
		addInfoPlaces();
		p1Captures.setFocusable(false);
		p2Captures.setFocusable(false);
		p1Name.setFocusable(false);
		p2Name.setFocusable(false);
	}
	
	public void addInfoPlaces()
	{
		JPanel p1Panel = new JPanel();
		
		p1Panel.setLayout(new BoxLayout(p1Panel, BoxLayout.Y_AXIS));
		p1Panel.setSize(sbWidth, (int)(sbHeight*0.45));
		p1Panel.setBackground(lightColor);
			
		p1Name = new JLabel("Player1 name");
		p1Name.setAlignmentX(Component.CENTER_ALIGNMENT);
		p1Name.setFont(myFont);
		p1Name.setForeground(Color.WHITE);
		p1Name.setHorizontalAlignment(SwingConstants.CENTER);
			
		p1Captures = new JTextField("Player 1 Captures");
		p1Captures.setAlignmentX(Component.CENTER_ALIGNMENT);
		p1Captures.setFont(myFont);
		p1Captures.setForeground(lightColor);	
		p1Captures.setBackground(Color.BLACK);
		p1Captures.setHorizontalAlignment(SwingConstants.CENTER);
			
		p1Panel.add(Box.createRigidArea(new Dimension(sbWidth-40, 50)));
		p1Panel.add(p1Name);
		p1Panel.add(Box.createRigidArea(new Dimension(sbWidth-40, 30)));
		p1Panel.add(p1Captures);
		p1Panel.add(Box.createRigidArea(new Dimension(sbWidth-40, 50)));
		
		Border b = BorderFactory.createLineBorder(Color.CYAN, 8, true);
		
		p1Panel.setBorder(b);
		
		this.add(Box.createRigidArea(new Dimension(sbWidth-40, 20)));
		this.add(p1Panel);
		this.add(Box.createRigidArea(new Dimension(sbWidth-40, 20)));
		
		resetButton = new JButton("New Game");
		resetButton.setFont(myFont);
		
		this.add(resetButton);
		
		JPanel p2Panel = new JPanel();
		
		p2Panel.setLayout(new BoxLayout(p2Panel, BoxLayout.Y_AXIS));
		p2Panel.setSize(sbWidth, (int)(sbHeight*0.45));
		p2Panel.setBackground(darkColor);
			
		p2Name = new JLabel("Player2 name");
		p2Name.setAlignmentX(Component.CENTER_ALIGNMENT);
		p2Name.setFont(myFont);
		p2Name.setForeground(Color.WHITE);
		p2Name.setHorizontalAlignment(SwingConstants.CENTER);
			
		p2Captures = new JTextField("Player 2 Captures");
		p2Captures.setAlignmentX(Component.CENTER_ALIGNMENT);
		p2Captures.setFont(myFont);
		p2Captures.setForeground(darkColor);
		p2Captures.setBackground(Color.WHITE);
		p2Captures.setHorizontalAlignment(SwingConstants.CENTER);
			
		p2Panel.add(Box.createRigidArea(new Dimension(sbWidth-40, 50)));
		p2Panel.add(p2Name);
		p2Panel.add(Box.createRigidArea(new Dimension(sbWidth-40, 30)));
		p2Panel.add(p2Captures);
		p2Panel.add(Box.createRigidArea(new Dimension(sbWidth-40, 50)));
		
		p2Panel.setBorder(b);
		
		this.add(Box.createRigidArea(new Dimension(sbWidth-40, 20)));
		this.add(p2Panel);
		
		JPanel whoseTurn = new JPanel();
		whoseTurn.setLayout(new BoxLayout(whoseTurn, BoxLayout.Y_AXIS));
		whoseTurn.setSize(sbWidth, (int)(sbHeight*0.45));
		whoseTurn.setOpaque(false);
		
		whoseTurnField = new JTextField("Its ? Turn Now");
		whoseTurnField.setAlignmentX(Component.CENTER_ALIGNMENT);
		whoseTurnField.setFont(myFont);
		whoseTurnField.setForeground(Color.BLACK);
		whoseTurnField.setHorizontalAlignment(SwingConstants.CENTER);
		whoseTurnField.setFocusable(false);
		
		whoseTurn.add(Box.createRigidArea(new Dimension(sbWidth-40, 20)));
		whoseTurn.add(whoseTurnField);
		whoseTurn.add(Box.createRigidArea(new Dimension(sbWidth-40, 20)));
		whoseTurn.setBorder(b);
		
		this.add(Box.createRigidArea(new Dimension(sbWidth-40, 30)));
		this.add(whoseTurn);
		
	}
	public void setName(String n, int whichPlayer)
	{
		if(whichPlayer == PenteGameBoard.BLACKSTONE)
		{
			p1Name.setText("Player 1: " + n);
		}
		else
		{
			p2Name.setText("Player 2: " + n);
		}
	}
	public void setCaptures(int c, int whichPlayer)
	{
		if(whichPlayer == PenteGameBoard.BLACKSTONE)
		{
			p1Captures.setText("Captures: " + Integer.toString(c));
			Rectangle r = p1Captures.getVisibleRect();
			p1Captures.paintImmediately(r);
		}
		else
		{
			p2Captures.setText("Captures: " + Integer.toString(c));
			Rectangle x = p2Captures.getVisibleRect();
			p2Captures.paintImmediately(x);
		}
	}
	
	public void setPlayerTurn(int whichPlayer)
	{
		if(whichPlayer == PenteGameBoard.BLACKSTONE)
		{
			whoseTurnField.setBackground(Color.BLACK);
			whoseTurnField.setForeground(Color.WHITE);
			int cLoc = p1Name.getText().indexOf(":");
			String n = p1Name.getText().substring(cLoc + 2, p1Name.getText().length());
			whoseTurnField.setText("It's " + n + "'s Turn Now");
			
		}
		else
		{
			whoseTurnField.setBackground(Color.WHITE);
			whoseTurnField.setForeground(Color.BLACK);
			int cLoc2 = p2Name.getText().indexOf(":");
			String n2 = p2Name.getText().substring(cLoc2 + 2, p2Name.getText().length());
			whoseTurnField.setText("It's " + n2 + "'s Turn Now");
		}
		if(firstGame)
		{
			whoseTurnField.repaint();
		}
		else
		{
			Rectangle r = whoseTurnField.getVisibleRect();
			whoseTurnField.paintImmediately(r);
		}
	}	
	public void resetWhoseTurn()
	{
		whoseTurnField.setText("It's ? Turn");
	}
	
	public JButton getResetButton()
	{
		return resetButton;
	}
	public String getName(int whichPlayer)
	{
		if(whichPlayer == PenteGameBoard.BLACKSTONE)
		{
			int cL1 = p1Name.getText().indexOf(":");
			return p1Name.getText().substring(cL1 + 2, p1Name.getText().length());
		}
		else
		{
			int cL2 = p2Name.getText().indexOf(":");
			return p2Name.getText().substring(cL2 + 2, p2Name.getText().length());
		}
			
	}

	public void setFirstGame(boolean fG)
	{
		firstGame = fG;
	}
}
