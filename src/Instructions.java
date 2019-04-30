import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Instructions extends JPanel
{
	private static final long serialVersionUID = 274292313334079340L;
	private int iWidth, iHeight;
	private Color c;
	private JLabel howToPlay;
	private Font myFont = new Font("Arial", Font.BOLD, 14);
	public Instructions(int w, int h)
	{
		iWidth  = w;
		iHeight = h;
		
		c = new Color(154, 248, 249);
		
		this.setSize(iWidth, iHeight);
		this.setBackground(c);
		this.setVisible(true);
		
		this.showText();
		howToPlay.setFocusable(false);
	}
	
	public void showText()
	{
		howToPlay = new JLabel("<html>How To Play Pente!!!<BR>"
				             + "Win conditions are:<BR>"
				             + "1. Capture 10 stones<BR>"
				             + "2. Get 5 stones in a<BR>"
				             + "row, column, or diagonal<BR>"
				             + "Capturing:<BR>"
				             + "Picture a line of four stones<BR>"
				             + "The two in the middle are your opponnents<BR>"
				             + "The two on the ends are yours<BR>"
				             + "If you place the two on the ends,<BR>"
				             + "it captures the two middle<BR>"
				             + "If you have the two stones on the ends<BR>"
				             + "and one in the middle and your<BR>"
				             + "opponent places their stone in that spot<BR>"
				             + "it looks like a capture,<BR>"
				             + "but it is not<BR>"
				             + "Special rule:<BR>"
				             + "If it is black's second turn<BR>"
				             + "(meaning the first after black<BR>"
				             + "plays the middle square)<BR>"
				             + "Then you can't play in the inner<BR>"
				             + "lighter squares for that turn</html>");
		howToPlay.setSize(iWidth, iHeight);
		howToPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		howToPlay.setHorizontalAlignment(SwingConstants.CENTER);
		howToPlay.setFont(myFont);
		this.add(howToPlay);
	
		
		
	}
}
